package edu.brynmawr.cs.gradescope.java;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

import edu.brynmawr.cs.gradescope.test.*;
import edu.brynmawr.cs.gradescope.util.*;

public interface JavaMethod extends D<Method>
{
	String getMethodName();
	String getClassName();
	
	D<MethodResult> invokeD(D<Object> obj, D<?>... args)
	  throws BorkedException;
	
	D<MethodResult> invoke(Object obj, Object... args)
	  throws BorkedException;
	
	public static JavaMethod of(Method m)
	{
		return new JavaMethodValid(m);
	}
	
	public static JavaMethod err(String cls, String meth, String message)
	{
		return new JavaMethodError(cls, meth, message);
	}
	
	public default String makeMethodCallDoc(Object... args)
	{
		List<String> argStrings = Arrays.stream(args).map(Util::render).collect(Collectors.toList());
		return "Calling method " + getMethodName() + "(" + String.join(", ", argStrings) + ")\n";
	}
}

class JavaMethodValid extends DValid<Method> implements JavaMethod
{
	JavaMethodValid(Method m)
	{
		super(m);
	}
	
	@Override
	public String getMethodName()
	{
		return get().getName();
	}
	
	@Override
	public String getClassName()
	{
		return get().getDeclaringClass().getName();
	}
	
	@Override
	public D<MethodResult> invokeD(D<Object> dobj, D<?>... dargs)
	  throws BorkedException
	{
		if(dobj != null && !dobj.isValid())
		{
			return D.err(dobj.getError());
		}
		Optional<D<?>> maybeInvalid = Arrays.stream(dargs).filter(d -> !d.isValid()).findFirst();
		if(maybeInvalid.isPresent())
		{
			return D.err(maybeInvalid.get().getError());
		}
		
		// OK. everything is valid.
		Object obj = dobj == null ? null : dobj.get();
		Object[] args = new Object[dargs.length];
		for(int i = 0; i < dargs.length; i++)
		{
			args[i] = dargs[i].get();
		}
		
		return invoke(obj, args);
	}
	
	@Override
	public D<MethodResult> invoke(Object obj, Object... args)
	  throws BorkedException
	{	
		String methodCallDoc = makeMethodCallDoc(args);

		MethodResult result;
		try
		{
			result = TimedOperation.timeOperation(() -> 
			  {
			  		try
				  {
				  		return new MethodResultActual(get().invoke(obj, args));
				  }
				  catch(IllegalAccessException e)
				  {
						throw new BorkedException("Method " + getMethodName() + " in " + getClassName() + " could not be called.", e);				  	
				  }
			  		catch(IllegalArgumentException e)
			  		{
						throw new BorkedException("Method " + getMethodName() + " in " + getClassName() + " could not be called without an object.", e);
			  		}
			  		catch(InvocationTargetException e)
			  		{
					  return new MethodResultException(JavaClass.of(e.getCause().getClass()));
			  		}
			  });
		}
		catch (ProgramTooSlowException e)
		{
			return D.err(methodCallDoc + "took longer than 10 seconds to run.\n");
		}

		return D.of(result);
	}
}

class JavaMethodError extends DError<Method> implements JavaMethod
{
	private String methName;
	private String className;
	JavaMethodError(String clsName, String mName, String err)
	{
		super(err);
		className = clsName;
		methName = mName;
	}
	
	@Override
	public String getMethodName()
	{
		return methName;
	}
	
	@Override
	public String getClassName()
	{
		return className;
	}

	@Override
	public D<MethodResult> invokeD(D<Object> obj, D<?>... args)
			throws BorkedException
	{
		return D.err(getError());
	}
	
	@Override
	public D<MethodResult> invoke(Object obj, Object... args)
	{
		return D.err(getError());
	}
}