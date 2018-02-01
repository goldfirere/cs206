package edu.brynmawr.cs.gradescope.java;

import edu.brynmawr.cs.gradescope.run.*;

/** Represents exceptions due to a malformed system environment
 * @author Richard Eisenberg
 */
public class BorkedException extends AutograderException
{
	/** Create a BorkedException with a description of the problem
	 * @param s Problem description
	 */
	public BorkedException(String s)
	{
		this("System is borked: " + s, null);
	}

	/** Create a BorkedException with an underlying exception
	 * @param s Problem description
	 * @param e Underlying exception
	 */
	public BorkedException(String s, Exception e)
	{
		super("System is borked: " + s, e);
	}
}
