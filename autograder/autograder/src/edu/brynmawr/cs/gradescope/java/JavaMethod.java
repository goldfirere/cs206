package edu.brynmawr.cs.gradescope.java;

import java.lang.reflect.*;

public class JavaMethod
{
	private String errorMessage;
	private Method meth;
	
	public JavaMethod(String error)
	{
		errorMessage = error;
		meth = null;
	}
	
	public JavaMethod(Method m)
	{
		meth = m;
	}
	
	public boolean isValid()
	{
		return meth != null;
	}
	
	public Method getMethod()
	{
		return meth;
	}
	
	public String getError()
	{
		return errorMessage;
	}
}
