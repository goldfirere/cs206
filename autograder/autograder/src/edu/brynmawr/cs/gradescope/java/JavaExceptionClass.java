package edu.brynmawr.cs.gradescope.java;

public class JavaExceptionClass
{
	private String errorMessage;
	private Class<? extends Throwable> exception;
	
	public JavaExceptionClass(String error)
	{
		errorMessage = error;
		exception = null;
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
