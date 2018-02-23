package edu.brynmawr.cs.gradescope.util;

import edu.brynmawr.cs.gradescope.java.*;

@FunctionalInterface
public interface TriConsumerB<A,B,C>
{
	void accept(A a, B b, C c) throws BorkedException;
}
