package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;

@FunctionalInterface
public interface BorkedSupplier<T>
{
	T get() throws BorkedException;
}