-- Generate main course site
-- Copyright (c) Richard Eisenberg

{-# LANGUAGE OverloadedStrings, TypeApplications, ViewPatterns #-}

module Main where

import Hakyll
import Control.Monad
import Data.Monoid
import System.FilePath
import Text.Printf
import Data.List
import Debug.Trace

main :: IO ()
main = hakyll $ do
  piimatch "placement.html" $ do
    route   idRoute
    compile copyFileCompiler

  piimatch "web/css/*" $ do
    route   dropWebRoute
    compile compressCssCompiler

  piimatch "web/images/*" $ do
    route   dropWebRoute
    compile copyFileCompiler

  piimatch "acm.jar" $ do
    route   idRoute
    compile copyFileCompiler

  piimatch (fromRegex "^[0-9][0-9]_[^/]+/[^/]*\\.md") $ do
    route   $ dropClassNameRoute `composeRoutes` setHtmlExtension
    compile $ mdCompiler

  piimatch (fromRegex "^[0-9][0-9]_[^/]+/images/.*") $ do
    route   $ dropClassNameRoute
    compile $ copyFileCompiler

  piimatch (fromRegex "^[0-9][0-9]_[^/]+/[^0-9][^/]*\\.(pdf|txt|hs|json|java|jar|xls|csv|mp3|jpg|wav)") $ do
    route   $ dropClassNameRoute
    compile $ copyFileCompiler

  piimatch (fromRegex "^labs/[0-9][0-9]_[^/]+/[^/]*\\.md") $ do
    route   $ dropLabNameRoute `composeRoutes` setHtmlExtension
    compile $ mdCompiler

  piimatch (fromRegex "^labs/[0-9][0-9]_[^/]+/[^/]*\\.(zip|java|pdf)") $ do
    route   $ dropLabNameRoute
    compile $ copyFileCompiler

  piimatch (fromRegex "^hw/[0-9][0-9]_[^/]+/[^/]*.md") $ do
    route   $ homeworkRoute `composeRoutes` setExtension "html"
    compile $ mdCompiler

  piimatch (fromRegex "^hw/[0-9][0-9]_[^/]+/[^/]*\\.(pdf|jar|jnlp|txt)") $ do
    route   $ homeworkRoute
    compile $ copyFileCompiler

   -- for the navbar
  piimatch "web/*.md" $
    compile $ pandocCompiler

  piimatch "**.md" $ do   -- catchall
    route   $ setExtension "html"
    compile $ mdCompiler

  piimatch "web/templates/*.html" $ compile templateBodyCompiler

-- drop a "web/" prefix
dropWebRoute :: Routes
dropWebRoute = pathRoute tail

-- drop the XXX from 03_XXX/blah
dropClassNameRoute :: Routes
dropClassNameRoute = pathRoute $ \ (class_name : rest) -> take 2 class_name : rest

-- go from labs/01_blah/foo.md to lab01/foo.md
dropLabNameRoute :: Routes
dropLabNameRoute = pathRoute $ \ (_labs : lab_name : rest) -> ("lab" ++ take 2 lab_name) : rest

-- move from hw/NN_XXX/blah to hwNN/blah
homeworkRoute :: Routes
homeworkRoute = pathRoute $ \ (_hw : number : rest) -> ("hw" ++ take 2 number) : rest

setHtmlExtension :: Routes
setHtmlExtension = pathRoute $ \ (snocView -> (dirs, file)) ->
                                 dirs ++ [replaceExtensions file "html"]

-- drop class name and "examples" from example file
exampleRoute :: Routes
exampleRoute = pathRoute $
    \ (class_name : _examples : example_name : _web_export : rest) ->
      take 2 class_name : example_name : rest

-- defaultContext + basename
wrapperContext :: Context String
wrapperContext =
  defaultContext <>
  field "basename" (return . takeBaseName . toFilePath . itemIdentifier)

pandocCompileThis :: Item String -> Compiler (Item String)
pandocCompileThis = (return . writePandoc) <=< readPandoc

-- do all the processing I expect on an md file
mdCompiler :: Compiler (Item String)
mdCompiler
  =   pandocCompiler
  >>= applyAsTemplate (navbarContext <> examplesContext)
  >>= loadAndApplyTemplate "web/templates/wrapper.html" wrapperContext
  >>= relativizeUrls

-- augment the default context with values for, e.g., examples01
examplesContext :: Context String
examplesContext = mconcat [ mk_ex_ctx n | n <- [1..27] ] <> mconcat [ mk_lab_ctx n | n <- [1..13] ]
  where
    mk_ex_ctx :: Int -> Context String
    mk_ex_ctx n = field ("examples" ++ printf "%02d" n)
                        (const (mk_ex_str False n))

    mk_lab_ctx :: Int -> Context String
    mk_lab_ctx n = field ("lab_examples" ++ printf "%02d" n)
                         (const (mk_ex_str True n))

    mk_ex_str :: Bool  -- False <=> normal examples; True <=> labs examples
              -> Int -> Compiler String
    mk_ex_str labs n = do
      let ex_num_str = printf "%02d" n
          fileglob   = (if labs then "labs/" else "") ++ ex_num_str ++ "_*/*.java"
      examples <- loadAll @CopyFile (fromGlob fileglob)
      let decompose path_components = case path_components of
            [_labs, class_name, example_file] | labs     -> ("lab" ++ take 2 class_name, example_file)
            [class_name, example_file]        | not labs -> (take 2 class_name, example_file)
            _                                            -> error "bad path for examples"

          filenames = map (decompose . splitPath . toFilePath . itemIdentifier) examples
          one_ex (num, name) = printf "<a href=\"%s/%s\">%s</a>" num name name
          all_ex = intercalate "<br />" (map one_ex filenames)
      return all_ex

navbarContext :: Context String
navbarContext = defaultContext <>
                field "navbar" (const $ loadBody (fromFilePath "web/navbar.md"))

------------------
-- a customRoute that alters the list of filepath components. Directories in
-- this list end with '/'
pathRoute :: ([FilePath] -> [FilePath]) -> Routes
pathRoute f = customRoute $ joinPath . f . splitPath . toFilePath

snocView :: [a] -> ([a], a)
snocView = go []
  where
    go acc [x]    = (reverse acc, x)
    go acc (x:xs) = go (x:acc) xs
    go _   _      = error "snocView on empty list"

piimatch :: Pattern -> Rules () -> Rules ()
piimatch pat = match (pat .&&. complement "pii/**" .&&. complement "private/**")
