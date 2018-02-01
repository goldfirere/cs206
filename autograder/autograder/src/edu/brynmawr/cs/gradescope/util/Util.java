package edu.brynmawr.cs.gradescope.util;

import java.util.function.*;

/** Utility functions
 * @author Richard Eisenberg
 */
public final class Util
{
	private Util() { } // prevent instantiation
	
	/** Search through a string until a predicate is satisfied
	 * @param s The string to search through
	 * @param f The predicate
	 * @return The first index at which the predicate is satisfied, or
	 *         -1 if there is no such index.
	 */
	public static int stringFind(String s, Predicate<Character> f)
	{
		for(int i = 0; i < s.length(); i++)
		{
			if(f.test(s.charAt(i)))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public static <A> BiConsumer<A, A> ignoreResult(BinaryOperator<A> op)
	{
		return (a1, a2) -> { op.apply(a1, a2); };
	}

	public static String dropWhitespace(String in)
	{
		StringBuilder out = new StringBuilder(in.length());
		
		for(int i = 0; i < in.length(); i++)
		{
			if(!Character.isWhitespace(in.charAt(i)))
			{
				out.append(in.charAt(i));
			}
		}
		
		return out.toString();
	}
}
