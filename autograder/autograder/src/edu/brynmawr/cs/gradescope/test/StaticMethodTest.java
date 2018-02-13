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
	private JavaMethod method;
	private MethodBehavior[] behaviors;
	
	public StaticMethodTest(JavaMethod meth, MethodBehavior... bs)
	{
		method = meth;
		behaviors = bs;
	}
		
	@Override
	public List<TestResult> runTest() throws BorkedException
	{
		Method m;
		if(method.isValid())
		{
			m = method.getMethod();
		}
		else
		{
			return Collections.singletonList(new MultPoints(behaviors.length, new TestError(method.getError())));
		}
		
		String methodName = m.getName();
		String className = m.getDeclaringClass().getName();
		int modifiers = m.getModifiers();
		if(!Modifier.isStatic(modifiers))
		{
			return Collections.singletonList(new MultPoints(behaviors.length, new TestError("Method " + methodName + " in " + className + " is not static.\nPlease make it static, as requested in the assignment.")));
		}
		if(!Modifier.isPublic(modifiers))
		{
			return Collections.singletonList(new MultPoints(behaviors.length, new TestError("Method " + methodName + " in " + className + " is not public.\nPlease make it public.")));
		}
		
		List<TestResult> results = new ArrayList<TestResult>(behaviors.length);
		
		for(MethodBehavior b : behaviors)
		{
			Object[] arguments = b.getArguments();
			Optional<Object> expectedResult = b.getResult();

			List<String> argStrings = Arrays.stream(arguments).map(StaticMethodTest::render).collect(Collectors.toList());
			String methodCallDoc = "Calling method " + methodName + "(" + String.join(", ", argStrings) + ")\n";
			
			if(!b.expectsResult())
			{
				if(!b.getExpectedException().get().isValid())
				{
					results.add(new TestError(methodCallDoc + "was expected to throw an exception, but\n" + b.getExpectedException().get().getError()));
					continue;
				}
			}
			
			Object actualResult; 
			try
			{
				actualResult = TimedOperation.timeOperation(() -> m.invoke(null, arguments));
			}
			catch (ExecutionException e)
			{
				Throwable f = e.getCause();
				if(f instanceof IllegalAccessException)
				{
					throw new BorkedException("Method " + methodName + " in " + className + " could not be called.", f);
				}
				else if(f instanceof IllegalArgumentException)
				{
					throw new BorkedException("Method " + methodName + " in " + className + " could not be called without an object.", f);
				}
				else if(f instanceof InvocationTargetException)
				{
					Throwable actualException = f.getCause();
					Optional<JavaExceptionClass> expectedException = b.getExpectedException();
					
					if(expectedException.isPresent())
					{
						JavaExceptionClass exception = expectedException.get();
						Class<? extends Throwable> excClass = exception.getException();
						if(excClass.equals(actualException.getClass()))
						{
							results.add(new TestSuccess(methodCallDoc + "threw exception " + actualException.getClass().getName() + ", as expected.\n"));
						}
						else
						{
							results.add(new TestError(methodCallDoc + "threw exception " + actualException.getClass().getName() + ",\nbut it should have thrown " + excClass.getName() + ".\n"));
						}
					}
					else
					{
						results.add(new TestError(methodCallDoc + "threw exception " + actualException.getClass().getName() + ",\nbut it should have returned this:\n" + render(expectedResult.get())));
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
				results.add(new TestTimeout(methodCallDoc + "took longer than 10 seconds to run.\n"));
				continue;
			}

			if(expectedResult.isPresent())
			{
				Object expectedOutput = expectedResult.get();
				
				String testDoc = methodCallDoc + "Expected output: " + render(expectedOutput) + "\n";

				if(matches(expectedOutput, actualResult))
				{
					results.add(new TestSuccess(testDoc));
				}
				else
				{
					results.add(new TestFailure(testDoc, render(actualResult)));
				}
			}
			else
			{
				results.add(new TestError(methodCallDoc + "was supposed to throw exception " + b.getExpectedException().get().getException().getName() + ",\nbut it returned this instead:\n" + render(actualResult)));
			}
		}
		
		return results;
	}
	
	private static boolean matches(Object expected, Object actual)
	{
		if(actual == null)
		{
			return expected == null;
		}
		
		if(expected instanceof String)
		{
			return expected.equals(actual.toString());
		}
		else if(expected.getClass().isArray())
		{			
			try
			{
				if(expected instanceof int[] && actual instanceof int[])
				{
					int[] e = (int[])expected;
					int[] a = (int[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof long[] && actual instanceof long[])
				{
					long[] e = (long[])expected;
					long[] a = (long[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof short[] && actual instanceof short[])
				{
					short[] e = (short[])expected;
					short[] a = (short[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof byte[] && actual instanceof byte[])
				{
					byte[] e = (byte[])expected;
					byte[] a = (byte[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof float[] && actual instanceof float[])
				{
					float[] e = (float[])expected;
					float[] a = (float[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof double[] && actual instanceof double[])
				{
					double[] e = (double[])expected;
					double[] a = (double[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof char[] && actual instanceof char[])
				{
					char[] e = (char[])expected;
					char[] a = (char[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof boolean[] && actual instanceof boolean[])
				{
					boolean[] e = (boolean[])expected;
					boolean[] a = (boolean[])actual;
					
					return Arrays.equals(e, a);
				}
				else
				{
					Object[] e = (Object[])expected;
					Object[] a = (Object[])actual;
					
					if(e.length == a.length)
					{
						for(int i = 0; i < e.length; i++)
						{
							if(!matches(e[i], a[i]))
							{
								return false;
							}
						}
						
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			catch (ClassCastException e)
			{
				return false;
			}
		}
		else
		{
			return expected.equals(actual);
		}
	}
	
	private static String render(Object o)
	{
		if(o == null)
		{
			return "null";
		}
		
		if(o.getClass().isArray())
		{
			try
			{
				if(o instanceof int[])
				{
					return Arrays.toString((int[])o);
				}
				else if(o instanceof long[])
				{
					return Arrays.toString((long[])o);
				}
				else if(o instanceof short[])
				{
					return Arrays.toString((short[])o);
				}
				else if(o instanceof byte[])
				{
					return Arrays.toString((byte[])o);
				}
				else if(o instanceof float[])
				{
					return Arrays.toString((float[])o);
				}
				else if(o instanceof double[])
				{
					return Arrays.toString((double[])o);
				}
				else if(o instanceof char[])
				{
					return Arrays.toString((char[])o);
				}
				else if(o instanceof boolean[])
				{
					return Arrays.toString((boolean[])o);
				}
				else
				{
					return Arrays.toString((Object[])o);
				}
			}
			catch (ClassCastException e)
			{
				return o.toString();
			}
		}
		else
		{
			return o.toString();
		}
	}
}
