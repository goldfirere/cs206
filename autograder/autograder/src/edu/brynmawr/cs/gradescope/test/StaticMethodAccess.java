package edu.brynmawr.cs.gradescope.test;

import java.lang.reflect.*;

import edu.brynmawr.cs.gradescope.java.*;

public class StaticMethodAccess
{
	private final String methodName;
	private final Class<?>[] arguments;
	
	private Method method;
	
	public StaticMethodAccess(String methName, Class<?>... args)
	{
		methodName = methName;
		arguments = args;
		
		method = null;
	}
	
	public Method getMethod(JavaFile jf) throws BorkedException, NoSuchMethodException
	{
		if(method == null)
		{
			method = jf.getMethod(methodName, arguments);
		}
		
		return method;
	}
}
