package edu.brynmawr.cs.gradescope.test;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

import edu.brynmawr.cs.gradescope.java.*;

/** Test an individual method
 * @author Richard Eisenberg
 */
public class StaticMethodTest extends TestCase
{
	private String methodName;
	private Class<?>[] argumentClasses;
	private MethodBehavior[] behaviors;
	
	public StaticMethodTest(String methName, MethodBehavior... bs)
	{
		this(false, methName, bs);
	}
	
	public StaticMethodTest(boolean hide, String methName, MethodBehavior... bs)
	{
		this(hide, methName, getArgumentClasses(bs), bs);
	}
	
	public StaticMethodTest(boolean hide, String methName, Class<?>[] argClasses, MethodBehavior... bs)
	{	
		super(hide);
		
		methodName = methName;
		argumentClasses = argClasses;
		behaviors = bs;
	}
	
	private static Class<?>[] getArgumentClasses(MethodBehavior[] bs)
	{
		if(bs.length == 0)
		{
			throw new IllegalArgumentException("A StaticMethodTest needs either the argument classes or at least one MethodBehavior.");
		}
		else
		{
			return Arrays.stream(bs[0].getArguments()).map(Object::getClass).toArray(Class<?>[]::new);
		}
	}
	
	@Override
	public List<TestResult> runTest(JavaFile jf) throws BorkedException
	{
		Method method;
		try
		{
			method = jf.getMethod(methodName, argumentClasses);
		}
		catch (NoSuchMethodException e)
		{
			System.out.println(e); // for debugging
			return Collections.singletonList(new TestError(this, "Could not find method " + methodName + " in " + jf.getName() + ".\n", behaviors.length));
		}

		int modifiers = method.getModifiers();
		if(!Modifier.isStatic(modifiers))
		{
			return Collections.singletonList(new TestError(this, "Method " + methodName + " in " + jf.getName() + " is not static.\nPlease make it static, as requested in the assignment."));
		}
		if(!Modifier.isPublic(modifiers))
		{
			return Collections.singletonList(new TestError(this, "Method " + methodName + " in " + jf.getName() + " is not public.\nPlease make it public."));
		}
		
		List<TestResult> results = new ArrayList<TestResult>(behaviors.length);
		
		for(MethodBehavior b : behaviors)
		{
			Object[] arguments = b.getArguments();
			Optional<Object> expectedResult = b.getResult();

			List<String> argStrings = Arrays.stream(arguments).map(Object::toString).collect(Collectors.toList());
			String methodCallDoc = "Calling method " + methodName + "(" + String.join(", ", argStrings) + ")\n";
			
			Object actualResult; 
			try
			{
				actualResult = TimedOperation.timeOperation(() -> method.invoke(null, arguments));
			}
			catch (ExecutionException e)
			{
				Throwable f = e.getCause();
				if(f instanceof IllegalAccessException)
				{
					throw new BorkedException("Method " + methodName + " in " + jf.getName() + " could not be called.", f);
				}
				else if(f instanceof IllegalArgumentException)
				{
					throw new BorkedException("Method " + methodName + " in " + jf.getName() + " could not be called without an object.", f);
				}
				else if(f instanceof InvocationTargetException)
				{
					Throwable actualException = f.getCause();
					Optional<Class<? extends Throwable>> expectedException = b.getExpectedException();
					
					if(expectedException.isPresent())
					{
						if(expectedException.get().equals(actualException.getClass()))
						{
							results.add(new TestSuccess(this, methodCallDoc + "threw exception " + actualException.getClass().getName() + ", as expected.\n"));
						}
						else
						{
							results.add(new TestError(this, methodCallDoc + "threw exception " + actualException.getClass().getName() + ",\nbut it should have thrown " + expectedException.get().getName() + ".\n"));
						}
					}
					else
					{
						results.add(new TestError(this, methodCallDoc + "threw exception " + actualException.getClass().getName() + ",\nbut it should have returned this:\n" + expectedResult.get()));
					}
					continue;
				}
				else
				{
					throw new BorkedException("Unexpected exception when " + methodCallDoc, e);
				}
			}
			catch (ProgramTooSlowException e)
			{
				results.add(new TestTimeout(this, methodCallDoc + "took longer than 10 seconds to run.\n"));
				continue;
			}

			if(expectedResult.isPresent())
			{
				Object expectedOutput = expectedResult.get();
				
				String testDoc = methodCallDoc + "Expected output: " + expectedOutput + "\n";
					
				try
				{
					if(expectedOutput instanceof TestObject ?
							((TestObject)expectedOutput).checkMethodResult(actualResult) :
						  expectedOutput.equals(actualResult))
					{
						results.add(new TestSuccess(this, testDoc));
					}
					else
					{
						results.add(new TestFailure(this, testDoc, actualResult.toString()));
					}
				}
				catch (TestErrorException e)
				{
					results.add(new TestError(this, methodCallDoc + "led to an error:\n" + e.getMessage()));
				}
			}
			else
			{
				results.add(new TestError(this, methodCallDoc + "was supposed to throw exception " + b.getExpectedException().get().getName() + ",\nbut it returned this instead:\n" + actualResult.toString()));
			}
		}
		
		return results;
	}
	
	@Override
	public double getWeight()
	{
		return behaviors.length;
	}
}
