import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/* Name: Richard Eisenberg
 * File: TreeMain.java
 * Desc: A main method to test BinarySearchTree's printInOrder method
 */

public class TreeMain
{
	public static void main(String[] args)
	{
		BinarySearchTree<String> tree = new BinarySearchTree<>();
		tree.add("pig");
		tree.add("goat");
		tree.add("chicken");
		tree.add("rat");
		tree.add("jackrabbit");
		
		tree.printInOrder();
	}
}
