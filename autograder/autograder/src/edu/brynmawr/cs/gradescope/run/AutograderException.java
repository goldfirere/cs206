package edu.brynmawr.cs.gradescope.run;

/** Exceptions thrown in the process of autograding an assignment
 * 
 * @author Richard Eisenberg
 */
public class AutograderException extends Exception
{
	/** Create an exception with a given description
	 * @param reason Why did this exception happen?
	 */
	public AutograderException(String reason)
	{
		super(reason);
	}
	
	/** Create an exception with a given reason and precipitating
	 * exception.
	 * 
	 * @param reason A textual description of the problem
	 * @param cause An underlying exception
	 */
	public AutograderException(String reason, Exception cause)
	{
		super(reason, cause);
	}
}
