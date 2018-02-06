/* Name: Richard Eisenberg
 * File: EmployeeV2.java
 * Desc: Represents a general employee, who might do anything
 */

public class EmployeeV2
{
	private int employeeNumber; // the number of the employee
	private double salary; // the employee's yearly salary
	private String name; // the employee's name
	
	public EmployeeV2()
	{
		// for reasons to be discussed later, nothing here yet
	}

	/** @return the employee's ID number
	 */
	public int getEmployeeNumber()
	{
		return employeeNumber;
	}

	/** Sets employee number
	 * @param anEmployeeNumber the new employee number
	 */
	public void setEmployeeNumber(int anEmployeeNumber)
	{
		employeeNumber = anEmployeeNumber;
	}

	/** @return Employee's name
	 */
	public String getName()
	{
		return name;
	}

	/** Sets employee name
	 * @param aName the new name
	 */
	public void setName(String aName)
	{
		name = aName;
	}

	/** @return employee's salary
	 */
	public double getSalary()
	{
		return salary;
	}

	/** Sets employee salary
	 * @param aSalary the new salary
	 */
	public void setSalary(double aSalary)
	{
		salary = aSalary;
	}
	
	/** prints out the relevant information about this object
	 */
	public void printInfo()
	{
		System.out.println("Id: " + employeeNumber);
		System.out.println("Name: " + name);
		System.out.println("Salary: " + salary);
	}
	
	/** @return the type of job this object represents
	 */
	public String getJobDescription()
	{
		return "generic employee";
	}
}
