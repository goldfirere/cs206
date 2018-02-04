package edu.brynmawr.cs.gradescope.run;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

import org.json.*;

import edu.brynmawr.cs.gradescope.java.*;
import edu.brynmawr.cs.gradescope.test.*;

/** A class to run a bunch of test cases
 * @author Richard Eisenberg
 *
 */
public class AutogradeSession
{
	private Map<String, JavaFile> files;
	
	private List<TestCase> tests;
	private List<TestResult> results;
	private List<String> errors;
	
	private ClassLoader classLoader;
	
	public AutogradeSession()
	{
		System.out.println("Richard's Java autograder, v4");
				
		files = new HashMap<>();
		tests = new ArrayList<>();
		results = new ArrayList<>();		
		errors = new ArrayList<>();
	}
	
	@FunctionalInterface
	public interface AutogradeSessionDriver
	{
		void drive() throws BorkedException;
	}
	
	public void withDriver(AutogradeSessionDriver d)
	{
		try
		{
			d.drive();
			runAutograder();
		}
		catch (BorkedException e)
		{
			System.out.println(e);
			reportError("There was an internal error in the autograder. Email Richard so he can look into it.");
		}
		finally
		{
			writeResults();
		}
	}
	
	public JavaFile loadJavaFile(String name) throws BorkedException
	{
		if(files.containsKey(name))
		{
			return files.get(name);
		}
		else
		{
			JavaFile jf = new JavaFile(name);
			try
			{
				jf.find();
			}
			catch(JavaFileNotFoundException e)
			{
				reportError("File not found for " + name + ".java");
			}
			
			try
			{
				jf.compile();
			}
			catch(JavaCompilationException e)
			{
				reportError(e.getErrorOutput());
			}
			
			if(classLoader == null && jf.isFound())
			{
				URL rootURL;
				try
				{
					rootURL = jf.getRoot().toURI().toURL();
				}
				catch (MalformedURLException e)
				{
					throw new BorkedException("Malformed URL during dynamic loading", e);
				}
				
				classLoader = new URLClassLoader(new URL[] { rootURL });
			}
			
			jf.load(classLoader);
			
			files.put(name, jf);
			
			return jf;
		}
	}
	
	public JavaExceptionClass loadExceptionClass(String clsName)
	  throws BorkedException
	{
		JavaFile jf = loadJavaFile(clsName);
		
		if(jf.isLoaded())
		{
			try
			{
				Class<? extends Throwable> klass = jf.getLoadedClass().asSubclass(Throwable.class);
				return new JavaExceptionClass(klass);
			}
			catch (ClassCastException e)
			{
				reportError(clsName + " is not an exception. It must be a subclass of Exception.");
				return new JavaExceptionClass("class " + clsName + " is not a subclass of Exception.");
			}
		}
		else
		{
			return new JavaExceptionClass("class " + clsName + " did not compile.");
		}
	}
	
	public void addTests(TestCase... newTests)
	{
		tests.addAll(Arrays.asList(newTests));
	}
	
	private void runAutograder() throws BorkedException
	{
		for(TestCase t : tests)
		{
			results.addAll(t.runTest());
		}
	}
	
	public void reportError(String msg)
	{
		errors.add(msg);
	}
	
	private Optional<JSONObject> testToJSON(Map<String, JSONObject> hiddenResults, TestResult result)
	{
		double maxScore = result.getMaxScore();

		double score;
		if(result.success())
		{
			score = maxScore;
		}
		else
		{
			score = 0;
		}

		if(result.isHidden())
		{	
			String key = result.hiddenKey();
			JSONObject hiddenObj;
			if(hiddenResults.containsKey(key))
			{
				hiddenObj = hiddenResults.get(key);
			}
			else
			{
				hiddenObj = new JSONObject();
				hiddenObj.put("max_score", 0.0);
				hiddenObj.put("score", 0.0);
				hiddenObj.put("visibility", "visible");
				hiddenResults.put(key, hiddenObj);
			}
			
			double prevMaxScore = hiddenObj.getDouble("max_score");
			hiddenObj.put("max_score", prevMaxScore + maxScore);
			
			double prevScore = hiddenObj.getDouble("score");
			hiddenObj.put("score", prevScore + score);
			
			return Optional.empty();
		}
		else
		{
			JSONObject test = new JSONObject();
						
			test.put("score", score);
			test.put("max_score", maxScore);
						
			test.put("output", result.render());
			
			test.put("visibility", "visible");

			return Optional.of(test);
		}
	}
	
	private void writeResults()
	{
		JSONObject obj = new JSONObject();
		
		if(errors.isEmpty())
		{
			obj.put("output", "Your submission compiled successfully. Huzzah!");
		}
		else
		{
			obj.put("output", String.join("\n", errors));
		}
		
		obj.put("visibility", "visible");
		obj.put("stdout_visibility", "hidden");
		
		JSONArray allTests = new JSONArray();
		Map<String, JSONObject> hiddenResults = new HashMap<>();
		for(TestResult result : results)
		{
			Optional<JSONObject> json = testToJSON(hiddenResults, result);
			if(json.isPresent())
			{
				allTests.put(json.get());
			}
		}
		
		for(Map.Entry<String, JSONObject> entry : hiddenResults.entrySet())
		{
			String key = entry.getKey();
			JSONObject value = entry.getValue();
			
			value.put("output", (key.length() == 0 ? "" : key + ": ") + "Results of hidden tests");
			allTests.put(value);
		}
		
		obj.put("tests", allTests);
		
		try
		{
				BufferedWriter out = new BufferedWriter(new FileWriter("/autograder/results/results.json"));
				out.write(obj.toString());
				out.flush();
				out.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
			// oh dear. Not much good we can do here.
		}
		
		System.out.println(obj); // just for debugging
	}
}
