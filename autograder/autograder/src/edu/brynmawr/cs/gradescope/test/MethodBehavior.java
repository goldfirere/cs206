package edu.brynmawr.cs.gradescope.test;

import java.util.*;

public class MethodBehavior
{
	private final Object[] arguments;
	private final Optional<Object> result;
	private final Optional<Class<? extends Throwable>> exception;

	public MethodBehavior(Object res, Object... args)
	{
		result = Optional.of(res);
		arguments = args;
		exception = Optional.empty();
	}
	
	public MethodBehavior(Class<? extends Throwable> exc, Object... args)
	{
		exception = Optional.of(exc);
		arguments = args;
		result = Optional.empty();
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

	public Optional<Class<? extends Throwable>> getExpectedException()
	{
		return exception;
	}
}
