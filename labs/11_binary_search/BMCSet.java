/* Name: Richard Eisenberg
 * File: BMCSet.java
 * Desc: A basic interface for unordered Sets
 */

public interface BMCSet<E>
{
	/** Adds a new item to the set
	 *  @param item The new item to add to the set
	 *  @return true if the item is a new item added to the set;
	 *          false if the item was already in the set
	 */
	boolean add(E item);
	
	/** Checks whether this set contains an item
	 *  @param item The item to check for
	 *  @return true if the item is in the set; false otherwise
	 */
	boolean contains(E item);
	
	/** Removes an item from the set
	 *  @param item The item to remove
	 *  @return true if the item was in the set and removed; false otherwise
	 */
	boolean remove(E item);
	
	/** Removes all items from the set */
	void clear();
	
	/** @return true if the set is empty; false otherwise */
	boolean isEmpty();
	
	/** @return the number of elements in this set */
	int size();
}