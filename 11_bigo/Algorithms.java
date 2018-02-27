//This file is taken from Koffman & Wolfgang's
//"Data Structures: Abstraction and Design Using Java", 2nd Edition
//Pub. John Wiley & Sons, Inc., 2010.
//with minor modifications by Richard Eisenberg

public class Algorithms
{
	/** Find a number in an array of ints
	 *  @param x The array to search in
	 *  @param target The number searched for
	 *  @return The index of the target, or -1 if the target is not found
	 */
	public static int search(int[] x, int target)
	{
		for(int i = 0; i < x.length; i++)
		{
			if(x[i] == target)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	/** Determine whether two arrays have no common elements
	 *  @param x One array
	 *  @param y The other array
	 *  @return true if there are no common elements, or false otherwise
	 */
	public static boolean areDifferent(int[] x, int[] y)
	{
		for(int i = 0; i < x.length; i++)
		{
			if(search(y, x[i]) != -1)
			{
				return false;
			}
		}
		
		return true;
	}
	
	/** Determine whether the contents of an array are all unique
	 *  @param x The array
	 *  @return true if all elements of x are unique, or false otherwise
	 */
	public static boolean areUnique(int[] x)
	{
		for(int i = 0; i< x.length; i++)
		{
			for(int j = 0; j < x.length; j++)
			{
				if(i != j && x[i] == x[j])
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	/** Determine whether the contents of an array are all unique
	 *  @param x The array
	 *  @return true if all elements of x are unique, or false otherwise
	 */
	public static boolean areUnique2(int[] x)
	{
		for(int i = 0; i < x.length; i++)
		{
			for(int j = i + 1; j < x.length; j++)
			{
				if(x[i] == x[j])
				{
					return false;
				}
			}
		}
		
		return true;
	}
}
