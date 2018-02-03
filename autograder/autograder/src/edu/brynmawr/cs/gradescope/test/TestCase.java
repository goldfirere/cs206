package edu.brynmawr.cs.gradescope.test;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public abstract class TestCase
{
	private boolean hidden;
	
	public TestCase(boolean h)
	{
		hidden = h;
	}
	
	public abstract List<TestResult> runTest(JavaFile jf)
	  throws BorkedException;
	
	public abstract double getWeight();
	
	public boolean isHidden()
	{
		return hidden;
	}
}
