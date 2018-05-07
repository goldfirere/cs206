/* Name: Richard Eisenberg
 * File: Entry.java
 * Desc: A key/value pair to be used as an entry in a map
 */

public class Entry<K,V>
{
	private K key;
	private V value;
	
	/** Constructs an entry with the given key and value
	 *  @param k The key
	 *  @param v The value
	 */
	public Entry(K k, V v)
	{
		key = k;
		value = v;
	}
	
	/** @return The key in this Entry */
	public K getKey()
	{
		return key;
	}
	
	/** @return The value in this Entry */
	public V getValue()
	{
		return value;
	}
	
	/** Updates the value in this Entry
	 *  @param v The new value
	 */
	public void setValue(V v)
	{
		value = v;
	}
}
