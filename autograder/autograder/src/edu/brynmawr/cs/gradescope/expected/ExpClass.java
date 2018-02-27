package edu.brynmawr.cs.gradescope.expected;

import edu.brynmawr.cs.gradescope.java.*;

public class ExpClass implements Expected
{
	private JavaClass<Object> klass;
	
	public ExpClass(JavaClass<Object> k)
	{
		klass = k;
	}
	
	@Override
	public D<Double> matches(Object other)
	{
		if(klass.isValid())
		{
			return D.of(klass.get().equals(other.getClass()) ? 1.0 : 0.0);
		}
		else
		{
			return D.err(klass.getError());
		}
	}
	
	@Override
	public String toString()
	{
		return "something of type " + klass.getName();
	}
}
