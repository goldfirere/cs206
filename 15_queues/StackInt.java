//This file is taken from Koffman & Wolfgang's
//"Data Structures: Abstraction and Design Using Java", 2nd Edition
//Pub. John Wiley & Sons, Inc., 2010.
//with minor modifications by Richard Eisenberg

/** A Stack is a data structure in which objects are inserted into
 *  and removed from the same end (i.e., Last-In, First-Out).
 */
public interface StackInt<E>
{
	/** Pushes an item onto the top of the stack and returns the item
	 *  pushed.
	 *  @param obj The object to be inserted
	 *  @return The object inserted
	 */
	E push(E obj);
	
	/** Returns the object at the top of the stack without removing it.
	 *  Post-condition: The stack remained unchanged.
	 *  @return The object at the top of the stack
	 *  @throws EmptyStackException if stack is empty
	 */
	E peek();
	
	/** Returns the object at the top of the stack and removes it.
	 *  Post-condition: The stack is one item smaller
	 *  @return The object at the top of the stack
	 *  @throws EmptyStackException if stack is empty
	 */
	E pop();
	
	/** Returns true if the stack is empty; otherwise, returns false.
	 *  @return true if the stack is empty
	 */
	boolean empty();
}
