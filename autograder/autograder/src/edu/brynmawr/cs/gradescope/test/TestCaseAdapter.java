package edu.brynmawr.cs.gradescope.test;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public abstract class TestCaseAdapter extends TestCase
{
	private TestCase[] tests;
	
	public TestCaseAdapter(TestCase... ts)
	{
		tests = ts;
	}
	
	@Override
	public List<TestResult> runTest() throws BorkedException
	{
		List<TestResult> results = new ArrayList<>();
		for(TestCase t : tests)
		{
			results.addAll(t.runTest());
		}
		
		return adapt(results);
	}
	
	protected abstract List<TestResult> adapt(List<TestResult> results);
}
