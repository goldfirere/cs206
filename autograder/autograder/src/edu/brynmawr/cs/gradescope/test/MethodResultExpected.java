package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.expected.*;
import edu.brynmawr.cs.gradescope.java.*;

public class MethodResultExpected extends MethodResultReturn
{
	private Expected expected;
	
	public MethodResultExpected(Expected exp)
	{
		expected = exp;
	}
	
	@Override
	public String render()
	{
		return expected.toString();
	}

	@Override
	protected D<Double> matches(Object other) throws BorkedException
	{
		return expected.matches(other);
	}
	
  @Override
  public D<Object> exceptionToD(String doc)
    throws BorkedException
  {
    throw new BorkedException("Trying to convert a MethodResultExpected to a D");
  }
  
  @Override
  protected String getExtra()
  {
  		return expected.getExtra();
  }
}
