package edu.brynmawr.cs.gradescope.java;

import edu.brynmawr.cs.gradescope.run.*;

/** Exception thrown when a requested file doesn't exist.
 * @author rae
 */
public class JavaFileNotFoundException extends AutograderException
{
	/** Create an exception for when a Java file of a known class name
	 *  cannot be found
	 * @param className The name of the class you are looking for
	 */
	public JavaFileNotFoundException(String className)
	{
		this(className, null);
	}
	
	/** Create an exception for when a Java file of a known class name
	 *  cannot be found, with an extra reason to print.
	 * @param className The name of the class you are looking for
	 * @param reason Extra text to be printed
	 */
	public JavaFileNotFoundException(String className, String reason)
	{
		super("Could not find any file named " + className + ".java" +
	        (reason == null ? "" : "\n" + reason));
	}
}
