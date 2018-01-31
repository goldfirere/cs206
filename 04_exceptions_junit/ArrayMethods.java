/* Name: Richard Eisenberg
 * File: ArrayMethods.java
 * Desc: Example showing arrays and unit testing
 */

public class ArrayMethods
{
	/** Finds the first instance a number in an array
	 * @param nums An array of numbers to search in
	 * @param numToFind The number being sought
	 * @return The index of that number, or -1 if it doesn't exist in the
	 *         array
	 */
	public static int findFirst(int[] nums, int numToFind)
	{
		for(int i = 0; i < nums.length; i++)
		{
			if(nums[i] == numToFind)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	/** Extracts an array of characters representing the contents
	 *  of a string
	 * @param s The string to explode
	 * @return The array of the characters in the string
	 */
	public static char[] explode(String s)
	{
		char[] result = new char[s.length()]; // allocate space for result
		for(int i = 0; i < s.length(); i++)
		{
			result[i] = s.charAt(i);
		}
		return result;
	}
}
