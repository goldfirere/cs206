package edu.brynmawr.cs.gradescope.run;

import java.io.*;
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

	public AutogradeSession(List<ClassToTest> cs)
	{
		classes = cs;
		
		results = new HashMap<>();
		for(ClassToTest cls : classes)
		{
			results.put(cls, new ArrayList<>());
		}
		
		errors = new ArrayList<>();
	}
	
	public void runAutograder()
	{
		System.out.println("Richard's Java autograder, v3");
		
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
					TestResult result = t.runTest(jf);
					results.get(cls).add(result);
				}
			}
		}
		catch(BorkedException e)
		{
			System.out.println(e);
			reportError("There was an internal error in the autograder. Email Richard so he can look into it.");
		}
		
		writeResults();
	}
	
	private void reportError(String msg)
	{
		errors.add(msg);
	}
	
	private JSONArray testToJSON(ClassToTest prog)
	{
		JSONArray arr = new JSONArray();
		
		int numTests = results.get(prog).size();
		double hiddenPoints = 0.0;
		int numHiddenTests = 0;
		double maxScore = prog.getNumPoints() / numTests;

		for(TestResult res : results.get(prog))
		{
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
			test.put("max_score", maxScore * numHiddenTests);
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
