/* Name: Richard Eisenberg
 * File: ArrayTests.java
 * Desc: Demonstration of unit tests over ArrayMethods
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class ArrayTests
{
	@Test
	void findEmpty()
	{
		assertEquals(ArrayMethods.findFirst(new int[] {}, 5), -1);
	}
	
	@Test
	void findOneSuccess()
	{
		assertEquals(ArrayMethods.findFirst(new int[] { 2 }, 2), 0);
	}
	
	@Test
	void findOneFailure()
	{
		assertEquals(ArrayMethods.findFirst(new int[] { 2 }, 3), -1);
	}
	
	@Test
	void findMiddle()
	{
		assertEquals(ArrayMethods.findFirst(new int[] {3, 8, 2, 0}, 8), 1);
	}
}
