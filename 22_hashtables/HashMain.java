/* Name: Richard Eisenberg
 * File: HashMain.java
 * Desc: A main method to test BinarySearchTree's printInOrder method
 */

public class HashMain
{
	public static void main(String[] args)
	{
		BMCHashSet<String> hash = new BMCHashSet<>();
		hash.add("pig");
		hash.add("goat");
		hash.add("chicken");
		hash.add("rat");
		hash.add("jackrabbit");
		
		for(String s : hash)
		{
			System.out.println(s);
		}
	}
}
