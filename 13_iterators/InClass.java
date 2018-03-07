import java.util.*;

public class InClass
{
	public static void main(String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		
		list.add("hi");
		list.add("there");
		list.add("baloney");
		
		System.out.println(list);
		{
			Iterator<String> itor = list.iterator();
			while(itor.hasNext())
			{
				String s = itor.next();
				System.out.println(s);
			}
			System.out.println(itor.next());
		}
		
		for(Iterator<String> itor = list.iterator(); itor.hasNext(); )
		{
			String s = itor.next();
			System.out.println(s);
		}
		
		for(String s : list)
		{
			System.out.println(s);
		}
	}
}
