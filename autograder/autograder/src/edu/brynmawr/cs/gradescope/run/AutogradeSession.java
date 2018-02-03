package edu.brynmawr.cs.gradescope.run;

import java.io.*;
import java.lang.reflect.*;
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
	private List<ClassToTest> classes;
	
	private Map<ClassToTest, List<TestResult>> results;
	private List<String> errors;
	
	private boolean borked; // don't run tests if this is true

	public AutogradeSession(ClassToTest... cs)
	{
		System.out.println("Richard's Java autograder, v3");
				
		classes = new ArrayList<>();
		results = new HashMap<>();		
		errors = new ArrayList<>();
		borked = false;
		
		addClassesToTest(cs);
	}
	
	public void loadTestStringMethods(String... clses)
	{
		for(String cls : clses)
		{
			try
			{
				JavaFile jf = new JavaFile(cls);
				Method testStringMethod = jf.getMethod("testString");
				TestObject.addTestString(cls, testStringMethod);
			}
			catch (BorkedException e)
			{
				System.out.println(e);
				reportError("There was an internal error in the autograder. Email Richard so he can look into it.");
				borked = true;
			}
			catch (JavaFileNotFoundException e)
			{
				reportError("File not found for " + cls + ".java");
				borked = true;
			}
			catch (NoSuchMethodException e)
			{
				reportError("Could not find testString method in " + cls + ".");
				borked = true;
			}
		}
	}
	
	public void addClassesToTest(ClassToTest... cs)
	{
		classes.addAll(Arrays.asList(cs));
		for(ClassToTest cls : classes)
		{
			results.put(cls, new ArrayList<>());
		}
	}
	
	public void runAutograder()
	{
		if(!borked)
		{
			try
			{
				for(ClassToTest cls : classes)
				{
					JavaFile jf;
					try
					{
						jf = new JavaFile(cls.getClassName());
					}
					catch(JavaFileNotFoundException e)
					{
						reportError("File not found for " + cls.getClassName() + ".java");
						continue;
					}
					
					try
					{
						jf.compile();
					}
					catch(JavaCompilationException e)
					{
						reportError(e.getErrorOutput());
						continue;
					}
					
					for(TestCase t : cls.getTests())
					{
						results.get(cls).addAll(t.runTest(jf));
					}
				}
			}
			catch(BorkedException e)
			{
				System.out.println(e);
				reportError("There was an internal error in the autograder. Email Richard so he can look into it.");
			}
		}
		
		writeResults();
	}
	
	public void reportError(String msg)
	{
		errors.add(msg);
	}
	
	private JSONArray testToJSON(ClassToTest prog)
	{
		JSONArray arr = new JSONArray();
		
		List<TestResult> testResults = results.get(prog);
		double hiddenPoints = 0.0;
		double maxHiddenPoints = 0.0;
		int numHiddenTests = 0;
		double totalWeight = testResults.stream()
		                                .map(TestResult::getTest)
		                                .mapToDouble(TestCase::getWeight)
		                                .sum();

		for(TestResult res : testResults)
		{
			double maxScore = prog.getNumPoints() * res.getWeight() / totalWeight;
			
			JSONObject test = new JSONObject();
						
			double score;
			if(res.success())
			{
				score = maxScore;
			}
			else
			{
				score = 0;
			}
			test.put("score", score);
			test.put("max_score", maxScore);
						
			test.put("output", prog.getClassName() + ":\n" + res.render());
			
			test.put("visibility", "visible");
			
			if(res.getTest().isHidden())
			{
				hiddenPoints += score;
				maxHiddenPoints += maxScore;
				numHiddenTests++;
				System.out.println(test); // only I can see it
			}
			else
			{
				arr.put(test);
			}
		}
		
		if(numHiddenTests > 0)
		{
			JSONObject test = new JSONObject();
			
			test.put("score", hiddenPoints);
			test.put("max_score", maxHiddenPoints);
			test.put("output", "Results of " + numHiddenTests + " hidden tests for " + prog.getClassName());
			test.put("visibility", "visible");
			arr.put(test);
		}
		
		return arr;
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
		
		Stream<JSONArray> tests = classes.stream().map(this::testToJSON);
		JSONArray allTests = tests.collect(new JSONArrayConcatCollector());
		
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
