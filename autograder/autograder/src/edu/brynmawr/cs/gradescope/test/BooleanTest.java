package edu.brynmawr.cs.gradescope.test;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public class BooleanTest extends TestCase
{
	private String description;
	private D<Boolean> answer;
	
	public BooleanTest(String s, D<Boolean> b)
	{
		description = s;
		answer = b;
	}
	
	@Override
	public List<TestResult> runTest() throws BorkedException
	{
		TestResult result;
		if(answer.isValid())
		{
			if(answer.get())
			{
				result = new TestSuccess(description);
			}
			else
			{
				result = new TestFailure(description, "This is not the case.");
			}
		}
		else
		{
			result = new TestError("Cannot run test:\n" + description + "\n" + answer.getError());
		}
		
		return Collections.singletonList(result);
	}
	
}
