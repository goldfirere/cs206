/* Name: Richard Eisenberg
 * File: LeftoverNumbersException.java
 * Desc: An exception when the Postfix Calculator has numbers left at end
 */

public class LeftoverNumbersException extends Exception
{
	public LeftoverNumbersException(String msg)
	{
		super(msg);
	}
}
