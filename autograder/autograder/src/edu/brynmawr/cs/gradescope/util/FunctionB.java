package edu.brynmawr.cs.gradescope.util;

import edu.brynmawr.cs.gradescope.java.*;

@FunctionalInterface
public interface FunctionB<A,R>
{
	public R apply(A a) throws BorkedException;
}
