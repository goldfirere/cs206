import java.util.*;

public class InClass
{
	public static void main(String[] args)
	{
		/*
		EmployeeV4 emp = new DoctorV4(1, 12345, "Martha", "podiatry");
		System.out.println(emp.getJobDescription());
		emp = new TeacherV4(2, 23456, "Thomas", "biology");
		System.out.println(emp.getJobDescription());
		emp = new EmployeeV4(3, 34567, "No one"); */
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(5);
		list.add(8);
		
		for(int x : list)
		{
			System.out.println(x);
		}
		
		System.out.println(list.get(0));
	}
}
