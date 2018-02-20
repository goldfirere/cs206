package edu.brynmawr.cs.gradescope.test;

public abstract class TestResult
{
	private final double percentage;
	
	public TestResult(boolean s)
	{
		percentage = s ? 1 : 0;
	}
	
	public TestResult(double p)
	{
		percentage = p;
	}
	
	public abstract String render();

	/**
	 * @return the success
	 */
	public double getPercentage()
	{
		return percentage;
	}

	public double getMaxScore()
	{
		return 1.0;
	}
	
	public boolean isHidden()
	{
		return false;
	}
	
	public String hiddenKey()
	{
		return null;
	}
}