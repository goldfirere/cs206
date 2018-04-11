/* Name: Richard Eisenberg
 * File: BSTIterator.java
 * Desc: An inorder iterator through a binary search tree
 */

import java.util.*;

public class BSTIterator<E> implements Iterator<E>
{
	// The stack of parent nodes on the way to the node
	// where the next item is stored. The next item to be
	// returned by this iterator is the data in the node
	// at the top of the stack. When this is empty, the
	// iterator has no next item.
	private StackInt<Node<E>> stack;
	
	public BSTIterator(Node<E> root)
	{
		stack = new SingleLinkedList<>();
		
		goLeft(root);
	}
	
	// pushes root and all of the nodes to root's left-most
	// descendant onto the stack.
	private void goLeft(Node<E> root)
	{
		if(root != null)
		{
			stack.push(root);
			goLeft(root.left);
		}
	}
	
	// pops nodes off the stack until we find a node whose
	// right child was not the next node on the stack
	private void goRight()
	{
		Node<E> top = stack.pop();
		if(!stack.empty() && stack.peek().right == top)
		{
			goRight();
		}
	}
	
	@Override
	public boolean hasNext()
	{
		return !stack.empty();
	}
	
	@Override
	public E next()
	{
		if(stack.empty())
		{
			throw new NoSuchElementException();
		}
		
		Node<E> topNode = stack.peek();
	
		if(topNode.right == null)
		{
			// There is no right child of the top node. We must go
			// up the stack until we find a node where we haven't
			// yet visited the right child.
			goRight();
		}
		else
		{
			// There is a right child. The next element to return
			// will be the leftmost descendant of that right child.
			goLeft(topNode.right);
		}
		
		return topNode.data;
	}		
}
