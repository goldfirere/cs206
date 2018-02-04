package edu.brynmawr.cs.gradescope.test;

public class MultPoints extends TestResultAdapter
{
	private double multiplier;
	
	public MultPoints(double mult, TestResult res)
	{
		super(res);
		multiplier = mult;
	}
	
	@Override
	public double getMaxScore()
	{
		return multiplier * getInnerResult().getMaxScore();
	}
}
