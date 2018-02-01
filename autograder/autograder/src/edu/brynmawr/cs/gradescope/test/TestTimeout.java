package edu.brynmawr.cs.gradescope.test;

public class TestTimeout extends TestResult
{
	private String testDoc;
	
	public TestTimeout(TestCase test, String d)
	{
		super(test, false);
		testDoc = d;
	}
	
	@Override
	public String render()
	{
		return "Test timed out after 10 seconds.\n\n" + testDoc;
	}
}