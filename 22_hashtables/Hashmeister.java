/* Name: Richard Eisenberg
   File: Hashmeister.java
   Desc: Hashes strings from the user
*/
import java.util.*;

public class Hashmeister
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

		System.out.println("Welcome to the Hashmeister, where all your " +
		                   "hashes come true.");

		System.out.println("You cannot escape the Hashmeister, unless, " +
		                   "of course, you press the stop button.");
		for(;;)
		{
			System.out.println();
			System.out.print("Enter a string: ");

			String str = in.nextLine();
			System.out.println("The hash of that string is " +
			                   str.hashCode() + ".");
		}
	}
}