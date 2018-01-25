/* Name: Richard Eisenberg
 * File: Euler6.java
 * Desc: A poorly written solution to Project Euler, Problem #6:
 * 
 * The sum of the squares of the first ten natural numbers is,
 *    1^2 + 2^2 + ... + 10^2 = 385
 *
 * The square of the sum of the first ten natural numbers is,
 *    (1 + 2 + ... + 10)^2 = 55^2 = 3025
 *    
 * Hence the difference between the sum of the squares of the first ten
 * natural numbers and the square of the sum is 3025 - 385 = 2640.
 *
 * Find the difference between the sum of the squares of the first one
 * hundred natural numbers and the square of the sum.
 */

public class Euler6
{
	public static void main(String[] args)
	{
		int unsquaredSum = 0; // running sum of unsquared numbers
		int squaredSum = 0;
		
		for(int n = 1; n <= 100; n++)
		{
			// count1 = count1 + n;
			unsquaredSum += n;
			squaredSum += n * n;
		}
		
		System.out.println(unsquaredSum * unsquaredSum - squaredSum);
	}
}
