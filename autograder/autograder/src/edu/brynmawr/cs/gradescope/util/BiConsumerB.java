package edu.brynmawr.cs.gradescope.util;

import edu.brynmawr.cs.gradescope.java.*;

@FunctionalInterface
public interface BiConsumerB<A,B>
{
	void accept(A a, B b) throws BorkedException;
}
