package edu.brynmawr.cs.gradescope.test;

public class TestFailure extends TestResult
{
	private String testDoc;
	private String actualOutput;
	
	public TestFailure(String td, String output)
	{
		super(false);
		testDoc = td;
		actualOutput = output;
	}
	
	@Override
	public String render()
	{
		return "Test failed.\n\n" + testDoc + "\n" +
				   "Your output:\n" + actualOutput;
	}
}