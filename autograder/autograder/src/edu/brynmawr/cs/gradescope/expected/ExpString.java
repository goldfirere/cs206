package edu.brynmawr.cs.gradescope.expected;

import edu.brynmawr.cs.gradescope.java.*;

public class ExpString implements Expected
{
	private JavaClass<Object> klass;
	private String string;
	
	public ExpString(JavaClass<Object> k, String s)
	{
		klass = k;
		string = s;
	}
	
	@Override
	public D<Double> matches(Object other)
	{
		if(klass.isValid())
		{
			return D.of(klass.get().equals(other.getClass()) &&
		              string.equals(other.toString()) ? 1.0 : 0.0);
		}
		else
		{
			return D.err(klass.getError());
		}
	}
	
	@Override
	public String toString()
	{
		return string;
	}
}
