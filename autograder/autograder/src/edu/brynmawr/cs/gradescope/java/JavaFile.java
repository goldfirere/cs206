package edu.brynmawr.cs.gradescope.java;

import java.io.*;
import java.util.stream.*;

import edu.brynmawr.cs.gradescope.util.*;

import java.util.*;
import java.util.function.*;

/** Represents a Java file on disk.
 * 
 * @author Richard Eisenberg
 */
public class JavaFile
{
	private String className; // the desired name of the class in the file
	private Package pack; // the declared name of the package
	
	private File path; // where is this file (absolute pathname)
	private File root; // where the package structure is rooted
	
	private JavaClass<Object> klass; // the loaded class
	
	private enum FileState {
		NAMED,
		FOUND,
		COMPILED,
		LOADED
	}
	
	private FileState state;
	
	public JavaFile(String name)
	{
		className = name;
		
		pack = null;
		path = null;
		root = null;
		klass = null;
		
		state = FileState.NAMED;
	}
	
	/** Find a new JavaFile for a given class. Always searches in
	 * the /autograder/submission directory.
	 * 
	 * @throws JavaFileNotFoundException If we can't find the file
	 * @throws BorkedException If something else goes drastically wrong
	 */
	public void find() throws JavaFileNotFoundException,
	                          BorkedException
	{
		if(state != FileState.NAMED)
		{
			throw new BorkedException("JavaFile " + className + " being found more than once.");
		}
		
		try
		{
			File submissionDir = new File("/autograder/submission");
			
			Runtime rt = Runtime.getRuntime();
			Process finder = rt.exec(new String[] { "find", ".", "-name", className + ".java" },
					                     null, submissionDir);
			InputStream finderResults = finder.getInputStream();
			int finderExitCode = finder.waitFor();
			
			if(finderExitCode != 0)
			{
				throw new BorkedException("Call to `find` failed with code " + finderExitCode);
			}
			
			Stream<String> finderLinesStream = new BufferedReader(new InputStreamReader(finderResults)).lines();
			String[] finderLines = finderLinesStream.toArray(String[]::new);
			
			if(finderLines.length != 1)
			{
				throw new JavaFileNotFoundException(className, finderLines.length + " files matching " + className + ".java found.");
			}
			
			path = new File(submissionDir, finderLines[0]);
		}
		catch(IOException e)
		{
			throw new BorkedException("Failed to spawn `find` process", e);
		}
		catch(InterruptedException e)
		{
			throw new BorkedException("Somehow got interrupted while running `find`", e);
		}
		
		try
		{
			ConsIterator<String> fileLines = new ConsIterator<>(new BufferedReader(new FileReader(path)).lines().iterator());
			discardComments(fileLines);
			String firstToken = getToken(fileLines);
			
			if(firstToken.equals("package"))
			{
				pack = getPackage(fileLines);
			}
			else
			{
				pack = new Package();
			}
		}
		catch(IOException e)
		{
			throw new BorkedException("Failed to read from " + path, e);
		}
		catch(JavaParseException e)
		{
			// set the package to empty to avoid cascade of errors
			pack = new Package();
		}
		
		int numPackageComponents = pack.getNumComponents();
		File directory = path.getParentFile();
		root = new File(directory, String.join("/", Collections.nCopies(numPackageComponents, "..")));
		state = FileState.FOUND;
	}
	
	public boolean isFound()
	{
		return state != FileState.NAMED;
	}

	private Package getPackage(ConsIterator<String> fileLines)
	  throws JavaParseException
	{	
		List<String> components = new ArrayList<String>();
		String token;
		do
		{
			discardComments(fileLines);
			token = getToken(fileLines);
			
			components.add(token);
			
			discardComments(fileLines);
			token = getToken(fileLines);
		} while(token.equals("."));
		
		return new Package(components);
	}

	private String getToken(ConsIterator<String> fileLines)
	  throws JavaParseException
	{
		if(fileLines.hasNext())
		{
			String line = fileLines.next();
			if(line.length() == 0)
			{
				throw new JavaParseException(line);
			}
			
			Predicate<Character> recognizeToken;
			if(Character.isJavaIdentifierStart(line.charAt(0)))
			{
				recognizeToken = Character::isJavaIdentifierPart;
			}
			else
			{
				recognizeToken = ((Predicate<Character>)Character::isWhitespace).negate().and(((Predicate<Character>)Character::isJavaIdentifierStart).negate());
			}
				
			int endIndex = Util.stringFind(line, recognizeToken.negate());
			if(endIndex == -1)
			{
				return line;
			}
			else
			{
				fileLines.cons(line.substring(endIndex));
				return line.substring(0, endIndex);
			}
		}
		else
		{
			throw new JavaParseException();
		}
	}

	private static void discardComments(ConsIterator<String> fileLines)
	{
		String trimmed;
		boolean inComment = false;
		do
		{
		  if(fileLines.hasNext())
		  {
		  		String line = fileLines.next();
		  		if(inComment)
		  		{
		  			Optional<String> searchResult = findCommentTerminator(line);
		  			if(!searchResult.isPresent())
		  			{
		  				trimmed = ""; // so that the loop goes around again
		  				continue; // no terminator found
		  			}
		  			else
		  			{
		  				line = searchResult.get();
		  			}
		  		}
		  	
		  		Pair<String, Boolean> discardResult = discardComments(line);
		  		trimmed = discardResult.fst();
		  		inComment = discardResult.snd();
		  }
		  else
		  {
		  	  // file contains only comments
		  		return;
		  }
		} while(trimmed.isEmpty());
				
		fileLines.cons(trimmed);
	}

	private static Pair<String, Boolean> discardComments(String string)
	{
		int index = 0;
		while(index < string.length())
		{
			if(Character.isWhitespace(string.charAt(index)))
			{
				index++;
				continue;
			}
			
			if(index + 1 < string.length())
			{
				String twoChars = string.substring(index, index+2);
				if(twoChars.equals("//"))
				{
					return new Pair<>("", false);
				}
				else if(twoChars.equals("/*"))
				{
					Optional<String> result = findCommentTerminator(string.substring(index + 2));
					if(result.isPresent())
					{
						// this is gross. But so is imperative programming.
						string = result.get();
						index = 0;
						continue;
					}
					else
					{
						return new Pair<>("", true);
					}
				}
			}
			
			// next char must be a non-comment, non-whitespace char.
			return new Pair<>(string.substring(index), false);
		}
		
		// must have gotten to the end.
		return new Pair<>("", false);
	}

	private static Optional<String> findCommentTerminator(String string)
	{
		int index = string.indexOf("*/");
		if(index >= 0)
		{
			return Optional.of(string.substring(index + 2));
		}
		else
		{
			return Optional.empty();
		}
	}
	
	@Override
	public String toString()
	{
		return path + ": class " + className + " in " + pack;
	}
	
	public File getRoot()
	{
		return root;
	}
	
	/** Compile the file.
	 * @throws JavaCompilationException Compiler error.
	 * @throws BorkedException Drastic system failure.
	 */
	public void compile() throws JavaCompilationException, BorkedException
	{
		if(!isFound())
		{
			return; // we don't have the file; avoid further errors
		}
		
		try
		{
			Runtime rt = Runtime.getRuntime();
			Process javac = rt.exec(new String[] { "javac", "-classpath", "/autograder/source/junit/*:" + root.getAbsolutePath(), path.getAbsolutePath() });
			int javacCode = javac.waitFor();
			
			if(javacCode != 0)
			{
				throw new JavaCompilationException(className, new BufferedReader(new InputStreamReader(javac.getErrorStream())).lines());
			}
		}
		catch (IOException e)
		{
			throw new BorkedException("Failure spawning `javac` process", e);
		}
		catch (InterruptedException e)
		{
			throw new BorkedException("We got interruped while running `javac`.", e);
		}
		
		state = FileState.COMPILED;
	}
	
	/** Run the JavaFile as an application
	 * @param input The input to the program
	 * @return Program output
	 * @throws BorkedException Drastic system failure
	 */
	public D<String> runProgram(String input) throws BorkedException
	{
		if(state.compareTo(FileState.COMPILED) < 0)
		{
			return D.err("Compilation error prevents running " + className);
		}
		
		Process java;
		try
		{
			Runtime rt = Runtime.getRuntime();
			java = rt.exec(new String[] { "java", "-classpath", root.getAbsolutePath(), 
					pack.getNumComponents() > 0 ? pack.getPackage() + "." + className : className });
		}
		catch(IOException e)
		{
			throw new BorkedException("Error spawning `java`", e);
		}
			
		try
		{
			// send requested input:
			Writer w = new OutputStreamWriter(java.getOutputStream());
			w.write(input);
			w.flush();
		}
		catch(IOException e)
		{
			throw new BorkedException("Couldn't send input to subprocess", e);
		}
		
		boolean terminated;
		try
		{
			terminated = java.waitFor(ProgramTooSlowException.TIMEOUT, ProgramTooSlowException.TIMEOUT_UNIT);
		}
		catch(InterruptedException e)
		{
			throw new BorkedException("Interrupted while user program was running", e);
		}
		
		if(!terminated)
		{
			return D.err("Running " + className + " timed out after 10 seconds.");
		}
		
		int exitCode = java.exitValue();
		if(exitCode != 0)
		{
			String errors = new BufferedReader(new InputStreamReader(java.getErrorStream())).lines().collect(Collectors.joining("\n"));
			return D.err("There appears to be no `main` method in " + className + ":\n" + errors);
		}
		
		// return output:
		return D.of(new BufferedReader(new InputStreamReader(java.getInputStream())).lines().collect(Collectors.joining("\n")));
	}
	
	/** Loads this class, making it available for method lookup.
	 */
	public void load(ClassLoader loader) throws BorkedException
	{
		if(state != FileState.COMPILED)
		{
			klass = JavaClass.err(className, className + " did not compile.");
			return;
		}
		
		String fullyQualified = pack.getNumComponents() == 0 ? className :
													pack.getPackage() + "." + className;
		try
		{
			klass = JavaClass.of(loader.loadClass(fullyQualified));
		}
		catch (ClassNotFoundException e)
		{
			throw new BorkedException("Can't find class " + className + " during loading", e);
		}
	}
	
	/** An Iterator that can be consed back onto
	 * @author Richard Eisenberg
	 *
	 * @param <T> The element type stored here
	 */
	private class ConsIterator<T> implements Iterator<T> 
	{
		private Stack<T> consed; // Extra elements consed onto the beginning
		private Iterator<T> iter; // The underlying iterator
		
		/** Construct a ConsIterator based on an existing iterator
		 * @param base The original element iterator
		 */
		public ConsIterator(Iterator<T> base)
		{
			consed = new Stack<T>();
			iter = base;
		}

		@Override
		public boolean hasNext()
		{
			return !consed.isEmpty() || iter.hasNext();
		}

		@Override
		public T next()
		{
			if(consed.isEmpty())
			{
				return iter.next();
			}
			else
			{
				return consed.pop();
			}
		}
		
		/** Add a new element on the beginning of this iterator
		 * @param t The new element
		 */
		public void cons(T t)
		{
			consed.push(t);
		}
	}

	public String getName()
	{
		return className;
	}
	
	public boolean isLoaded()
	{
		return state == FileState.LOADED;
	}
	
	public JavaClass<Object> getLoadedClass()
	{
		return klass;
	}
}
