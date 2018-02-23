package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;
import edu.brynmawr.cs.gradescope.util.*;

public class MethodResultActual extends MethodResultReturn
{
	public Object actual;
	
	public MethodResultActual(Object o)
	{
		actual = o;
	}
	
	@Override
	public String render()
	{
		return Util.render(actual);
	}

	@Override
	protected D<Double> matches(Object other) throws BorkedException
	{
		throw new BorkedException("MethodResultActual.matches called on " + render());
	}
	
	public Object getActual()
	{
		return actual;
	}

	@Override
	public D<Object> exceptionToD(String doc)
	{
		return D.of(actual);
	}
	
}
