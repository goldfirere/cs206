package edu.brynmawr.cs.gradescope.test;

import java.util.concurrent.*;

import edu.brynmawr.cs.gradescope.java.*;

public class TimedOperation
{
	private TimedOperation() {}
	
	public static <R> R timeOperation(Callable<R> c)
	  throws BorkedException, ExecutionException, ProgramTooSlowException
	{
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		final Future<R> future = executor.submit(c);
		executor.shutdown();
		
		try
		{
			return future.get(ProgramTooSlowException.TIMEOUT, ProgramTooSlowException.TIMEOUT_UNIT);
		}
		catch (InterruptedException e)
		{
			throw new BorkedException("Timed method interrupted", e);
		}
		catch (TimeoutException e)
		{
			throw new ProgramTooSlowException();
		}
	}	
}
