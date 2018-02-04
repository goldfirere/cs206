package edu.brynmawr.cs.gradescope.test;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public abstract class TestCase
{
	public abstract List<TestResult> runTest()
	  throws BorkedException;
}
