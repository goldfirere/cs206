/* Name: Richard Eisenberg
 * File: BMCHashMap.java
 * Desc: A hashtable implementation of a map
 */
import java.util.*;

public class BMCHashMap<K,V> implements BMCMap<K,V>
{
	private LinkedList<Entry<K,V>>[] table; // the array of linked lists
	private int size; // the number of items in the map
	
	// the default number of slots in the table
	private final static int NUM_INITIAL_SLOTS = 10;
	
	// the maximum load factor; after this is breached, we rehash
	private final static double MAX_LOAD_FACTOR = 3.0;
	
	/** Construct an empty BMCHashMap */
	public BMCHashMap()
	{
		table = newArray(NUM_INITIAL_SLOTS);
		size = 0;
	}
	
	/** Choose the slot number an item should belong in
	 *  @param k The item to look up
	 *  @return The slot number. 0 <= slot number < table.length
	 */
	private int hash(K k)
	{
		return Math.abs(x.hashCode()) % table.length;
	}
	
	@Override
	public void put(K k, V v)
	{
		int hash = hash(k);
		
		if(table[hash] == null)
		{
			table[hash] = new LinkedList<>();
		}
		
		for(Entry<K,V> elem : table[hash])
		{
			if(k.equals(elem.getKey()))
			{
				elem.setValue(v);
			}
		}
		
		table[hash].add(new Entry<>(k, v));
		size++;
		
		if(1.0 * size / table.length > MAX_LOAD_FACTOR)
		{
			rehash();
		}
	}
	
	@Override
	public V get(K k)
	{
		int hash = hash(k);
		
		if(table[hash] == null)
		{
			return null;
		}
		
		for(Entry<K,V> elem : table[hash])
		{
			if(k.equals(elem.getKey()))
			{
				return elem.getValue();
			}
		}
		
		return null;
	}

	@Override
	public boolean containsKey(K k)
	{
		int hash = hash(k);
		
		if(table[hash] == null)
		{
			return false;
		}
		
		for(Entry<K,V> elem : table[hash])
		{
			if(k.equals(elem.getKey()))
			{
				return true;
			}
		}
		
		return false;
	}

	
	@Override
	public void remove(K k)
	{
		int hash = hash(k);
		
		if(table[hash] == null)
		{
			return;
		}
		
		for(Iterator<Entry<K,V>> itor = table[hash].iterator(); itor.hasNext(); )
		{
			Entry<K,V> entry = itor.next();
			if(k.equals(entry.getKey()))
			{
				itor.remove();
				size--;
				return;
			}
		}
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
		LinkedList<Entry<K,V>>[] oldTable = table;
		int oldSize = size;
		
		table = newArray(oldSize);
		size = 0; // adding will re-increment this
		
		for(LinkedList<Entry<K,V>> list : oldTable)
		{
			for(Entry<K,V> item : list)
			{
				put(item.getKey(), item.getValue());
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
}
