package edu.brynmawr.cs.gradescope.test;

public class TestSuccess extends TestResult
{
	private String doc;
	
	public TestSuccess(String d)
	{
		super(true);
		doc = d;
	}
	
	@Override
	public String render()
	{
		return "Test succeeded!\n\n" + doc;
	}
}