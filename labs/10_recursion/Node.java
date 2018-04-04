/* Name: Richard Eisenberg
 * File: Node.java
 * Desc: A node in a singly linked list
 */

public class Node
{
	public final String data; // the data stored in this Node
	// it is 'final' because it shouldn't be changed once the Node is built
	             
	public Node next;
	
	/** Construct a node with a null next field.
	 *  @param d The data to store in the node
	 */
	public Node(String d)
	{
		data = d;
		next = null;
	}
	
	/** Construct a node
	 *  @param d The data to store in the node
	 *  @param n The next node after this one
	 */
	public Node(String d, Node n)
	{
		data = d;
		next = n;
	}
	
	/** Build a list with the given elements.
	 *  @param elts The elements in the list
	 *  @return The head node of a list containing the given elements.
	 */
	public static Node makeList(String... elts)
	{
		Node head = null;
		for(int i = elts.length - 1; i >= 0; i--)
		{
			head = new Node(elts[i], head);
		}
		return head;
	}
}
