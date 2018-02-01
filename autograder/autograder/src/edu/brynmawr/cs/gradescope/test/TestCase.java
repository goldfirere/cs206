package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;

public abstract class TestCase
{
	private boolean hidden;
	
	public TestCase(boolean h)
	{
		hidden = h;
	}
	
	public abstract TestResult runTest(JavaFile jf)
	  throws BorkedException;
	
	public boolean isHidden()
	{
		return hidden;
	}
}
