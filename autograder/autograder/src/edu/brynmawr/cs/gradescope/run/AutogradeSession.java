package edu.brynmawr.cs.gradescope.run;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import org.json.*;

import edu.brynmawr.cs.gradescope.test.*;

/** A class to run a bunch of test cases
 * @author Richard Eisenberg
 *
 */
public class AutogradeSession
{
	private List<ProgramToTest> progs;
	
	private Map<ProgramToTest, List<TestResult>> results;
	private List<String> errors;

	private static class TestResult
	{
		IntegrationTest test;
		TestStatus status;
		
		TestResult(IntegrationTest t, TestStatus s)
		{
			test = t;
			status = s;
		}
	}
	
	private static abstract class TestStatus
	{
		final boolean success;

		TestStatus(boolean s)
		{
			success = s;
		}
		
		abstract String render(String inputOutputDoc);
	}
	
	private static class TestFailure extends TestStatus
	{
		String actualOutput;
		
		TestFailure(String output)
		{
			super(false);
			actualOutput = output;
		}
		
		@Override
		String render(String inputOutputDoc)
		{
			return "Test failed.\n\n" + inputOutputDoc + "\n" +
					   "Your output:\n" + actualOutput;
		}
	}
	
	private static class TestSuccess extends TestStatus
	{
		TestSuccess()
		{
			super(true);
		}
		
		@Override
		String render(String inputOutputDoc)
		{
			return "Test succeeded!\n\n" + inputOutputDoc;
		}
	}
	
	private static class TestTimeout extends TestStatus
	{
		TestTimeout()
		{
			super(false);
		}
		
		@Override
		String render(String inputOutputDoc)
		{
			return "Test timed out after 10 seconds.\n\n" + inputOutputDoc;
		}
	}
	
	public AutogradeSession(List<ProgramToTest> ps)
	{
		progs = ps;
		
		results = new HashMap<>();
		for(ProgramToTest prog : progs)
		{
			results.put(prog, new ArrayList<>());
		}
		
		errors = new ArrayList<>();
	}
	
	public void runAutograder()
	{
		System.out.println("Richard's Java autograder, v2");
		
		try
		{
			for(ProgramToTest prog : progs)
			{
				JavaFile jf;
				try
				{
					jf = new JavaFile(prog.getProgName());
				}
				catch(JavaFileNotFoundException e)
				{
					reportError("File not found for " + prog.getProgName() + ".java");
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
				
				for(IntegrationTest it : prog.getTests())
				{
					try
					{
						String output = jf.runProgram(it.getProgInput());

						String outputNoWS = Util.dropWhitespace(output);
						String expectedNoWS = Util.dropWhitespace(it.getExpectedOutput());
						if(outputNoWS.equalsIgnoreCase(expectedNoWS))
						{
							reportSuccess(prog, it);
						}
						else
						{
							reportFailure(prog, it, output);
						}

					}
					catch (ProgramTooSlowException e)
					{
						reportTimeout(prog, it);
					}
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
	
	private void reportTimeout(ProgramToTest prog, IntegrationTest it)
	{
		results.get(prog).add(new TestResult(it, new TestTimeout()));
	}

	private void reportFailure(ProgramToTest prog, IntegrationTest it, String output)
	{
		results.get(prog).add(new TestResult(it, new TestFailure(output)));
	}

	private void reportSuccess(ProgramToTest prog, IntegrationTest it)
	{
		results.get(prog).add(new TestResult(it, new TestSuccess()));
	}

	private void reportError(String msg)
	{
		errors.add(msg);
	}
	
	private JSONArray testToJSON(ProgramToTest prog)
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
			if(res.status.success)
			{
				score = maxScore;
			}
			else
			{
				score = 0;
			}
			test.put("score", score);
			test.put("max_score", maxScore);
			
			String inputOutputDoc = "Input:\n" + res.test.getProgInput() +
                              "Expected output:\n" + res.test.getExpectedOutput();
			
			test.put("output", prog.getProgName() + ":\n" + res.status.render(inputOutputDoc));
			
			test.put("visibility", "visible");
			
			if(res.test.isHidden())
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
			test.put("output", "Results of " + numHiddenTests + " hidden tests for " + prog.getProgName());
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
		
		Stream<JSONArray> tests = progs.stream().map(this::testToJSON);
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
