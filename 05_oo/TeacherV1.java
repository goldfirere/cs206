/* Name: Richard Eisenberg
 * File: TeacherV1.java
 * Desc: A Teacher is an employee with a subject
 */

public class TeacherV1 extends EmployeeV1
{
	private String subject; // the subject this teacher teaches
	
	/** Creates a teacher object
	 * @param id The teacher's employee ID
	 * @param sal The teachr's salary
	 * @param name The teacher's name
	 * @param subj The teacher's subject
	 */
	public TeacherV1(int id, double sal, String name, String subj)
	{
		setEmployeeNumber(id);
		setSalary(sal);
		setName(name);
		
		subject = subj;
	}

	/** @return The teacher's subject
	 */
	public String getSubject()
	{
		return subject;
	}
	
  /** Prints information about this teacher
   */
	public void printTeacherInfo()
	{
		printInfo();
		System.out.println("Subject: " + subject);
	}
}
