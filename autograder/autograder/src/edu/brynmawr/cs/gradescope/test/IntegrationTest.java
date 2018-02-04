package edu.brynmawr.cs.gradescope.test;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;
import edu.brynmawr.cs.gradescope.util.*;

public class IntegrationTest extends TestCase
{
	private JavaFile program;
	private String progInput;
	private String expectedOutput;
	
	public IntegrationTest(JavaFile prog, String in, String out)
	{
		program = prog;
		progInput = in;
		expectedOutput = out;
	}
	
	/**
	 * @return the progInput
	 */
	public String getProgInput()
	{
		return progInput;
	}

	/**
	 * @return the expectedOutput
	 */
	public String getExpectedOutput()
	{
		return expectedOutput;
	}
	
	@Override
	public List<TestResult> runTest()
	  throws BorkedException
	{
		String inputOutputDoc = "Input:\n" + progInput +
        "Expected output:\n" + expectedOutput;
		
		try
		{
			String output = program.runProgram(progInput);

			String outputNoWS = Util.dropWhitespace(output);
			String expectedNoWS = Util.dropWhitespace(expectedOutput);
			if(outputNoWS.equalsIgnoreCase(expectedNoWS))
			{
				return Collections.singletonList(new TestSuccess(inputOutputDoc));
			}
			else
			{
				return Collections.singletonList(new TestFailure(inputOutputDoc, output));
			}

		}
		catch (ProgramTooSlowException e)
		{
			return Collections.singletonList(new TestTimeout(inputOutputDoc));
		}	
		catch (NoMainException e)
		{
			return Collections.singletonList(new TestError(e.getMessage()));
		}
	}
}
