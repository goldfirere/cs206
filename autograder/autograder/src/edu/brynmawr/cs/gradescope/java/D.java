package edu.brynmawr.cs.gradescope.java;

import java.util.function.*;

// D is for Dynamic
public class D<T>
{
	private boolean valid;
	
	private T t;
	private String message;
	
	protected D(boolean v, T theT)
	{
		if(!v)
		{
			throw new IllegalArgumentException("Bad D constructor call: false");
		}
		
		valid = v;
		t = theT;
		message = null;
	}
	
	protected D(boolean v, String s)
	{
		if(v)
		{
			throw new IllegalArgumentException("Bad D constructor call: true");
		}
		
		valid = v;
		t = null;
		message = s;
	}
	
	public boolean isValid()
	{
		return valid;
	}
	
	public T get()
	{
		if(valid)
		{
			return t;
		}
		else
		{
			throw new IllegalArgumentException("not valid");
		}
	}
	
	public String getError()
	{
		if(valid)
		{
			throw new IllegalArgumentException("
	
	public <R> D<R> f1(Function<T,R> f)
	{
		if(valid)
		{
			return D.of(f.apply(t));
		}
		else
		{
			return D.err(message);
		}
	}
	
	public <R,A> D<R> f2(BiFunction<T,A,R> f, A a)
	{
		if(valid)
		{
			return D.of(f.apply(t, a));
		}
		else
		{
			return D.err(message);
		}
	}
	
	public void p1(Consumer<T> f)
	{
		if(valid)
		{
			f.accept(t);
		}
	}
	
	public <A> void p2(BiConsumer<T,A> f, A a)
	{
		if(valid)
		{
			f.accept(t, a);
		}
	}
	
	public static <T> D<T> of(T t)
	{
		return new D<T>(true, t);
	}
	
	public static <T> D<T> err(String message)
	{
		return new D<T>(false, message);
	}
}
