package edu.brynmawr.cs.gradescope.run;

/** Exceptions when a Java file is malformed somehow
 * @author Richard Eisenberg
 */
public class JavaParseException extends AutograderException
{
	/** Create a JavaParseException on a known line
	 * @param line The line of the error
	 */
	public JavaParseException(String line)
	{
		super("Parse error when reading \"" + line + "\"");
	}
	
	/**
	 * Create a JavaParseException for an unknown line
	 */
	public JavaParseException()
	{
		super("Parse error in Java code");
	}
}
