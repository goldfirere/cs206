package edu.brynmawr.cs.gradescope.java;

import java.util.*;
import java.util.function.*;

import edu.brynmawr.cs.gradescope.util.*;

// D is for Dynamic
public interface D<T>
{
	boolean isValid();
	
	T get();
	String getError();
	
	<R> D<R> f1(FunctionB<T, R> f) throws BorkedException;
	<A,R> D<R> f2(BiFunctionB<T,A,R> f, D<A> a) throws BorkedException;
	void p1(ConsumerB<T> f) throws BorkedException;
	<A> void p2(BiConsumerB<T,A> f, D<A> a) throws BorkedException;
	<A,B> void p3(TriConsumerB<T,A,B> f, D<A> da, D<B> db) throws BorkedException;
	
	public static <T> D<T> of(T t)
	{
		return new DValid<>(t);
	}

	@SafeVarargs
	@SuppressWarnings("unchecked")
	public static <T> D<T>[] ofs(T... ts)
	{
		return (D<T>[])Arrays.stream(ts).map(D::of).toArray(D<?>[]::new);
	}
	
	public static <T> D<T> err(String str)
	{
		return new DError<>(str);
	}
	
	public static <T> D<T> join(D<D<T>> ddt)
	{
		if(ddt.isValid())
		{
			return ddt.get();
		}
		else
		{
			return D.err(ddt.getError());
		}
	}
}

class DValid<T> implements D<T>
{
	private T t;
	
	DValid(T theT)
	{
		t = theT;
	}
	
	@Override
	public boolean isValid()
	{
		return true;
	}
	
	@Override
	public T get()
	{
		return t;
	}
	
	@Override
	public String getError()
	{
		throw new IllegalArgumentException("No error in a valid D.");
	}

	@Override
	public <R> D<R> f1(FunctionB<T, R> f) throws BorkedException
	{
		return D.of(f.apply(t));
	}

	@Override
	public <A, R> D<R> f2(BiFunctionB<T, A, R> f, D<A> da) throws BorkedException
	{
		return da.f1(a -> f.apply(t, a));
	}

	@Override
	public void p1(ConsumerB<T> f) throws BorkedException
	{
		f.accept(t);
	}

	@Override
	public <A> void p2(BiConsumerB<T, A> f, D<A> da) throws BorkedException
	{
		da.p1(a -> f.accept(t, a));
	}

	@Override
	public <A,B> void p3(TriConsumerB<T, A, B> f, D<A> da, D<B> db) throws BorkedException
	{
		da.p1(a -> db.p1(b -> f.accept(t, a, b)));
	}
	
	@Override
	public int hashCode()
	{
		return t.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return (obj instanceof DValid<?>) && t.equals(((DValid<?>)obj).t);
	}

	@Override
	public String toString()
	{
		return t.toString();
	}
}

class DError<T> implements D<T>
{
	private String errorMessage;
	
	DError(String err)
	{
		errorMessage = err;
	}
	
	@Override
	public boolean isValid()
	{
		return false;
	}

	@Override
	public T get()
	{
		throw new IllegalArgumentException("No payload in DError.");
	}

	@Override
	public String getError()
	{
		return errorMessage;
	}

	@Override
	public <R> D<R> f1(FunctionB<T, R> f) throws BorkedException
	{
		return D.err(errorMessage);
	}

	@Override
	public <A, R> D<R> f2(BiFunctionB<T, A, R> f, D<A> da) throws BorkedException
	{
		return D.err(errorMessage);
	}

	@Override
	public void p1(ConsumerB<T> f) throws BorkedException
	{
		// do nothing
	}

	@Override
	public <A> void p2(BiConsumerB<T, A> f, D<A> a) throws BorkedException
	{
		// do nothing
	}
	
	@Override
	public <A,B> void p3(TriConsumerB<T, A, B> f, D<A> da, D<B> db) throws BorkedException
	{
		//do nothing
	}

	@Override
	public int hashCode()
	{
		return getClass().hashCode(); // don't look at error
	}

	@Override
	public boolean equals(Object obj)
	{
		return getClass().equals(obj.getClass());
	}

	@Override
	public String toString()
	{
		return errorMessage;
	}
}