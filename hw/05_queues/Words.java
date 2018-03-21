/* Name: Richard Eisenberg
   File: Words.java
   Desc: Allows a program to take words from a dictionary
*/

import java.util.*;
import java.io.*;

public class Words
{
  private static ArrayList<String> words = new ArrayList<String>();
  private static ArrayList<String> names = new ArrayList<String>();

  /* This block is called a static initializer. It runs when a program
     first starts up. In this case, it reads in the dictionary file for
     later retrieval. You do not need to understand or modify this. */
  static
  {
    try
    {
      BufferedReader dictReader =
                        new BufferedReader(new FileReader("words.txt"));

      for(String word = dictReader.readLine();
                 word != null;
                 word = dictReader.readLine())
      {
        if(Character.isUpperCase(word.charAt(0)))
        {
          names.add(word);
        }
        else
        {
          words.add(word);
        }
      }

      dictReader.close();
    }
    catch(IOException e)
    {
      System.out.println("Error reading dictionary: " + e);
      System.exit(0);
    }
  }

  /** @return A random proper noun */
  public static String getRandomName()
  {
    return names.get((int)(Math.random() * names.size()));
  }

  /** @return A random non-proper noun */
  public static String getRandomWord()
  {
    return words.get((int)(Math.random() * words.size()));
  }
}
