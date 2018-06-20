-- Generate main course site
-- Copyright (c) Richard Eisenberg

{-# LANGUAGE OverloadedStrings, TypeApplications, ViewPatterns, GeneralizedNewtypeDeriving,
             ScopedTypeVariables #-}

module Main where

import Hakyll
import Control.Monad
import Data.Monoid
import System.FilePath
import Text.Printf
import Data.List
import Debug.Trace
import Text.Pandoc ( Pandoc(..), Meta(..), MetaValue(..), Inline(..) )
import qualified Data.Map as M
import Data.Coerce
import Data.Binary
import System.Process

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

  piimatch (fromRegex "^[0-9][0-9]_[^/]+/[^0-9][^/]*\\.(pdf|txt|hs|json|jar|xls|csv|mp3|jpg|wav)") $ do
    route   $ dropClassNameRoute
    compile $ copyFileCompiler

  codeMatch (fromRegex "^[0-9][0-9]_[^/]+/[^0-9][^/]*\\.java") dropClassNameRoute

  piimatch (fromRegex "^labs/[0-9][0-9]_[^/]+/[^/]*\\.md") $ do
    route   $ dropLabNameRoute `composeRoutes` setHtmlExtension
    compile $ mdCompiler

  piimatch (fromRegex "^labs/[0-9][0-9]_[^/]+/[^/]*\\.(zip|pdf)") $ do
    route   $ dropLabNameRoute
    compile $ copyFileCompiler

  codeMatch (fromRegex "^labs/[0-9][0-9]_[^/]+/[^/]*\\.java") dropLabNameRoute

  piimatch (fromRegex "^hw/[0-9][0-9]_[^/]+/[^/]*.md") $ do
    route   $ homeworkRoute `composeRoutes` setExtension "html"
    compile $ mdCompiler

  piimatch (fromRegex "^hw/[0-9][0-9]_[^/]+/[^/]*\\.(pdf|jar|jnlp|txt|csv)") $ do
    route   $ homeworkRoute
    compile $ copyFileCompiler

  codeMatch (fromRegex "^hw/[0-9][0-9]_[^/]+/[^/]*\\.java") homeworkRoute

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

-- do all the processing I expect on an md file
mdCompiler :: Compiler (Item String)
mdCompiler = do
  body <- getResourceString
  pandoc <- readPandoc body
  let code_context = extractCodeContext (itemBody pandoc)
      html = writePandoc pandoc
  substed <- applyAsTemplate (code_context <> navbarContext <> examplesContext) html
  full_page <- loadAndApplyTemplate "web/templates/wrapper.html" wrapperContext substed
  relativizeUrls full_page

extractCodeContext :: Pandoc -> Context String
extractCodeContext (Pandoc meta _) = M.foldrWithKey (\k v c -> go k v <> c) mempty (coerce meta)
  where
    go :: String -> MetaValue -> Context String
    go c_key (MetaInlines [Str path])
      | Just key <- stripPrefix "c-" c_key
      = constField key $ codeLinkString path
    go _ _ = mempty

codeLinkString :: FilePath -> String
codeLinkString path = concat [ "<a href=\""
                             , path
                             , "\">"
                             , takeFileName path
                             , "</a> (<a href=\""
                             , path -<.> "pdf"
                             , "\">pdf</a>)" ]

codeMatch :: Pattern -> Routes -> Rules ()
codeMatch pat routes = do
  piimatch pat $ do
    route   routes
    compile copyFileCompiler

  piimatch pat $ version "raw" $ do
    route   (routes `composeRoutes` setExtension "pdf")
    compile enscriptCompiler

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
      examples <- loadAll @CopyFile (fromGlob fileglob .&&. hasNoVersion)
      let decompose path_components = case path_components of
            [_labs, class_name, example_file] | labs     -> ("lab" ++ take 2 class_name, example_file)
            [class_name, example_file]        | not labs -> (take 2 class_name, example_file)
            _                                            -> error "bad path for examples"

          filenames = map (decompose . splitPath . toFilePath . itemIdentifier) examples
          one_ex (num, name) = codeLinkString (num </> name)
          all_ex = "<ul>" ++ concat [ "<li>" ++ codeLinkString (num </> name) ++ "</li>"
                                    | (num, name) <- filenames ] ++ "</ul>"
      return all_ex

navbarContext :: Context String
navbarContext = defaultContext <>
                field "navbar" (const $ loadBody (fromFilePath "web/navbar.md"))

------------------
-- Calling enscript
-- Strongly based on copyFileCompiler

newtype EnscriptFile = EnscriptFile FilePath
  deriving (Binary, Eq, Ord, Show)

instance Writable EnscriptFile where
  write dst (Item _ (EnscriptFile src)) = do
    let syn_highlight_opt = case takeExtension src of
                              ".java" -> ["-Ejava"]
                              ".hs" -> []
                              _ -> error "Unknown file type"
    ps <- readProcess "enscript"
            (["-C", "-M", "Letter", "-T", "2", "-b", "$n", "-p", "-", "-f", "Courier9"] ++
             syn_highlight_opt ++
             [src])
            ""

    void $ readProcess "ps2pdf" ["-", dst] ps

enscriptCompiler :: Compiler (Item EnscriptFile)
enscriptCompiler = do
  identifier <- getUnderlying
  makeItem $ EnscriptFile $ toFilePath identifier

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

foldrWithKeyM :: forall m k a b. Monad m => (k -> a -> b -> m b) -> b -> M.Map k a -> m b
foldrWithKeyM f z = M.foldrWithKey go (return z)
  where
    go :: k -> a -> m b -> m b
    go k v mb = do
      b <- mb
      f k v b

foldrWithKeyM_ :: forall m k a b. Monad m => (k -> a -> m b) -> M.Map k a -> m ()
foldrWithKeyM_ f = foldrWithKeyM go ()
  where
    go :: k -> a -> () -> m ()
    go k v _ = void $ f k v
