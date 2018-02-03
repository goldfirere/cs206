package edu.brynmawr.cs.gradescope.test;

public class TestError extends TestResult
{
	private String desc;
	private double weight;
	
	public TestError(TestCase test, String d)
	{
		this(test, d, 1);
	}
	
	public TestError(TestCase test, String d, double w)
	{
		super(test, false);
		
		desc = d;
		weight = w;
	}
	
	@Override
	public String render()
	{
		return "Test failed to run: " + desc;
	}
	
	@Override
	public double getWeight()
	{
		return weight;
	}
}
