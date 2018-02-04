package edu.brynmawr.cs.gradescope.test;

public abstract class TestResult
{
	private final boolean success;
	
	public TestResult(boolean s)
	{
		success = s;
	}
	
	public abstract String render();

	/**
	 * @return the success
	 */
	public boolean success()
	{
		return success;
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