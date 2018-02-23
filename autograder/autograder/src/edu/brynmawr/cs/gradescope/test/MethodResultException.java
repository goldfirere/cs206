package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;

public class MethodResultException implements MethodResult
{
	private JavaClass<Throwable> exception;
	
	public MethodResultException(JavaClass<Throwable> exc)
	{
		exception = exc;
	}

	@Override
	public TestResult checkResult(MethodResult other, String doc)
	  throws BorkedException
	{
		if(other instanceof MethodResultException)
		{
			MethodResultException actual = (MethodResultException)other;
			
			D<Boolean> isSuper = exception.f2(Class::isAssignableFrom, actual.exception); 
			
			if(isSuper.isValid())
			{
				if(isSuper.get())
				{
					return new TestSuccess(doc + "threw exception " + actual.exception.getName() + ", as expected.\n");
				}
				else
				{
					return new TestError(doc + "threw exception " + actual.exception.getName() + ",\nbut it should have thrown " + exception.getName() + ".\n");
				}
			}
			else
			{
				return new TestError("Cannot test " + doc + isSuper.getError());
			}
		}
		else
		{
			return new TestError(doc + "was supposed to throw exception " + exception.getName() + ",\nbut it returned this instead:\n" + other.render());
		}
	}

	@Override
	public String render()
	{
		return "exception " + exception.getName();
	}
	
  @Override
  public D<Object> exceptionToD(String doc)
  {
          return D.err(doc + " produced an unexpected " + render());
  }
}