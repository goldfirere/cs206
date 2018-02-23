/* Name: Richard Eisenberg
 * File: Counter.java
 * Desc: A simple counter that can be read, incremented, and cleared
 */

public class Counter implements Printable
{
	private int count; // the count stored within this Counter
	
	/** Construct a counter starting at 0 */
	public Counter()
	{
		count = 0;
	}
	
	/** Construct a counter starting at a specified value.
	 * 
	 * @param n The starting value for the counter.
	 */
	public Counter(int n)
	{
		count = n;
	}
	
	/** Increment the counter. After calling this method, the counter's
	 * value will be one more than it was previously
	 */
	public void increment()
	{
		count++;
	}
	
	/** Retrieve the current value of the counter.
	 * 
	 * @return The counter's current value.
	 */
	public int get()
	{
		return count;
	}
	
	/** Clear the counter, setting it to 0. */
	public void clear()
	{
		count = 0;
	}
	
	// Print out the value of this counter
	@Override
	public void print()
	{
		System.out.println(count);
	}
}
