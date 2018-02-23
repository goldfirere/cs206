package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;

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
					return new TestSuccess(testDoc);
				}
				else
				{
					return new TestFailure(testDoc, render());
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
}