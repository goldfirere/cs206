package edu.brynmawr.cs.gradescope.java;

/** This gets thrown when trying to run a Java program that has
 *  no main method.
 * @author Richard Eisenberg
 */
public class NoMainException extends Exception
{
	public NoMainException(String className, String errors)
	{
		super("Error running program " + className + ":" + errors);
	}
}
