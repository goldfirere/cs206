package edu.brynmawr.cs.gradescope.java;

public class JavaExceptionClass extends D<Class<? extends Throwable>>
{
	public JavaExceptionClass(String error)
	{
		super(false, error);
	}
	
	public JavaExceptionClass(Class<? extends Throwable> exc)
	{
		exception = exc;
	}
	
	public boolean isValid()
	{
		return exception != null;
	}
	
	public String getError()
	{
		return errorMessage;
	}
	
	public Class<? extends Throwable> getException()
	{
		return exception;
	}
}
