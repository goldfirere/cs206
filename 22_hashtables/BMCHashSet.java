/* Name: Richard Eisenberg
 * File: BMCHashSet.java
 * Desc: A hashtable implementation of a set
 */
import java.lang.reflect.*;
import java.util.*;

public class BMCHashSet<E> implements BMCSet<E>, Iterable<E>
{
	private LinkedList<E>[] table; // the array of linked lists
	private int size; // the number of items in the set
	
	// the default number of slots in the table
	private final static int NUM_INITIAL_SLOTS = 10;
	
	// the maximum load factor; after this is breached, we rehash
	private final static double MAX_LOAD_FACTOR = 3.0;
	
	/** Construct an empty BMCHashSet */
	public BMCHashSet()
	{
		table = newArray(NUM_INITIAL_SLOTS);
		size = 0;
	}
	
	/** Choose the slot number an item should belong in
	 *  @param x The item to look up
	 *  @return The slot number. 0 <= slot number < table.length
	 */
	private int hash(E x)
	{
		return Math.abs(x.hashCode()) % table.length;
	}
	
	@Override
	public boolean add(E x)
	{
		int hash = hash(x);
		
		if(table[hash] == null)
		{
			table[hash] = new LinkedList<>();
		}
		
		for(E elem : table[hash])
		{
			if(x.equals(elem))
			{
				return false;
			}
		}
		
		table[hash].add(x);
		size++;
		
		if(1.0 * size / table.length > MAX_LOAD_FACTOR)
		{
			rehash();
		}
		
		return true;
	}
	
	@Override
	public boolean contains(E x)
	{
		int hash = hash(x);
		
		if(table[hash] == null)
		{
			return false;
		}
		
		for(E elem : table[hash])
		{
			if(x.equals(elem))
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean remove(E x)
	{
		int hash = hash(x);
		
		if(table[hash] == null)
		{
			return false;
		}
		
		for(Iterator<E> itor = table[hash].iterator(); itor.hasNext(); )
		{
			if(itor.next().equals(x))
			{
				itor.remove();
				size--;
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int size()
	{
		return size;
	}

	@Override
	public void clear()
	{
		table = newArray(NUM_INITIAL_SLOTS);
		size = 0;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	/** Reallocate space in the hashtable. This increases the number
	 *  of slots to match the current size of the hashtable.
	 */
	private void rehash()
	{
		LinkedList<E>[] oldTable = table;
		int oldSize = size;
		
		table = newArray(oldSize);
		size = 0; // adding will re-increment this
		
		for(LinkedList<E> list : oldTable)
		{
			for(E item : list)
			{
				add(item);
			}
		}
	}
	
	/** Allocate space for an array of linked lists. This is in a
	 *  separate method because it uses advanced Java features that
	 *  are out of scope for CS206.
	 *  @param len The desired number of slots
	 *  @return The array of linked lists to use as the table
	 */
	@SuppressWarnings("unchecked")
	private static <E> LinkedList<E>[] newArray(int len)
	{
		return (LinkedList<E>[])new LinkedList<?>[len];
	}
	
	/** This internal class provides an iterator over a BMCHashSet
	 *  Note that the order this iterator returns elements is completely
	 *  arbitrary.
	 */
	private static class BMCHSIterator<E> implements Iterator<E>
	{
		private LinkedList<E>[] slots; 
		private int whichSlot;
		private Iterator<E> whichItem;
		
		public BMCHSIterator(BMCHashSet<E> parent)
		{
			slots = parent.table;
			
			whichSlot = -1; // findNext increments this
			whichItem = null;
			
			findNext();
		}
		
		private void findNext()
		{
			while(whichSlot < slots.length - 1 &&
					  (whichItem == null || !whichItem.hasNext()))
			{
				whichSlot++;
				if(slots[whichSlot] != null)
				{
					whichItem = slots[whichSlot].iterator();
				}
			}
		}
		
		@Override
		public boolean hasNext()
		{
			return whichSlot < slots.length && 
						 whichItem != null &&
						 whichItem.hasNext();
		}
		
		@Override
		public E next()
		{
			E result = whichItem.next();
			findNext();
			return result;
		}
	}
	
	@Override
	public Iterator<E> iterator()
	{
		return new BMCHSIterator<>(this);
	}
}
