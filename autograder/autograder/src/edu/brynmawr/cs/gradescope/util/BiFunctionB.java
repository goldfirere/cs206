package edu.brynmawr.cs.gradescope.util;

import edu.brynmawr.cs.gradescope.java.*;

public interface BiFunctionB<A,B,R>
{
	R apply(A a, B b) throws BorkedException;
}
