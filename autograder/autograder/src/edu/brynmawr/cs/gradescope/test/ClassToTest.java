package edu.brynmawr.cs.gradescope.test;

import java.util.*;

/** Represents a program under test
 * @author Richard Eisenberg
 *
 */
public class ProgramToTest
{
	private String progName;
	private List<IntegrationTest> tests;
	
	private double numPoints;
	
	public ProgramToTest(String className, double points, IntegrationTest... ts)
	{
		progName = className;
		numPoints = points;
		tests = Arrays.asList(ts);
	}

	/**
	 * @return the program name
	 */
	public String getProgName()
	{
		return progName;
	}

	/**
	 * @return the list of integration tests
	 */
	public List<IntegrationTest> getTests()
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
			return progName.equals(((ProgramToTest)other).progName);
		}
		catch(ClassCastException e)
		{
			return false;
		}
	}
}
