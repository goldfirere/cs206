/* Name: Richard Eisenberg
 * File: BMCMap.java
 * Desc: An interface for an associative map
 */

public interface BMCMap<K,V>
{
	/** Associates the specified value with the specified key.
	 *  @param key The key in the association
	 *  @param value The value to associate that key with.
	 */
	void put(K key, V value);
	
	/** Retrieves the value associated with a key.
	 *  @param key The key to look up.
	 *  @return The value associated with that key, or null if none exists
	 */
	V get(K key);
	
	/** Removes a mapping from the given key.
	 *  @param key The key whose mapping should be removed.
	 *  @return The value that was associated with the key, or null
	 *          if no such value exists.
	 */
	void remove(K key);
	
	/** Checks to see whether a key is mapped.
	 *  @param key The key to check
	 *  @return true if this map maps the given key; false otherwise
	 */
	boolean containsKey(K key);
	
	/** @return true if there are no associations in this map */
	boolean isEmpty();
	
	/** @return The number of mappings in this map */
	int size();
	
	/** Removes all entries from this map. */
	void clear();
}
