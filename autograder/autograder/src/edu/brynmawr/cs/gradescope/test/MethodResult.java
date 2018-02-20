package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;
import edu.brynmawr.cs.gradescope.util.*;

public interface MethodResult
{
	boolean isException();
	
	// The doc should be something like "calling method foo(bar)\n"
	TestResult checkResult(MethodResult other, String doc);
	
	String render();
	
	D<Object> exceptionToD(String doc);
	
	public static MethodResult returned(Object obj)
	{
		return new MethodResultReturn(obj);
	}
	
	public static MethodResult exception(JavaClass<Throwable> exc)
	{
		return new MethodResultException(exc);
	}
	
	public static MethodResult d(D<Object> d)
	{
		return new MethodResultD(d);
	}
}

class MethodResultReturn implements MethodResult
{
	private Object result;
	
	MethodResultReturn(Object res)
	{
		result = res;
	}
	
	@Override
	public boolean isException()
	{
		return false;
	}
	
	@Override
	public TestResult checkResult(MethodResult other, String doc)
	{
		if(other instanceof MethodResultReturn)
		{
			MethodResultReturn actual = (MethodResultReturn)other;
			
			String testDoc = doc + "Expected output: " + render() + "\n";
			if(Util.matches(result, actual.result))
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
			return new TestError(doc + "threw " + other.render() + ",\nbut it should have returned this:\n" + render());
		}
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

class MethodResultException implements MethodResult
{
	private JavaClass<Throwable> exception;
	
	MethodResultException(JavaClass<Throwable> exc)
	{
		exception = exc;
	}

	@Override
	public boolean isException()
	{
		return true;
	}

	@Override
	public TestResult checkResult(MethodResult other, String doc)
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

class MethodResultD implements MethodResult
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
	public boolean isException()
	{
		return false;
	}

	@Override
	public TestResult checkResult(MethodResult other, String doc)
	{
		if(resultD.isValid())
		{
			return new MethodResultReturn(resultD.get()).checkResult(other, doc);
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