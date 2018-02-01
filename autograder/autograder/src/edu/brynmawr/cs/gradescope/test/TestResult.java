package edu.brynmawr.cs.gradescope.test;

public abstract class TestResult
{
	private final boolean success;
	private final TestCase test;
	
	public TestResult(TestCase t, boolean s)
	{
		success = s;
		test = t;
	}
	
	public abstract String render();

	/**
	 * @return the success
	 */
	public boolean success()
	{
		return success;
	}

	/**
	 * @return the test
	 */
	public TestCase getTest()
	{
		return test;
	}
}