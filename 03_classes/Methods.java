/* Name: Richard Eisenberg
 * File: Methods.java
 * Desc: Demonstration of methods
 */

public class Methods
{
	public static void main(String[] args)
	{
		System.out.println("main");
		
		f();
		
		g(5);
		g(10);
		
		int x = h(3);
		int y = h(4);
		
		System.out.println("y: " + y);
		System.out.println("x: " + x);
		
		System.out.println("End main");
	}
	
	public static void f()
	{
		System.out.println("In method f");
	}
	
	public static void g(int z)
	{
		System.out.println("In method g; z = " + z);
		f();
		System.out.println("End of method g; z = " + z);
	}
	
	public static int h(int a)
	{
		System.out.println("In method h; a = " + a);
		return a * 2;
	}
}