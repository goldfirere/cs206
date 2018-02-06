/* Name: Richard
 * File: TeacherV4.java
 * Desc: A Teacher is an employee with a subject
 */

public class TeacherV4 extends EmployeeV4
{
	private String subject; // the subject this teacher teaches
	
	public TeacherV4(int id, double sal, String name, String subj)
	{
		super(id, sal, name);
		
		subject = subj;
	}

	/* Returns subject.
	 */
	public String getSubject()
	{
		return subject;
	}
	
	// prints relevant information about a teacher
	public void printInfo()
	{
		super.printInfo();
		System.out.println("Subject: " + subject);
	}

	// post: returns the type of job this object represents
	public String getJobDescription()
	{
		return "teacher";
	}
}
