/* Name: Richard Eisenberg
 * File: HashTest.java
 * Desc: Unit tests for the hash table
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class HashTest
{
	@Test
	void testHashSet()
	{
		BMCHashSet<String> hash = new BMCHashSet<>();
		
		assertEquals(0, hash.size());
		assertTrue(hash.isEmpty());
		
		assertTrue(hash.add("pig"));
		assertTrue(hash.add("goat"));
		assertTrue(hash.add("chicken"));
		assertTrue(hash.add("rat"));
		assertTrue(hash.add("jackrabbit"));
		
		assertFalse(hash.add("rat"));
		assertFalse(hash.add("pig"));
		
		assertEquals(5, hash.size());
		assertFalse(hash.isEmpty());
		
		assertTrue(hash.contains("pig"));
		assertTrue(hash.contains("goat"));
		assertTrue(hash.contains("chicken"));
		assertTrue(hash.contains("rat"));
		assertTrue(hash.contains("jackrabbit"));
		
		assertTrue(hash.remove("goat"));
		assertFalse(hash.contains("goat"));
		assertTrue(hash.remove("pig"));
		assertFalse(hash.contains("pig"));
		assertTrue(hash.contains("jackrabbit"));
		assertTrue(hash.contains("rat"));
		assertTrue(hash.contains("chicken"));
		assertTrue(hash.remove("jackrabbit"));
		assertFalse(hash.contains("jackrabbit"));
		assertTrue(hash.remove("rat"));
		assertFalse(hash.contains("rat"));
		assertTrue(hash.remove("chicken"));
		assertFalse(hash.contains("chicken"));
		
		assertEquals(0, hash.size());
		assertTrue(hash.isEmpty());		
	}
}
