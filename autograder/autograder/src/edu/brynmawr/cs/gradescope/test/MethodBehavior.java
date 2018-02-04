package edu.brynmawr.cs.gradescope.test;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public class MethodBehavior
{
	private final Object[] arguments;
	private final Optional<Object> result;
	private final Optional<JavaExceptionClass> exception;

	public MethodBehavior(Object res, Object... args)
	{
		result = Optional.of(res);
		arguments = args;
		exception = Optional.empty();
	}
	
	public MethodBehavior(JavaExceptionClass exc, Object... args)
	{
		exception = Optional.of(exc);
		arguments = args;
		result = Optional.empty();
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
	public Object[] getArguments()
	{
		return arguments;
	}

	/**
	 * @return the result
	 */
	public Optional<Object> getResult()
	{
		return result;
	}

	public boolean expectsResult()
	{
		return result.isPresent();
	}

	public Optional<JavaExceptionClass> getExpectedException()
	{
		return exception;
	}
}
