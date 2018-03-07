//This file is taken from Koffman & Wolfgang's
//"Data Structures: Abstraction and Design Using Java", 2nd Edition
//Pub. John Wiley & Sons, Inc., 2010.
//with minor modifications by Richard Eisenberg

public class SingleLinkedList<E>
{
	private static class Node<E>
	{
		private E data; // the data stored at this node
		private Node<E> next; // the next node of data
		
		/** Creates a new node with a null next field
		 *  @param dataItem The data stored
		 */
		private Node(E dataItem)
		{
			data = dataItem;
			next = null;
		}
		
		/** Creates a new node that references another node.
		 *  @param dataItem The data stored
		 *  @param nodeRef The node referenced by new node
		 */
		private Node(E dataItem, Node<E> nodeRef)
		{
			data = dataItem;
			next = nodeRef;
		}
	}

	private Node<E> head = null; // reference to the list head
	private int size; // number of items in the list
	
	/** Add an item to the front of the list.
	 *  @param item The item to be added
	 */
	public void addFirst(E item)
	{
		head = new Node<E>(item, head);
		size++;
	}
	
	/** Add a node after a given node
	 *  @param node The node preceding the new item
	 *  @param item The item to insert
	 */
	private void addAfter(Node<E> node, E item)
	{
		node.next = new Node<E>(item, node.next);
		size++;
	}
	
	/** Insert the specified item at index
	 *  @param index The position where item is to be inserted
	 *  @param item The item to be inserted
	 *  @throws IndexOutOfBoundsException if index is out of range
	 */
	public void add(int index, E item)
	{
		if(index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		if(index == 0)
		{
			addFirst(item);
		}
		else
		{
			Node<E> node = getNode(index - 1);
			addAfter(node, item);
		}
	}

	/** Append item to the end of the list
	 *  @param item The item to be appended
	 *  @return true, always
	 */
	public boolean add(E item)
	{
		add(size, item);
		return true;
	}

	/** Remove the first node from the list
	 *  @return The removed node's data or null if the list is empty
	 */
	public E removeFirst()
	{
		Node<E> temp = head;
		if(head != null)
		{
			head = head.next;
		}
		
		// Return data at old head or null if list is empty
		if(temp != null)
		{
			size--;
			return temp.data;
		}
		else
		{
			return null;
		}
	}

	/** Remove the node after a given node
	 *  @param node The node before the one to be removed
	 *  @return The data from the removed node, or null if there
	 *          is no node to remove
	 */
	private E removeAfter(Node<E> node)
	{
		Node<E> temp = node.next;
		if(temp != null)
		{
			node.next = temp.next;
			size--;
			return temp.data;	
		}
		else
		{
			return null;
		}
	}
	
	/** Remove the node at the given index
	 *  @param index The position of the element to remove
	 *  @return The removed data
	 *  @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	public E remove(int index)
	{
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		
		if(index == 0)
		{
			return removeFirst();
		}
		else
		{
			Node<E> nodeBefore = getNode(index - 1);
			return removeAfter(nodeBefore);
		}
	}
	
	/** Get the data value at index
	 *  @param index The position of the element to return
	 *  @return The data at index
	 *  @throws IndexOutOfBoundsException if index is out of range
	 */
	public E get(int index)
	{
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		Node<E> node = getNode(index);
		return node.data;
	}
	
	/** Set the data value at index
	 *  @param index The position of the item to change
	 *  @param newValue The new value
	 *  @return The data value previously at index
	 *  @throws IndexOutOfBoundsException if index is out of range
	 */
	public E set(int index, E newValue)
	{
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		Node<E> node = getNode(index);
		E result = node.data;
		node.data = newValue;
		return result;
	}
	
	@Override
	public String toString()
	{
		// NB: The book uses StringBuilder, which is much more efficient
		// than using Strings. But using Strings is simpler.
		Node<E> nodeRef = head;
		String result = "";
		while(nodeRef != null)
		{
			result += nodeRef.data.toString();
			if(nodeRef.next != null)
			{
				result += " ==> ";
			}
			nodeRef = nodeRef.next;
		}
		return result;
	}

	/** Find the node at a specified position
	 *  @param index The position of the node sought
	 *  @return The node at index or null if it does not exist
	 */
	private Node<E> getNode(int index)
	{
		Node<E> node = head;
		for(int i = 0; i < index && node != null; i++)
		{
			node = node.next;
		}
		return node;
	}
}
