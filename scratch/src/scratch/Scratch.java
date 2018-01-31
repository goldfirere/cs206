package scratch;
import java.util.*;

public class Scratch
{
   public static void main(String[] args)
   {
	while(true)
     {
		Scanner in = new Scanner(System.in);

 	try
       	{
			System.out.print("Enter a number: ");
			String line = in.nextLine();
			double data = Double.parseDouble(line);
			System.out.println("The square root of " + data +
 		                   " is " + Math.sqrt(data));
		}
 	catch(NumberFormatException e)
 	{
		System.out.println("That's not a number!");
		}
	}
   }
}
