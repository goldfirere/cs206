
public class InClass
{
	public static void main(String[] args)
	{
		DoctorV2 doc = new DoctorV2(1, 12345, "Robin", "Cardiology");
/*		emp.setEmployeeNumber(1);
		emp.setName("Robin");
		emp.setSalary(12345);
	*/	
		doc.printDoctorInfo();
		
		EmployeeV2 emp = doc;
		emp.printInfo();
		System.out.println(emp.getJobDescription());
		
		emp = new TeacherV2(2, 23456, "Sam", "Computer science");
		emp.printInfo();
		
//		DoctorV1 doc2 = emp;
//		doc2.printDoctorInfo();
	}
}
