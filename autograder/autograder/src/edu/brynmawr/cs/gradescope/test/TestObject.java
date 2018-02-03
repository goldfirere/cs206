package edu.brynmawr.cs.gradescope.test;

import java.lang.reflect.*;
import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public class TestObject
{
	private String className;
	private String expectedOutput;
	
	private static Map<String, Method> testStringMethods;
	
	static
	{
		testStringMethods = new HashMap<>();
	}
	
	public TestObject(String cls, String out)
	{
		className = cls;
		expectedOutput = out;
	}
	
	public static void addTestString(String cls, Method m)
	{
		testStringMethods.put(cls, m);
	}
	
	public boolean checkMethodResult(Object other)
	  throws BorkedException, TestErrorException
	{
		if(other instanceof TestObject)
		{
			return expectedOutput.equals(((TestObject)other).expectedOutput);
		}
		else
		{
			Method testString = testStringMethods.get(className);
			if(testString == null)
			{
				throw new BorkedException("Can't find testString method for " + className + ".");
			}
			
			Object returned;
			try
			{
				returned = testString.invoke(other);
			}
			catch (IllegalAccessException e)
			{
				throw new TestErrorException(className + "'s testString method is not public.\n");
			}
			catch (IllegalArgumentException e)
			{
				throw new TestErrorException("Some method has the wrong return type.\nI was expecting a " + className + ", but your method produced a " + other.getClass().getName() + ".\n");
			}
			catch (InvocationTargetException e)
			{
				throw new TestErrorException(className + "'s testString method threw an exception: " + e.getCause().getMessage());
			}
			
			String actualOutput;
			try
			{
				actualOutput = (String)returned;
			}
			catch (ClassCastException e)
			{
				throw new TestErrorException("Place's testString method did not return a String.\nIt returned a " + returned.getClass().getName() + " instead.\n"); 
			}
			
			return expectedOutput.equals(actualOutput);
		}
	}
	
	@Override
	public String toString()
	{
		return expectedOutput;
	}
}
