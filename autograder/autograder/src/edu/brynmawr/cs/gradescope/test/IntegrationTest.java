package edu.brynmawr.cs.gradescope.test;

public class IntegrationTest
{
	private String progInput;
	private String expectedOutput;
	private boolean hidden = false;
	
	public IntegrationTest(String in, String out)
	{
		progInput = in;
		expectedOutput = out;
	}
	
	public IntegrationTest(String in, String out, boolean hide)
	{
		progInput = in;
		expectedOutput = out;
		hidden = hide;
	}
	
	/**
	 * @return the progInput
	 */
	public String getProgInput()
	{
		return progInput;
	}

	/**
	 * @return the expectedOutput
	 */
	public String getExpectedOutput()
	{
		return expectedOutput;
	}
	
	public boolean isHidden()
	{
		return hidden;
	}
}
