// This file is taken from Koffman & Wolfgang's
// "Data Structures: Abstraction and Design Using Java", 2nd Edition
// Pub. John Wiley & Sons, Inc., 2010.
// with minor modifications by Richard Eisenberg

import java.util.*;

/** This class implements some of the methods of the Java
 *  ArrayList class, but using Objects instead of a generic type parameter
 */
public class KWArrayListRaw
{
	/** The default initial capacity */
	private static final int INITIAL_CAPACITY = 10;
	
	/** The underlying data array */
	private Object[] theData;
	
	/** The current size */
	private int size = 0;
	
	/** The current capacity */
	private int capacity = 0;
	
	/** Creates a list with an initial capacity of INITIAL_CAPACITY
	 */
	public KWArrayListRaw()
	{
		capacity = INITIAL_CAPACITY;	
		theData = new Object[capacity];
	}
	
	/** Adds a new element to this list.
	 *  @param anEntry The new element
	 *  @return true, always
	 */
	public boolean add(Object anEntry)
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
	public void add(int index, Object anEntry)
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
	public Object get(int index)
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
	public Object set(int index, Object newValue)
	{
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		
		Object oldValue = theData[index];
		theData[index] = newValue;
		return oldValue;
	}
	
	/** Removes the element at the specified index, moving any later
	 *  elements to the left
	 *  @param index The index at which to remove an element
	 *  @return The element removed
	 *  @throws IndexOutOfBoundsException if the index < 0 or >= size
	 */
	public Object remove(int index)
	{
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException("" + index);
		}
		
		Object returnValue = theData[index];
		for(int i = index + 1; i < size; i++)
		{
			theData[i - 1] = theData[i];
		}
		size--;
		return returnValue;
	}
	
	/** Doubles the capacity of this list, preserving the elements
	 */
	private void reallocate()
	{
		capacity = 2 * capacity;
		theData = Arrays.copyOf(theData, capacity);
	}
}
