/* Name: Richard Eisenberg
 * File: DoctorV3.java
 * Desc: A doctor is an employee with a specialty
 */

public class DoctorV3 extends EmployeeV3
{
	private String specialty; // the field of medicine being practiced
	
	public DoctorV3(int id, double sal, String name, String spec)
	{
		super(id, sal, name); // call the superconstructor
		
		specialty = spec;
	}

	/* Returns specialty.
	 */
	public String getSpecialty()
	{
		return specialty;
	}
	
	// post: prints out all of the relevant information about a doctor
	@Override
	public void printInfo()
	{
		super.printInfo(); // call the printInfo method to get basic info
		System.out.println("Specialty: " + specialty);
	}
	
	// post: returns the type of job this object represents
	@Override
	public String getJobDescription()
	{
		return "doctor";
	}
}
