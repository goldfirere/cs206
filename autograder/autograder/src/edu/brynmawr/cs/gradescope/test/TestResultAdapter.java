package edu.brynmawr.cs.gradescope.test;

public abstract class TestResultAdapter extends TestResult
{
	private TestResult result;
	
	protected TestResultAdapter(TestResult res)
	{
		super(res.success());
		result = res;
	}
	
	@Override
	public double getMaxScore()
	{
		return result.getMaxScore();
	}
	
	@Override
	public boolean isHidden()
	{
		return result.isHidden();
	}
	
	@Override
	public String hiddenKey()
	{
		return result.hiddenKey();
	}
	
	@Override
	public String render()
	{
		return result.render();
	}
	
	public TestResult getInnerResult()
	{
		return result;
	}
}
