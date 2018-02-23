package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;
import edu.brynmawr.cs.gradescope.util.*;

public class MethodResultD implements MethodResult
{
	private D<Object> resultD;
	
	MethodResultD(D<Object> d)
	{
		if(d == null)
		{
			resultD = D.of(null);
		}
		else
		{
			resultD = d;
		}
	}

	@Override
	public TestResult checkResult(MethodResult other, String doc)
	  throws BorkedException
	{
		if(resultD.isValid())
		{
			return new MethodResultObject(resultD.get()).checkResult(other, doc);
		}
		else
		{
			return new TestError(resultD.getError());
		}
	}

	@Override
	public String render()
	{
		if(resultD.isValid())
		{
			return Util.render(resultD.get());
		}
		else
		{
			return "<<invalid object>>";
		}
	}
	
  @Override
  public D<Object> exceptionToD(String doc)
  {
          return resultD;
  }
}