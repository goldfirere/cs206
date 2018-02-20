package edu.brynmawr.cs.gradescope.test;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

import edu.brynmawr.cs.gradescope.java.*;
import edu.brynmawr.cs.gradescope.util.*;

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
		String methodName = method.getMethodName();
		String className = method.getClassName();
		D<Integer> modifiers = method.f1(Method::getModifiers);
		D<Boolean> isStatic = modifiers.f1(Modifier::isStatic);
		if(isStatic.isValid() && !isStatic.get())
		{
			return Collections.singletonList(new MultPoints(behaviors.length, new TestError("Method " + methodName + " in " + className + " is not static.\nPlease make it static, as requested in the assignment.")));
		}
		// if it's not valid, the normal error propagation will work
		
		List<TestResult> results = new ArrayList<TestResult>(behaviors.length);
		
		for(MethodBehavior b : behaviors)
		{
			D<Object>[] arguments = b.getArguments();
			MethodResult expected = b.getResult();

			List<String> argStrings = Arrays.stream(arguments).map(Util::render).collect(Collectors.toList());
			String methodCallDoc = "Calling method " + methodName + "(" + String.join(", ", argStrings) + ")\n";
						
			D<MethodResult> actual = method.invokeD(null, arguments);
			
			if(actual.isValid())
			{
				results.add(expected.checkResult(actual.get(), methodCallDoc));
			}
			else
			{
				results.add(new TestError(methodCallDoc + "could not be performed:\n" + actual.getError()));
			}
		}
		
		return results;
	}
}
