/* Name: Richard Eisenberg
 * File: FractionTest.java
 * Desc: Some unit tests for the Fraction class
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class FractionTest
{
	@Test
	void testConstruct1()
	{
		assertEquals("5/1", new Fraction(5).toString());
		assertEquals("-5/1", new Fraction(-5).toString());
		assertEquals("0/1", new Fraction(0).toString());
	}
	
	@Test
	void testConstruct2()
	{
		assertEquals("3/4", new Fraction(3, 4).toString());
		assertEquals("3/4", new Fraction(6, 8).toString());
		assertEquals("3/4", new Fraction(-3, -4).toString());
		assertEquals("3/4", new Fraction(-6, -8).toString());
		assertEquals("-3/4", new Fraction(-6, 8).toString());
		assertEquals("-3/4", new Fraction(6, -8).toString());
		
		assertEquals("0/1", new Fraction(0, 1).toString());
		assertEquals("0/1", new Fraction(0, 8).toString());
		assertEquals("0/1", new Fraction(0, -8).toString());
	}
	
	@Test
	void testAdd()
	{
		assertEquals("1/1", new Fraction(1,2).add(new Fraction(1,2)).toString());
		assertEquals("5/6", new Fraction(1,2).add(new Fraction(1,3)).toString());
		assertEquals("-1/2", new Fraction(1,2).add(new Fraction(-1)).toString());
		assertEquals("93/56", new Fraction(3,8).add(new Fraction(9,7)).toString());
		assertEquals("3/4", new Fraction(3,8).add(new Fraction(9, 24)).toString());
	}
	
	@Test
	void testMult()
	{
		assertEquals("3/16", new Fraction(3,8).multiply(new Fraction(1,2)).toString());
		assertEquals("-27/20", new Fraction(3,10).multiply(new Fraction(9,-2)).toString());
		assertEquals("0/1", new Fraction(45,77).multiply(new Fraction(0, 82)).toString());
	}
}
