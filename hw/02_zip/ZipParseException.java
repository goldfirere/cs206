/* Name: Richard Eisenberg
 * File: ZipParseException.java
 * Desc: A checked exception representatin a malformed zipcodes datafile

/** A checked exception representing a malformed zipcodes datafile.
 */
public class ZipParseException extends Exception
{
	/** Create a ZipParseException
	 * @param lineNumber The line number of the original input file where
	 *                   the error occurred
	 * @param line The contents of that line
	 */
	public ZipParseException(int lineNumber, String line)
	{
		// This next line is a superclass constructor call. We'll learn
		// more about this soon.
		super("Zip codes parse error on line " + lineNumber + ":" + line);
	}
}
