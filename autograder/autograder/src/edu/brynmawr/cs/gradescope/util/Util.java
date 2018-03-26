package edu.brynmawr.cs.gradescope.util;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

import edu.brynmawr.cs.gradescope.java.*;

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
	
	@SuppressWarnings("unchecked")
	public static <T> Class<? extends T[]> getArrayClass(Class<? extends T> klass)
	{
		return (Class<? extends T[]>)Array.newInstance(klass, 0).getClass();
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
	
	public static boolean matches(Object expected, Object actual)
	{
		if(actual == null)
		{
			return expected == null;
		}
		else if(expected == null)
		{
			return false;
		}
		
		if(expected instanceof String)
		{
			return expected.equals(actual.toString());
		}
		else if(expected.getClass().isArray())
		{			
			try
			{
				if(expected instanceof int[] && actual instanceof int[])
				{
					int[] e = (int[])expected;
					int[] a = (int[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof long[] && actual instanceof long[])
				{
					long[] e = (long[])expected;
					long[] a = (long[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof short[] && actual instanceof short[])
				{
					short[] e = (short[])expected;
					short[] a = (short[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof byte[] && actual instanceof byte[])
				{
					byte[] e = (byte[])expected;
					byte[] a = (byte[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof float[] && actual instanceof float[])
				{
					float[] e = (float[])expected;
					float[] a = (float[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof double[] && actual instanceof double[])
				{
					double[] e = (double[])expected;
					double[] a = (double[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof char[] && actual instanceof char[])
				{
					char[] e = (char[])expected;
					char[] a = (char[])actual;
					
					return Arrays.equals(e, a);
				}
				else if(expected instanceof boolean[] && actual instanceof boolean[])
				{
					boolean[] e = (boolean[])expected;
					boolean[] a = (boolean[])actual;
					
					return Arrays.equals(e, a);
				}
				else
				{
					Object[] e = (Object[])expected;
					Object[] a = (Object[])actual;
					
					if(e.length == a.length)
					{
						for(int i = 0; i < e.length; i++)
						{
							if(!matches(e[i], a[i]))
							{
								return false;
							}
						}
						
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			catch (ClassCastException e)
			{
				return false;
			}
		}
		else
		{
			return expected.equals(actual);
		}
	}
	
	public static String render(Object o)
	{
		if(o == null)
		{
			return "null";
		}
		
		if(o.getClass().isArray())
		{
			try
			{
				if(o instanceof int[])
				{
					return Arrays.toString((int[])o);
				}
				else if(o instanceof long[])
				{
					return Arrays.toString((long[])o);
				}
				else if(o instanceof short[])
				{
					return Arrays.toString((short[])o);
				}
				else if(o instanceof byte[])
				{
					return Arrays.toString((byte[])o);
				}
				else if(o instanceof float[])
				{
					return Arrays.toString((float[])o);
				}
				else if(o instanceof double[])
				{
					return Arrays.toString((double[])o);
				}
				else if(o instanceof char[])
				{
					return Arrays.toString((char[])o);
				}
				else if(o instanceof boolean[])
				{
					return Arrays.toString((boolean[])o);
				}
				else
				{
					return Arrays.toString((Object[])o);
				}
			}
			catch (ClassCastException e)
			{
				return o.toString();
			}
		}
		else if(o instanceof D<?>)
		{
			D<?> d = (D<?>)o;
			if(d.isValid())
			{
				return render(d.get());
			}
			else
			{
				return "<<invalid object>>";
			}
		}
		else	
		{
			return o.toString();
		}
	}
}
