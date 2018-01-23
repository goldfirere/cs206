/* Name: Richard Eisenberg
 * File: Prime.java
 * Desc: Tells the user whether or not their number is prime
 */

import java.util.*; // necessary to use Scanner

public class Prime
{
	public static void main(String[] args)
	{
		// Scanner gives us easy access to the numbers (and other input) that
		// the user types.
		Scanner in = new Scanner(System.in);
		
		// System.out.println shows some text to the user and then goes to
		// the next line. (With just System.out.print, it doesn't go to the
		// next line.
		System.out.println("Hello, and welcome to the prime checker.");
		
		// Here, I use just plain print because I want the user to be able
		// to type their number on the same line as this prompt.
		System.out.print("What number do you want to check? ");
		
		// Read in the number using Scanner's nextInt() method. This reads
		// characters typed by the user until it encounters the first
		// non-digit, and then converts those characters into an int. Crashes
		// if the user doesn't enter something starting with digits.
		// Later, you'll learn how to write this in a way that will never
		// crash.
		int numToTest = in.nextInt();
		
		if(numToTest < 2) // We can't test these numbers for primality
		{
			System.out.println("I can't test " + numToTest + " for primality.");
			System.out.println("Please enter a number >= 2.");
			return; // This line immediately ends the method.
		}
		
		// Now, we use a loop to test every possible divisor between 2 and
		// the square root of numToTest; if none of these divide evenly into
		// numToTest, then we know it's prime. (Why do we have to check
		// only up to the square root?)
		boolean isPrime = true; // start by assuming the # is prime
		for(int divisor = 2; divisor * divisor <= numToTest; divisor++)
		{
			// The % operator computes the remainder after division.
			// If the remainder after division is 0, then we've found a
			// factor.
			if(numToTest % divisor == 0)
			{
				isPrime = false;
			}
		}
	
		// Now that we've determined the primality of the number, inform
		// the user.
		if(isPrime)
		{
			System.out.println(numToTest + " is prime.");
		}
		else
		{
			System.out.println(numToTest + " is not prime.");
		}
	}
}
