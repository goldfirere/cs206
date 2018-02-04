package edu.brynmawr.cs.gradescope.test;

import java.util.*;

public class TestCaseSet extends TestCaseAdapter
{
	private double points;
	
	public TestCaseSet(double pts, TestCase... ts)
	{
		super(ts);
		points = pts;
	}
	
	@Override
	protected List<TestResult> adapt(List<TestResult> results)
	{
		double origTotal = 0;
		for(TestResult res : results)
		{
			origTotal += res.getMaxScore();
		}
		
		double factor = points / origTotal;
		
		List<TestResult> scaled = new ArrayList<>();
		for(TestResult res : results)
		{
			scaled.add(new MultPoints(factor, res));
		}
		
		return scaled;
	}
}
