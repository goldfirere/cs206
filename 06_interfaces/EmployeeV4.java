/* Name: Richard
 * File: EmployeeV4.java
 * Desc: Represents a general employee, who might do anything
 */

// EmployeeV4 must be abstract because it contains an abstract method
public abstract class EmployeeV4
{
	private int employeeNumber; // the number of the employee
	private double salary; // the employee's yearly salary
	private String name; // the employee's name
	
	// the name of parameters must be different from field names to prevent confusion
	public EmployeeV4(int id, double sal, String nm)
	{
		employeeNumber = id;
		salary = sal;
		name = nm;
	}

	/* Returns employeeNumber.
	 */
	public int getEmployeeNumber()
	{
		return employeeNumber;
	}

	/* Returns name.
	 */
	public String getName()
	{
		return name;
	}

	/* Returns salary.
	 */
	public double getSalary()
	{
		return salary;
	}
	
	// post: prints out the relevant information about this object
	public void printInfo()
	{
		System.out.println("Id: " + employeeNumber);
		System.out.println("Name: " + name);
		System.out.println("Salary: " + salary);
	}
	
	// post: returns the type of job this object represents
	public abstract String getJobDescription(); // abstract because an "employee" doesn't have a job description
}
