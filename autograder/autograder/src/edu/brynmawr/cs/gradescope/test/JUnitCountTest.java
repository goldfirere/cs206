package edu.brynmawr.cs.gradescope.test;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public class JUnitCountTest extends TestCase
{
	private JavaClass<Object> testClass;
	private int numDesiredTests;
	
	public JUnitCountTest(JavaClass<Object> cls, int num)
	{
		testClass = cls;
		numDesiredTests = num;
	}
	
	@Override
	public List<TestResult> runTest() throws BorkedException
	{
		D<Method[]> methods = testClass.f1(Class::getDeclaredMethods);
		
		if(methods.isValid())
		{
			int count = 0;
			for(Method m : methods.get())
			{
				Annotation[] anns = m.getAnnotationsByType(org.junit.jupiter.api.Test.class);
				if(anns.length == 1)
				{
					count++;
				}
			}
		
			final int finalCount = count;
			return Collections.singletonList(new TestResult((double)count/numDesiredTests) {
				@Override
				public String render()
				{
					return "JUnit test cases found in " + testClass.getName() + ": " + finalCount + "\nTest cases desired: " + numDesiredTests + "\n";
				}
			});
		}
		else
		{
			return Collections.singletonList(new TestError(methods.getError()));
		}
	}
	
}
