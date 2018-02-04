package edu.brynmawr.cs.gradescope.test;

import java.util.*;

public class HiddenTestCase extends TestCaseAdapter
{
	private String hiddenKey;
	
	public HiddenTestCase(TestCase... ts)
	{
		super(ts);
		hiddenKey = "";
	}
	
	public HiddenTestCase(String hk, TestCase... ts)
	{
		super(ts);
		hiddenKey = hk;
	}
	
	@Override
	protected List<TestResult> adapt(List<TestResult> results)
	{
		List<TestResult> hidden = new ArrayList<>();
		
		for(TestResult res : results)
		{
			hidden.add(new HiddenTestResult(hiddenKey, res));
		}
		
		return hidden;
	}
}
