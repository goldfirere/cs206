package edu.brynmawr.cs.gradescope.test;

public class TestError extends TestResult
{
	private String desc;
	
	public TestError(String d)
	{
		super(false);
		desc = d;
	}
	
	@Override
	public String render()
	{
		return "Test failed to run: " + desc;
	}
}
