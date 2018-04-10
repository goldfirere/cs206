/* Name: Richard Eisenberg
 * File: TreeTest.java
 * Desc: Unit tests for the binary search tree
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TreeTest
{
	@Test
	void testRec()
	{
		BinarySearchTree<String> tree = new BinarySearchTree<>();
		
		assertEquals(0, tree.size());
		assertTrue(tree.isEmpty());
		
		assertTrue(tree.add("pig"));
		assertTrue(tree.add("goat"));
		assertTrue(tree.add("chicken"));
		assertTrue(tree.add("rat"));
		assertTrue(tree.add("jackrabbit"));
		
		assertFalse(tree.add("rat"));
		assertFalse(tree.add("pig"));
		
		assertEquals(5, tree.size());
		assertFalse(tree.isEmpty());
		
		assertTrue(tree.contains("pig"));
		assertTrue(tree.contains("goat"));
		assertTrue(tree.contains("chicken"));
		assertTrue(tree.contains("rat"));
		assertTrue(tree.contains("jackrabbit"));
		
		assertTrue(tree.remove("goat"));
		assertFalse(tree.contains("goat"));
		assertTrue(tree.remove("pig"));
		assertFalse(tree.contains("pig"));
		assertTrue(tree.contains("jackrabbit"));
		assertTrue(tree.contains("rat"));
		assertTrue(tree.contains("chicken"));
		assertTrue(tree.remove("jackrabbit"));
		assertFalse(tree.contains("jackrabbit"));
		assertTrue(tree.remove("rat"));
		assertFalse(tree.contains("rat"));
		assertTrue(tree.remove("chicken"));
		assertFalse(tree.contains("chicken"));
		
		assertEquals(0, tree.size());
		assertTrue(tree.isEmpty());		
	}

	@Test
	void testIter()
	{
		BinarySearchTree<String> tree = new BinarySearchTree<>();
		
		assertEquals(0, tree.size());
		assertTrue(tree.isEmpty());
		
		assertTrue(tree.addIter("pig"));
		assertTrue(tree.addIter("goat"));
		assertTrue(tree.addIter("chicken"));
		assertTrue(tree.addIter("rat"));
		assertTrue(tree.addIter("jackrabbit"));
		
		assertFalse(tree.addIter("rat"));
		assertFalse(tree.addIter("pig"));
		
		assertEquals(5, tree.size());
		assertFalse(tree.isEmpty());
		
		assertTrue(tree.containsIter("pig"));
		assertTrue(tree.containsIter("goat"));
		assertTrue(tree.containsIter("chicken"));
		assertTrue(tree.containsIter("rat"));
		assertTrue(tree.containsIter("jackrabbit"));
		
		assertTrue(tree.remove("goat"));
		assertFalse(tree.containsIter("goat"));
		assertTrue(tree.remove("pig"));
		assertFalse(tree.containsIter("pig"));
		assertTrue(tree.containsIter("jackrabbit"));
		assertTrue(tree.containsIter("rat"));
		assertTrue(tree.containsIter("chicken"));
		assertTrue(tree.remove("jackrabbit"));
		assertFalse(tree.containsIter("jackrabbit"));
		assertTrue(tree.remove("rat"));
		assertFalse(tree.containsIter("rat"));
		assertTrue(tree.remove("chicken"));
		assertFalse(tree.containsIter("chicken"));
		
		assertEquals(0, tree.size());
		assertTrue(tree.isEmpty());		
	}
}
