package edu.brynmawr.cs.gradescope.java;

import java.util.concurrent.*;

import edu.brynmawr.cs.gradescope.run.*;

/** An exception representing a user program that takes too long to run
 * @author Richard Eisenberg
 *
 */
public class ProgramTooSlowException extends AutograderException
{
	/**
	 * The length of the timeout this exception represents
	 */
	public static final int TIMEOUT = 10;
	
	
	/**
	 * The units of the timeout length
	 */
	public static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	
	/**
	 * Create a new ProgramTooSlowException
	 */
	public ProgramTooSlowException()
	{
		super("User program failed to terminate after 10 seconds.");
	}
}
