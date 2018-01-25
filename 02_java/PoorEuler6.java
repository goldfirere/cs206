/* Name: Richard Eisenberg
 * File: PoorEuler6.java
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

public class PoorEuler6
{
	public static void main(String[] args)
	{
		double count1 = 0;
		double count2 = 0;
		
		for(int n = 0; n <= 100; n++)
		{
			double regular = Math.pow(n, 1);
			count1 = count1 + regular;
			
			double squared = Math.pow(n, 2);
			count2 = count2 + squared;
		}
		
		count1 = Math.pow(count1, 2);
		count1 = count1 - count2;
		
		System.out.println((int)count1);
	}
}
