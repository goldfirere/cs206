/* Name: Richard Eisenberg
 * File: EmployeeV3.java
 * Desc: Represents a general employee, who might do anything
 */

public class EmployeeV3
{
	private int employeeNumber; // the number of the employee
	private double salary; // the employee's yearly salary
	private String name; // the employee's name
	
	// the name of parameters must be different from field names to prevent confusion
	public EmployeeV3(int id, double sal, String nm)
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
	public String getJobDescription()
	{
		return "generic employee";
	}
}
