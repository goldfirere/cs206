
public class InClass
{
	public static void main(String[] args)
	{
		DoctorV3 doc = new DoctorV3(1, 12345, "Robin", "Cardiology");
		
		EmployeeV3 emp = doc;
		//System.out.println(emp.getJobDescription());
		
		TeacherV3 teach = new TeacherV3(2, 23456, "Sam", "computer science"); 
		emp = teach;
		//System.out.println(emp.getJobDescription());
		
		EmployeeV3[] emps = new EmployeeV3[3];
		emps[0] = doc;
		emps[1] = teach;
		emps[2] = new DoctorV3(3, 54321, "Margaret", "Pediatrician");
		
		for(EmployeeV3 e : emps)
		{
			e.printInfo();
			
	//		if(e instanceof DoctorV3)
			{
				System.out.println("I found a doctor");
//				System.out.println("doctor's specialty is " + e.getSpecialty());
				DoctorV3 d = (DoctorV3)e;
				System.out.println("doctor's specialty is " + d.getSpecialty());
			}
		}
	}
}
