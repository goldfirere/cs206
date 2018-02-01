package edu.brynmawr.cs.gradescope.util;

/** An immutable pair of values
 * 
 * @author Richard Eisenberg
 *
 * @param <T> Type of first element
 * @param <U> Type of second element
 */
public final class Pair<T, U>
{
	private final T fst;
	private final U snd;
	
	/** Construct a new Pair
	 * @param f The first element
	 * @param s The second element
	 */
	public Pair(T f, U s)
	{
		fst = f;
		snd = s;
	}
	
	/** Retrieve the first element
	 * @return The first element
	 */
	public T fst()
	{
		return fst;
	}

	/** Retrieve the second element
	 * @return The second element
	 */
	public U snd()
	{
		return snd;
	}
}
