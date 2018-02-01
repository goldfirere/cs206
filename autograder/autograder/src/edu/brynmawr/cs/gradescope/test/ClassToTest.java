package edu.brynmawr.cs.gradescope.test;

import java.util.*;

/** Represents a program under test
 * @author Richard Eisenberg
 *
 */
public class ClassToTest
{
	private String progName;
	private List<TestCase> tests;
	
	private double numPoints;
	
	public ClassToTest(String className, double points, TestCase... ts)
	{
		progName = className;
		numPoints = points;
		tests = Arrays.asList(ts);
	}

	/**
	 * @return the program name
	 */
	public String getClassName()
	{
		return progName;
	}

	/**
	 * @return the list of integration tests
	 */
	public List<TestCase> getTests()
	{
		return tests;
	}
	
	/**
	 * @return the number of points this program is worth
	 */
	public double getNumPoints()
	{
		return numPoints;
	}
	
	@Override
	public int hashCode()
	{
		return progName.hashCode();
	}
	
	@Override
	public boolean equals(Object other)
	{
		try
		{
			return progName.equals(((ClassToTest)other).progName);
		}
		catch(ClassCastException e)
		{
			return false;
		}
	}
}
