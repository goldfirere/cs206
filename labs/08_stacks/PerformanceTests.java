/* Name: Richard Eisenberg
 * File: PerformanceTests.java
 * Desc: Does performance tests on several stack implementations
 */

public class PerformanceTests
{
	public static long pushThenPop(StackInt<Integer> st, int numElts)
	{
		long start = System.nanoTime();
		for(int i = 0; i < numElts; i++)
		{
			st.push(i);
		}
		for(int i = 0; i < numElts; i++)
		{
			st.pop();
		}
		long end = System.nanoTime();
		
		return end - start;
	}
	
	public static long pushPopInterleaved(StackInt<Integer> st, int num)
	{
		long start = System.nanoTime();
		for(int i = 0; i < num; i++)
		{
			st.push(i);
			st.pop();
		}
		long end = System.nanoTime();
		
		return end - start;
	}
	
	public static void runTests(StackInt<Integer> st)
	{
		System.out.println("pushThenPop:");
		for(int num = 1000; num <= 50000; num += 1000)
		{
			long time = pushThenPop(st, num);
			System.out.println("With " + num + " elements: " + time / 1000000.0);
		}
		System.out.println("pushPopInterleaved:");
		for(int num = 1000; num <= 50000; num += 1000)
		{
			long time = pushPopInterleaved(st, num);
			System.out.println("With " + num + " elements: " + time / 1000000.0);
		}		
	}
	
	public static void main(String[] args)
	{
		System.out.println("SingleLinkedList:");
		runTests(new SingleLinkedList<>());
		System.out.println();
		System.out.println("KWArrayListEnd:");
		runTests(new KWArrayListEnd<>());
		System.out.println();
		System.out.println("KWArrayListStart:");
		runTests(new KWArrayListStart<>());
	}
}
