package edu.brynmawr.cs.gradescope.test;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

import edu.brynmawr.cs.gradescope.java.*;

/** Test an individual method
 * @author Richard Eisenberg
 */
public class StaticMethodTest extends TestCase
{
	private StaticMethodAccess method;
	private Object result;
	private Object[] arguments;
	
	public StaticMethodTest(StaticMethodAccess sma, Object res, Object... args)
	{
		this(false, sma, res, args);
	}
	
	public StaticMethodTest(boolean hide, StaticMethodAccess sma, Object res, Object... args)
	{
		super(hide);
		
		method = sma;
		result = res;
		arguments = args;
	}
	
	@Override
	public TestResult runTest(JavaFile jf) throws BorkedException
	{
		Method m;
		try
		{
			m = method.getMethod(jf);
		}
		catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object actualResult = m.invoke(null, arguments);
		
		List<String> argStrings = Arrays.stream(arguments).map(Object::toString).collect(Collectors.toList());
		String testDoc = "Calling method " + m.getName() + "(" + String.join(", ", argStrings) + "\n" +
		                 "Expected output: " + result + "\n";
		
		if(actualResult.equals(result))
		{
			return new TestSuccess(this, testDoc);
		}
		else
		{
			return new TestFailure(this, testDoc, actualResult.toString());
		}
	}
}
