
public class InClass3
{
	public static void main(String[] args)
	{
		Counter c1 = new Counter();
		Counter c2 = new Counter();
		
		c1.increment();		
		c2.increment();
		
		System.out.println(c1 == c2);
		
		System.out.println("hello".equals(String.join("", "he", "llo")));
		
		System.out.println(c1.get());
		System.out.println(c2.get());
		
		System.out.println("hello".indexOf("eg"));
	}
}
