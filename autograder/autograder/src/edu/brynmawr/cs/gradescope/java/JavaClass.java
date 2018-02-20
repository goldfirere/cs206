package edu.brynmawr.cs.gradescope.java;

import java.lang.reflect.*;

public interface JavaClass<T> extends D<Class<? extends T>>
{
	@SafeVarargs
	public static <T> JavaMethod getMethodD(JavaClass<T> cls, String methodName, D<Class<?>>... arguments)
	  throws BorkedException
	{
		Class<?>[] clses = new Class<?>[arguments.length];
		for(int i = 0; i < arguments.length; i++)
		{
			if(arguments[i].isValid())
			{
				clses[i] = arguments[i].get();
			}
			else
			{
				return JavaMethod.err(cls.getName(), methodName, arguments[i].getError());
			}
		}
		
		return cls.getMethod(methodName, clses);		
	}
	
	JavaMethod getMethod(String methodName, Class<?>... arguments)
	  throws BorkedException;
	
	JavaConstructor<T> getConstructor(Class<?>... arguments)
	  throws BorkedException;
	
	<S extends T> JavaClass<S> asSubclass(Class<S> sub);
	
	String getName();

	public static <T> JavaClass<T> of(Class<? extends T> klass)
	{
		return new JavaClassValid<>(klass);
	}
	
	public static <T> JavaClass<T> err(String cls, String message)
	{
		return new JavaClassError<>(cls, message);
	}
}

class JavaClassValid<T> extends DValid<Class<? extends T>> implements JavaClass<T>
{
	JavaClassValid(Class<? extends T> theT)
	{
		super(theT);
	}
	
	@Override
	public JavaMethod getMethod(String methodName, Class<?>... arguments) 
			throws BorkedException
	{
		Method meth;
		try
		{
			meth = get().getMethod(methodName, arguments);
		}
		catch (NoSuchMethodException e)
		{
			return JavaMethod.err(getName(), methodName, "Cannot run method " + methodName + ": Method with correct argument types not found.");
		}
		catch (SecurityException e)
		{
			throw new BorkedException("Security exception", e); 
		}
		catch (NoClassDefFoundError e)
		{
			// this was spotted in the wild. I think it happened because there
			// was an erroneous .java but still an (outdated) .class file around
			// not really sure. But there's no harm in catching the error.
			return JavaMethod.err(getName(), methodName, "There was a problem with " + getName() + ". Does the file have an error?");
		}
		
		if(!Modifier.isPublic(meth.getModifiers()))
		{
			return JavaMethod.err(getName(), methodName, "Method " + methodName + " in " + getName() + " is not public.\nPlease make it public.");
		}

		return JavaMethod.of(meth);
	}

	@Override
	public JavaConstructor<T> getConstructor(Class<?>... arguments) 
			throws BorkedException
	{
		Constructor<? extends T> meth;
		try
		{
			meth = get().getConstructor(arguments);
		}
		catch (NoSuchMethodException e)
		{
			return JavaConstructor.err(getName(), "Cannot run constructor " + getName() + ": Constructor with correct argument types not found.");
		}
		catch (SecurityException e)
		{
			throw new BorkedException("Security exception", e); 
		}
		catch (NoClassDefFoundError e)
		{
			// this was spotted in the wild. I think it happened because there
			// was an erroneous .java but still an (outdated) .class file around
			// not really sure. But there's no harm in catching the error.
			return JavaConstructor.err(getName(), "There was a problem with " + getName() + ". Does the file have an error?");
		}
		
		if(!Modifier.isPublic(meth.getModifiers()))
		{
			return JavaConstructor.err(getName(), "Constructor " + getName() + " is not public.\nPlease make it public.");
		}

		return JavaConstructor.of(meth);
	}

	
	@Override
	public <S extends T> JavaClass<S> asSubclass(Class<S> sub)
	{
		try
		{
			Class<? extends S> klass = get().asSubclass(sub);
			return JavaClass.of(klass);
		}
		catch (ClassCastException e)
		{
			return JavaClass.err(getName(), "class " + get().getName() + " is not a subclass of " + sub.getName() + ". It needs to be.");
		}
	}
	
	@Override
	public String getName()
	{
		return get().getName();
	}
}

class JavaClassError<T> extends DError<Class<? extends T>> implements JavaClass<T>
{
	private String className;
	
	JavaClassError(String cls, String err)
	{
		super(err);
		className = cls;
	}
	
	@Override
	public JavaMethod getMethod(String methodName, Class<?>... arguments)
	{
		return JavaMethod.err(getName(), methodName, "Cannot access method " + methodName + ":\n" + getError());
	}

	@Override
	public JavaConstructor<T> getConstructor(Class<?>... arguments)
	{
		return JavaConstructor.err(getName(), "Cannot access constructor " + getName() + ":\n" + getError());
	}
	
	@Override
	public <S extends T> JavaClass<S> asSubclass(Class<S> sub)
	{
		return JavaClass.err(className, getError());
	}
	
	@Override
	public String getName()
	{
		return className;
	}
}