package edu.brynmawr.cs.gradescope.expected;

import edu.brynmawr.cs.gradescope.java.*;

// represents an expected result of running a method
public interface Expected
{
	D<Double> matches(Object other) throws BorkedException;
	
	default String getExtra()
	{
		return "";
	}
}
