package edu.brynmawr.cs.gradescope.test;

public class TestTimeout extends TestResult
{
	private String testDoc;
	
	public TestTimeout(String d)
	{
		super(false);
		testDoc = d;
	}
	
	@Override
	public String render()
	{
		return "Test timed out after 10 seconds.\n\n" + testDoc;
	}
}