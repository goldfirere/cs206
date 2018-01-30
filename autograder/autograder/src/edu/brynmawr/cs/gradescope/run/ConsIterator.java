package edu.brynmawr.cs.gradescope.run;

import java.util.*;

/** An Iterator that can be consed back onto
 * @author Richard Eisenberg
 *
 * @param <T> The element type stored here
 */
public class ConsIterator<T> implements Iterator<T> 
{
	private Stack<T> consed; // Extra elements consed onto the beginning
	private Iterator<T> iter; // The underlying iterator
	
	/** Construct a ConsIterator based on an existing iterator
	 * @param base The original element iterator
	 */
	public ConsIterator(Iterator<T> base)
	{
		consed = new Stack<T>();
		iter = base;
	}

	@Override
	public boolean hasNext()
	{
		return !consed.isEmpty() || iter.hasNext();
	}

	@Override
	public T next()
	{
		if(consed.isEmpty())
		{
			return iter.next();
		}
		else
		{
			return consed.pop();
		}
	}
	
	/** Add a new element on the beginning of this iterator
	 * @param t The new element
	 */
	public void cons(T t)
	{
		consed.push(t);
	}
}
