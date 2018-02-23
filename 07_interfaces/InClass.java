import java.util.*;

public class InClass
{
	public static void main(String[] args)
	{
		Fraction f = new Fraction(3,4);
	
		System.out.println(5);
		System.out.println(f);
		System.out.println("The fraction " + f);
		
		ArrayList<Printable> printers = new ArrayList<>();
		
		printers.add(new Fraction(5, 7));
		printers.add(new Fraction(10, 2));
		printers.add(new Counter());
		
		Counter c2 = new Counter();
		c2.increment();
		c2.increment();
		printers.add(c2);
		
		for(int i = 0; i < printers.size(); i++)
		{
			printers.get(i).print();
		}
	}
}
