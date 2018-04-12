/* Name: Richard Eisenberg
 * File: ReadFile.java
 * Desc: Allows a program to take words from a file
 */

import java.util.*;
import java.io.*;

public class ReadFile
{
	/** Reads a list of all the words in a file.
	 *  @param filename The name of the file
	 *  @return A list of all the words in that file
	 */
	public static List<String> read(String filename)
	{
		List<String> list = new LinkedList<>();
		
		try
		{	
			BufferedReader reader =
					  new BufferedReader(new FileReader(filename));

			for(String line = reader.readLine();
				line != null;
				line = reader.readLine())
			{
				String[] words = line.split("\\s+");
				for(int i = 0; i < words.length; i++)
				{
					if(words[i].length() > 0)
					{
						list.add(words[i]);
					}
				}
			}

			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Error reading file: " + e);
			return null;
		}
		
		return new ArrayList<>(list); // this makes accessing quicker
	}
}