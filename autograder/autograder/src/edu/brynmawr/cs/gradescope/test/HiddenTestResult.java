package edu.brynmawr.cs.gradescope.test;

public class HiddenTestResult extends TestResultAdapter
{
	private String key;
	
	public HiddenTestResult(TestResult res)
	{
		super(res);
		key = "";
	}
	
	public HiddenTestResult(String k, TestResult res)
	{
		super(res);
		key = k;
	}
	
	@Override
	public boolean isHidden()
	{
		return true;
	}
	
	@Override
	public String hiddenKey()
	{
		return key;
	}
}
