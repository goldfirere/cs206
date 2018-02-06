/* Name: Richard Eisenberg
 * File: DoctorV1.java
 * Desc: A doctor is an employee with a specialty
 */

public class DoctorV1 extends EmployeeV1
{
	private String specialty; // the field of medicine being practiced
	
	public DoctorV1(int id, double sal, String name, String spec)
	{
		setEmployeeNumber(id);
		setSalary(sal);
		setName(name);
		
		specialty = spec;
	}

	/** @return the doctor's specialty
	 */
	public String getSpecialty()
	{
		return specialty;
	}
	
	/** prints out all of the relevant information about a doctor
	 */
	public void printDoctorInfo()
	{
		printInfo(); // call the printInfo method to get basic info
		System.out.println("Specialty: " + specialty);
	}
}
