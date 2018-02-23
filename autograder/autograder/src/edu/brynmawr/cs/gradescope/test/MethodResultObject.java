package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;
import edu.brynmawr.cs.gradescope.util.*;

public class MethodResultObject extends MethodResultReturn
{
	private Object result;
	
	public MethodResultObject(Object res)
	{
		result = res;
	}

	@Override
	protected D<Double> matches(Object other)
	{
		return D.of(Util.matches(result, other) ? 1.0 : 0.0);
	}

	@Override
	public String render()
	{
		return Util.render(result);
	}
	
  @Override
  public D<Object> exceptionToD(String doc)
  {
          return D.of(result);
  }
}
