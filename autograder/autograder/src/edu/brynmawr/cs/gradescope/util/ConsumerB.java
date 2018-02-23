package edu.brynmawr.cs.gradescope.util;

import edu.brynmawr.cs.gradescope.java.*;

@FunctionalInterface
public interface ConsumerB<A>
{
	void accept(A a) throws BorkedException;
}
