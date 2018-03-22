//This file is taken from Koffman & Wolfgang's
//"Data Structures: Abstraction and Design Using Java", 2nd Edition
//Pub. John Wiley & Sons, Inc., 2010.
//with minor modifications by Richard Eisenberg
//This version implements Queue (which extends Iterable) and StackInt

import java.lang.reflect.*;
import java.util.*;

public class SingleLinkedList<E> implements StackInt<E>, Queue<E>
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
	
	private static class SLLIterator<E> implements Iterator<E>
	{
		private Node<E> node;
		
		private SLLIterator(SingleLinkedList<E> list)
		{
			node = list.head;
		}
		
		@Override
		public boolean hasNext()
		{
			return (node != null);
		}
		
		@Override
		public E next()
		{
			if(node == null)
			{
				throw new NoSuchElementException();
			}
			
			E data = node.data;
			node = node.next;
			return data;
		}
	}

	private Node<E> head = null; // reference to the list head
	private Node<E> tail = null; // reference to the list tail
	private int size; // number of items in the list
	
	/** Add an item to the front of the list.
	 *  @param item The item to be added
	 */
	public void addFirst(E item)
	{
		head = new Node<E>(item, head);

		// if this is the first item ever, set the tail reference
		if(size == 0)
		{
			tail = head;
		}
		
		size++;
	}
	
	/** Add an item to the end of the list.
	 *  @param item The item to be added
	 */
	public void addLast(E item)
	{
		// if there are no elements, use addFirst
		if(size == 0)
		{
			addFirst(item);
		}
		
		// here, we know tail can't be null
		tail.next = new Node<E>(item, null);
		tail = tail.next;
		
		size++;
	}
	
	/** Add a node after a given node
	 *  @param node The node preceding the new item
	 *  @param item The item to insert
	 */
	private void addAfter(Node<E> node, E item)
	{
		// addLast can handle the case where we're adding the last element
		if(node.next == null)
		{
			addLast(item);
		}
		else
		{
			node.next = new Node<E>(item, node.next);
			size++;
		}
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
		else if(index == size)
		{
			addLast(item);
		}
		else
		{
			Node<E> node = getNode(index - 1);
			addAfter(node, item);
		}
	}

	/** Append item to the end of the list.
	 *  @param item The item to be appended
	 *  @return true, always
	 */
	@Override // this overrides an abstract method from Queue
	public boolean add(E item)
	{
		addLast(item);
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
			
			if(head == null) // this is the only item; update the tail
			{
				tail = null;
			}
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
			
			// if we've removed the last element, update the tail
			if(node.next == null)
			{
				tail = node;
			}
			
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
		// no special case for last element here, because removing can't
		// be optimized with a tail pointer
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
		// optimize for tail
		if(index == size - 1)
		{
			return tail;
		}
		
		Node<E> node = head;
		for(int i = 0; i < index && node != null; i++)
		{
			node = node.next;
		}
		return node;
	}

	/**********************************************************
	 * Stack methods
	 **********************************************************/
	
	@Override
	public E push(E obj)
	{
		addFirst(obj);
		return obj;
	}

	@Override
	public E peek()
	{
		// NB: This does not conform to the specification in the Queue
		// interface because the StackInt and the Queue interfaces are in
		// conflict about how peek() should deal with an empty collection
		// I have retained the StackInt behavior here, but the Queue
		// interface says that this should return null. In a real application,
		// this conflict would mean that no class could implement both
		// StackInt and Queue. More likely, StackInt would have to change
		// to conform to the standard Queue.
		if(size == 0)
		{
			throw new EmptyStackException();
		}
		
		return get(0);
	}

	@Override
	public E pop()
	{
		if(size == 0)
		{
			throw new EmptyStackException();
		}
		
		return removeFirst();
	}

	@Override
	public boolean empty()
	{
		return size == 0;
	}
	
	/******************************************************
	 * Iterator methods
	 ******************************************************/
	@Override
	public Iterator<E> iterator()
	{
		return new SLLIterator<>(this);
	}

	/******************************************************
	 * Collection methods
	 ******************************************************/
	
	/** Finds the index of the first occurrence of the given element in
	 *  this list, using the 'equals' method of the target. If the target
	 *  is null, finds the first null element of this list.
	 *  @param target The target value being sought.
	 *  @return The index of target, or -1 if no target is found.
	 */
	public int indexOf(Object target)
	{
		int index = 0;
		for(E e : this)
		{
			if(target == null)
			{
				if(e == null)
				{
					return index;
				}
			}
			else if(target.equals(e))
			{
				return index;
			}
			
			index++;
		}
		
		return -1;
	}
	
	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public boolean contains(Object o)
	{
		return indexOf(o) >= 0;
	}

	@Override
	public boolean remove(Object o)
	{
		int index = indexOf(o);
		if(index < 0)
		{
			return false;
		}
		else
		{
			remove(index);
			return true;
		}
	}
	
	@Override
	public Object[] toArray()
	{
		Object[] result = new Object[size];
		
		Node<E> current = head;
		for(int i = 0; i < size; i++)
		{
			result[i] = current.data;
			current = current.next;
		}
		
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a)
	{
		// This uses more advanced magic than I expect in CS 206!
		if(a.length < size)
		{
			// not big enough. Make a new one.
			a = (T[])Array.newInstance(a.getClass().getComponentType(), size);
		}
		else if(a.length > size)
		{
			a[size] = null;
		}
		
		Node<E> current = head;
		for(int i = 0; i < size; i++)
		{
			a[i] = (T)current.data;
			current = current.next;
		}
				
		return a;
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		for(Object o : c)
		{
			if(!contains(o))
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		for(E e : c)
		{
			add(e);
		}
		
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		boolean changed = false;
		for(Object o : c)
		{
			changed |= remove(o);
		}
		
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		boolean changed = false;
		
		// remove a prefix of elements
		while(size > 0 && !c.contains(head.data))
		{
			removeFirst();
			changed = true;
		}
		
		if(size == 0)
		{
			return changed;
		}
		
		Node<E> current = head;
		while(current.next != null)
		{
			if(!c.contains(current.next.data))
			{
				removeAfter(current);
				changed = true;
			}
			else
			{
				current = current.next;
			}
		}
		
		return changed;
	}

	@Override
	public void clear()
	{
		head = tail = null;
		size = 0;
	}

	/******************************************************
	 * Queue methods
	 ******************************************************/
	
	@Override
	public boolean offer(E e)
	{
		add(e);
		return true;
	}

	@Override
	public E remove()
	{
		return remove(0);
	}

	@Override
	public E poll()
	{
		if(size == 0)
		{
			return null;
		}
		else
		{
			return remove();
		}
	}

	@Override
	public E element()
	{
		return get(0);
	}
}
