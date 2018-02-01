package edu.brynmawr.cs.gradescope.java;

import java.util.*;
import java.util.stream.*;

import edu.brynmawr.cs.gradescope.run.*;

/** An exception representing compilation failure in user code
 * @author Richard Eisenberg
 */
public class JavaCompilationException extends AutograderException
{
	private String className; // name of the class being compiled
	private List<String> errorLines; // the error message
	
	/** Create a JavaCompilationException
	 * @param c The name of the class being compiled
	 * @param lines A Stream of lines of error messages
	 */
	public JavaCompilationException(String c, Stream<String> lines)
	{
		super("Compilation failure in " + c + ".java");
		
		className = c;
		errorLines = lines.collect(Collectors.toList());
	}

	/** Format the error message for user display
	 * @return An error string suitable for end users (students)
	 */
	public String getErrorOutput()
	{
		return "Compilation errors in " + className + ".java:\n" +
	         String.join("\n", errorLines);
	}
}
