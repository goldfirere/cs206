package edu.brynmawr.cs.gradescope.util;

@FunctionalInterface
public interface TriConsumer<A,B,C>
{
	void accept(A a, B b, C c);
}
