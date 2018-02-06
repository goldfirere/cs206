/* Name: Richard Eisenberg
 * File: TeacherV3.java
 * Desc: A Teacher is an employee with a subject
 */

public class TeacherV3 extends EmployeeV3
{
	private String subject; // the subject this teacher teaches
	
	public TeacherV3(int id, double sal, String name, String subj)
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
	@Override
	public void printInfo()
	{
		super.printInfo();
		System.out.println("Subject: " + subject);
	}

	// post: returns the type of job this object represents
	@Override
	public String getJobDescription()
	{
		return "teacher";
	}
}
