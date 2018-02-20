package edu.brynmawr.cs.gradescope.java;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

import edu.brynmawr.cs.gradescope.test.*;
import edu.brynmawr.cs.gradescope.util.*;

public interface JavaConstructor<T> extends D<Constructor<? extends T>>
{
	String getClassName();
	
	D<MethodResult> newInstanceD(D<?>... args)
	  throws BorkedException;
	
	D<MethodResult> newInstance(Object... args)
	  throws BorkedException;
	
	public static <T> JavaConstructor<T> of(Constructor<? extends T> m)
	{
		return new JavaConstructorValid<>(m);
	}
	
	public static <T> JavaConstructor<T> err(String cls, String message)
	{
		return new JavaConstructorError<>(cls, message);
	}
	
	public default String makeConstructorCallDoc(Object... args)
	{
		List<String> argStrings = Arrays.stream(args).map(Util::render).collect(Collectors.toList());
		return "Calling constructor " + getClassName() + "(" + String.join(", ", argStrings) + ")\n";
	}
}

class JavaConstructorValid<T> extends DValid<Constructor<? extends T>> implements JavaConstructor<T>
{
	JavaConstructorValid(Constructor<? extends T> m)
	{
		super(m);
	}
	
	@Override
	public String getClassName()
	{
		return get().getDeclaringClass().getName();
	}
	
	@Override
	public D<MethodResult> newInstanceD(D<?>... dargs)
	  throws BorkedException
	{
		Optional<D<?>> maybeInvalid = Arrays.stream(dargs).filter(d -> !d.isValid()).findFirst();
		if(maybeInvalid.isPresent())
		{
			return D.err(maybeInvalid.get().getError());
		}
		
		// OK. everything is valid.
		Object[] args = new Object[dargs.length];
		for(int i = 0; i < dargs.length; i++)
		{
			args[i] = dargs[i].get();
		}
		
		return newInstance(args);
	}
	
	@Override
	public D<MethodResult> newInstance(Object... args)
	  throws BorkedException
	{	
		String methodCallDoc = makeConstructorCallDoc(args);

		try
		{
		  return TimedOperation.timeOperation(() -> 
			  {
			  		try
				  {
				  		return D.of(MethodResult.returned(get().newInstance(args)));
				  }
				  catch(IllegalAccessException e)
				  {
						throw new BorkedException("Constructor " + getClassName() + " could not be called.", e);				  	
				  }
			  		catch(IllegalArgumentException e)
			  		{
						throw new BorkedException("Constructor " + getClassName() + " could not be called without an object.", e);
			  		}
			  		catch(InvocationTargetException e)
			  		{
					  return D.of(MethodResult.exception(JavaClass.of(e.getCause().getClass())));
			  		}
			  		catch(InstantiationException e)
			  		{
			  			return D.err(getClassName() + " appears to be abstract; it shouldn't be.");
			  		}
			  });
		}
		catch (ProgramTooSlowException e)
		{
			return D.err(methodCallDoc + "took longer than 10 seconds to run.\n");
		}
	}
}

class JavaConstructorError<T> extends DError<Constructor<? extends T>> implements JavaConstructor<T>
{
	private String className;
	JavaConstructorError(String clsName, String err)
	{
		super(err);
		className = clsName;
	}
	
	@Override
	public String getClassName()
	{
		return className;
	}

	@Override
	public D<MethodResult> newInstanceD(D<?>... args)
			throws BorkedException
	{
		return D.err(getError());
	}
	
	@Override
	public D<MethodResult> newInstance(Object... args)
	{
		return D.err(getError());
	}
}