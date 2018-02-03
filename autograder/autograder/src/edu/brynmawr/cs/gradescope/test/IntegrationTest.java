package edu.brynmawr.cs.gradescope.test;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;
import edu.brynmawr.cs.gradescope.util.*;

public class IntegrationTest extends TestCase
{
	private String progInput;
	private String expectedOutput;
	
	public IntegrationTest(String in, String out)
	{
		super(false);
		progInput = in;
		expectedOutput = out;
	}
	
	public IntegrationTest(String in, String out, boolean hide)
	{
		super(hide);
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
	public List<TestResult> runTest(JavaFile jf)
	  throws BorkedException
	{
		String inputOutputDoc = "Input:\n" + progInput +
        "Expected output:\n" + expectedOutput;
		
		try
		{
			String output = jf.runProgram(progInput);

			String outputNoWS = Util.dropWhitespace(output);
			String expectedNoWS = Util.dropWhitespace(expectedOutput);
			if(outputNoWS.equalsIgnoreCase(expectedNoWS))
			{
				return Collections.singletonList(new TestSuccess(this, inputOutputDoc));
			}
			else
			{
				return Collections.singletonList(new TestFailure(this, inputOutputDoc, output));
			}

		}
		catch (ProgramTooSlowException e)
		{
			return Collections.singletonList(new TestTimeout(this, inputOutputDoc));
		}	
		catch (NoMainException e)
		{
			return Collections.singletonList(new TestError(this, e.getMessage()));
		}
	}
	
	@Override
	public double getWeight()
	{
		return 1.0;
	}
}
