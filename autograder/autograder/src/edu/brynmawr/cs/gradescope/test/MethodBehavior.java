package edu.brynmawr.cs.gradescope.test;

import java.util.*;

import edu.brynmawr.cs.gradescope.expected.*;
import edu.brynmawr.cs.gradescope.java.*;

public class MethodBehavior
{
	private final D<Object>[] arguments;
	private final MethodResult result;

	@SuppressWarnings("unchecked")
	private static D<Object>[] dargs(Object[] args)
	{
		return (D<Object>[])Arrays.stream(args).map(D::of).toArray(D<?>[]::new);
	}
	
	public MethodBehavior(Object res, Object... args)
	{
		result = new MethodResultObject(res);
		arguments = dargs(args);
	}
	
	public MethodBehavior(JavaClass<Throwable> exc, Object... args)
	{
		result = new MethodResultException(exc);
		arguments = dargs(args);
	}
	
	public MethodBehavior(Expected exp, Object... args)
	{
		result = new MethodResultExpected(exp);
		arguments = dargs(args);
	}
	
	private MethodBehavior(MethodResult res, D<Object>[] args)
	{
		result = res;
		arguments = args;
	}
	
	@SafeVarargs
	public static MethodBehavior d(D<Object> res, D<Object>... args)
	{
		return new MethodBehavior(new MethodResultD(res), args);
	}
	
	@Deprecated
	@SuppressWarnings("unused")
	public MethodBehavior(Class<? extends Throwable> exc, Object... args)
	{
		throw new IllegalArgumentException();
	}

	/**
	 * @return the arguments
	 */
	public D<Object>[] getArguments()
	{
		return arguments;
	}
	
	public MethodResult getResult()
	{
		return result;
	}
}
