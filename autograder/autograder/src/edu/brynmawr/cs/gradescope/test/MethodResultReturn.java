package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;
import edu.brynmawr.cs.gradescope.util.*;

public abstract class MethodResultReturn implements MethodResult
{	
	@Override
	public TestResult checkResult(MethodResult other, String doc)
	  throws BorkedException
	{
		if(other instanceof MethodResultActual)
		{
			MethodResultActual actual = (MethodResultActual)other;
			
			String testDoc = doc + "Expected output: " + render() + "\n";
			D<Double> doesMatch = matches(actual.getActual());
			if(doesMatch.isValid())
			{
				if(doesMatch.get() > 0)
				{
					return new TestPartialSuccess(doesMatch.get(), testDoc + "\n" + getExtra());
				}
				else
				{
					return new TestFailure(testDoc, Util.render(actual.getActual()));
				}
			}
			else
			{
				return new TestError(doesMatch.getError());
			}
		}
		else
		{
			return new TestError(doc + "threw " + other.render() + ",\nbut it should have returned this:\n" + render());
		}
	}
	
	protected abstract D<Double> matches(Object other) throws BorkedException;
	protected String getExtra()
	{
		return "";
	}
}