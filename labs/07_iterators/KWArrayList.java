// This file is taken from Koffman & Wolfgang's
// "Data Structures: Abstraction and Design Using Java", 2nd Edition
// Pub. John Wiley & Sons, Inc., 2010.
// with minor modifications by Richard Eisenberg

import java.util.*;

/** This class implements some of the methods of the Java
 *  ArrayList class.
 */
public class KWArrayList<E>
{
	/** The default initial capacity */
	private static final int INITIAL_CAPACITY = 10;
	
	/** The underlying data array */
	private E[] theData;
	
	/** The current size */
	private int size = 0;
	
	/** The current capacity */
	private int capacity = 0;
	
	// INVARIANT: theData.length == capacity
	// INVARIANT: size <= capacity
	
	/** Creates a list with an initial capacity of INITIAL_CAPACITY
	 */
	@SuppressWarnings("unchecked")
	public KWArrayList()
	{
		capacity = INITIAL_CAPACITY;	
		theData = (E[]) new Object[capacity];
	}
	
	/** Adds a new element to this list.
	 *  @param anEntry The new element
	 *  @return true, always
	 */
	public boolean add(E anEntry)
	{
		if(size == capacity)
		{
			reallocate();
		}
		
		theData[size] = anEntry;
		size++;
		return true;
	}
	
	/** Adds a new element at the specified index to this list.
	 *  Later elements, if any, are moved to the right.
	 *  @param index Where to add this element; must be >= 0 and <= size
	 *  @param anEntry The new value to add
	 *  @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	public void add(int index, E anEntry)
	{
		if(index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		
		if(size == capacity)
		{
			reallocate();
		}
		
		// Shift data in elements from index to size - 1
		for(int i = size; i > index; i--)
		{
			theData[i] = theData[i - 1];
		}
		
		// Insert the new item
		theData[index] = anEntry;
		size++;
	}
	
	/** Get the element at a specified index
	 *  @param index The index at which to retrieve the item
	 *  @return The item at that index
	 *  @throws IndexOutOfBoundsException if the index is < 0 or >= size
	 */
	public E get(int index)
	{
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		
		return theData[index];
	}
	
	/** Change the value at a specified index
	 *  @param index The index at which to change the value
	 *  @param newValue The new value to be stored at that index
	 *  @return The old value at that index
	 *  @throws IndexOutOfBoundsException if the index < 0 or >= size
	 */
	public E set(int index, E newValue)
	{
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		
		E oldValue = theData[index];
		theData[index] = newValue;
		return oldValue;
	}
	
	/** Removes the element at the specified index, moving any later
	 *  elements to the left
	 *  @param index The index at which to remove an element
	 *  @return The element removed
	 *  @throws IndexOutOfBoundsException if the index < 0 or >= size
	 */
	public E remove(int index)
	{
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		
		E returnValue = theData[index];
		for(int i = index + 1; i < size; i++)
		{
			theData[i - 1] = theData[i];
		}
		size--;
		return returnValue;
	}
	
	/** @return the size of this list
	 */
	public int size()
	{
		return size;
	}
	
	/** Doubles the capacity of this list, preserving the elements
	 *  Precondition: size == capacity
	 */
	private void reallocate()
	{
		capacity = 2 * capacity;
		theData = Arrays.copyOf(theData, capacity);
	}
}
