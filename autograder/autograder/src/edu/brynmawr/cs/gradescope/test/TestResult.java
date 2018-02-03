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

	/** Returns the weight of this test (how many times it should count).
	 * @return The test weight. The default value is 1.
	 */
	public double getWeight()
	{
		return 1;
	}
}