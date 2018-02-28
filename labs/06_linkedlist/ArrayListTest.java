import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class ArrayListTest
{
	
	@Test
	void test1()
	{
		KWArrayListBuggy<Integer> list = new KWArrayListBuggy<>();
		list.add(3);
		list.add(4);
		list.add(5);
		
		assertEquals(list.size(), 3);
		assertEquals(list.get(0), new Integer(3));
		assertEquals(list.get(1), new Integer(4));
		assertEquals(list.get(2), new Integer(5));
	}
	
	@Test
	void test2()
	{
		KWArrayListBuggy<Integer> list = new KWArrayListBuggy<>();
		for(int i = 0; i < 20; i++)
		{
			list.add(i);
		}
		
		for(int i = 0; i < 20; i++)
		{
			assertEquals(list.get(i), new Integer(i));
		}
	}
}
