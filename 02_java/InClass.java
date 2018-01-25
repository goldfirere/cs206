
public class InClass
{
	public static void main(String[] args)
	{
		int n = 5;
		
		double d = 5;
		
		System.out.println(n);
		System.out.println(d);
		
		double d2 = 0.2;
		d2 = d2 + 2.4;
		d2 = d2 + (1 / 5.0);
		System.out.println(d2);
		
		// n = 3.14;
		d = 4;
		n = 4;
		
		boolean b = true;
		
		// int n2 = n + b;
		
		d = 3.1 + 5.6;
		n = 8 + 2;
		
		String s = "hello " + "world";
		System.out.println(s);
		
		String s2 = "hello " + n;
		System.out.println(s2);
		
		s2 = "hello " + 5 + 9;
		System.out.println(s2);
		
		s2 = 5 + "hello" + 9;
		System.out.println(s2);
		
		s2 = 5 + 9 + "hello";
		System.out.println(s2);
		
		d = 1.5 + 1.5;
		n = (int)d; // <-- that is a *cast*
		System.out.println(n);
		
		Counter c;
		c = new Counter();
		
		c.increment();
		c.increment();
		
		System.out.println(c);
	}
}
