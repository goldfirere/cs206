/* Name: Richard Eisenberg
 * File: PrintDirectory.java
 * Desc: Prints out the contents of the given file
 */

import java.io.*;
import java.util.*;

public class PrintFile
{
	/** Prints out the contents of the given file
	 * @param filename The name of the file to print
	 */
	public static void print(String filename)
	  throws FileNotFoundException
	{
		// first, check to make sure that the file isn't a directory
		File file = new File(filename);
		if(file.isDirectory())
		{
			throw new IllegalArgumentException("I can't read a directory");
			// Note: I *don't* need to declare this in the "throws" clause
		}
		
		// this next line might throw a FileNotFoundException, which is
		// a declared exception
		Scanner input = new Scanner(new File(filename));
		while(input.hasNextLine())
		{
			System.out.println(input.nextLine());
		}
		input.close();
	}
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		System.out.print("What file would you like to see? ");
		String filename = in.nextLine();
		
		try
		{
			print(filename);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("That file was not found.");
		}
	}
}
