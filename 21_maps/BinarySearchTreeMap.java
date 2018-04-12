/* Name: Richard Eisenberg
 * File: BinarySearchTreeMap.java
 * Desc: Implements a binary search tree
 * 
 * Note: This is heavily based on implementations in
 * "Data Structures: Abstraction and Design Using Java", 2nd Edition
 * Pub. John Wiley & Sons, Inc., 2010.
 */

public class BinarySearchTreeMap<K extends Comparable<K>,V>
  implements BMCMap<K,V>
{
	private Node<K,V> root; // the root of the tree
	private int size; // number of elements in this set
	
	/** Constructs an empty tree */
	public BinarySearchTreeMap()
	{
		root = null;
		size = 0;
	}

	@Override
	public void put(K key, V value)
	{
		root = addRec(root, key, value);
	}
	
	/** Adds an item into the tree rooted at 'where', returning the
	 *  new root of the tree. If a new node is created, increments
	 *  the size.
	 *  @param where The root of the tree to add to.
	 *  @param key The key to add
	 *  @param value The value to add
	 *  @return The new root of the tree.
	 */
	private Node<K,V> addRec(Node<K,V> where, K key, V value)
	{
		if(where == null)
		{
			size++;
			return new Node<>(key, value);
		}
		
		int cmp = key.compareTo(where.key);
		
		if(cmp < 0)
		{
			where.left = addRec(where.left, key, value);
			return where;
		}
		else if(cmp > 0)
		{
			where.right = addRec(where.right, key, value);
			return where;
		}
		else
		{
			return where;
		}
	}

	@Override
	public boolean containsKey(K item)
	{
		return containsKeyRec(root, item);
	}

	/** Recursively searches for an item in the tree rooted at 'where'
	 *  @param where The root of the tree to search in
	 *  @param item The item to search for
	 *  @return true if the item is found; false otherwise
	 */
	private boolean containsKeyRec(Node<K,V> where, K item)
	{
		if(where == null)
		{
			return false;
		}
		
		int cmp = item.compareTo(where.key);
		
		if(cmp < 0)
		{
			return containsKeyRec(where.left, item);
		}
		else if(cmp > 0)
		{
			return containsKeyRec(where.right, item);
		}
		else
		{
			return true;
		}
	}

	@Override
	public V get(K item)
	{
		return getRec(root, item);
	}

	/** Recursively searches for an item in the tree rooted at 'where'
	 *  @param where The root of the tree to search in
	 *  @param item The item to search for
	 *  @return The value mapped by the item, or null if no such value
	 */
	private V getRec(Node<K,V> where, K item)
	{
		if(where == null)
		{
			return null;
		}
		
		int cmp = item.compareTo(where.key);
		
		if(cmp < 0)
		{
			return getRec(where.left, item);
		}
		else if(cmp > 0)
		{
			return getRec(where.right, item);
		}
		else
		{
			return where.value;
		}
	}

	
	@Override
	public void remove(K item)
	{
		root = removeRec(root, item);
	}
	
	/** Removes an item from the tree rooted at 'where', if it exists.
	 *  (Does nothing if the item is not found.)
	 *  Decrements 'size' as appropriate.
	 *  @param where The tree to look in
	 *  @param item The item to remove.
	 *  @return The new root of the tree, with 'item' removed
	 */
	private Node<K,V> removeRec(Node<K,V> where, K item)
	{
		if(where == null)
		{
			return null; // nothing to delete
		}
		
		int cmp = item.compareTo(where.key);
		if(cmp < 0)
		{
			where.left = removeRec(where.left, item);
			return where;
		}
		else if(cmp > 0)
		{
			where.right = removeRec(where.right, item);
			return where;
		}
		else
		{ // we've found it. Now we have to look at the children
			size--; // we're decrementing size no matter what.
			
			if(where.left == null)
			{
				return where.right; // even if this is null, that's OK
			}
			else if(where.right == null)
			{
				return where.left;
			}
			else
			{ // urgh. two children.
				// replace the data with the inorder predecessor ("ip")
				if(where.left.right == null)
				{
					// The left child has no right child.
					// Replace the data with the data in the left child.
					where.key = where.left.key;
					where.value = where.left.value;
					where.left = where.left.left;
					return where;
				}
				else
				{
					// Search for the ip and replace deleted note's data with ip
					Node<K,V> n = findLargestChild(where.left);
					where.key = n.key;
					where.value = n.value;
					return where;
				}
			}
		}
	}
	
	/** Find the node that is the inorder predecessor and replace it
	 *  with its left child (if any).
	 *  Pre-condition: The right child of the parent is non-null.
	 *  Post-condition: the inorder predecessor is removed from the tree.
	 *  @param parent The parent of possible inorder predecessor
	 *  @return The data in the inorder predecessor
	 */
	private Node<K,V> findLargestChild(Node<K,V> parent)
	{
		if(parent.right.right == null)
		{
			Node<K,V> returnValue = parent.right;
			parent.right = parent.right.left;
			return returnValue;
		}
		else
		{
			return findLargestChild(parent.right);
		}
	}

	@Override
	public void clear()
	{
		root = null;
		size = 0;
	}

	@Override
	public boolean isEmpty()
	{
		return root == null;
	}

	@Override
	public int size()
	{
		return size;
	}
	
	/** Prints out all elements in this tree, in order.
	 *  (That is, this is an inorder traversal.)
	 */
	public void printInOrder()
	{
		printInOrderRec(root);
	}
	
	/** Prints out all elements in the tree rooted at 'where', in order.
	 *  @param where The root of the tree to print.
	 */
	private void printInOrderRec(Node<K,V> where)
	{
		if(where != null)
		{
			printInOrderRec(where.left);
			System.out.println(where);
			printInOrderRec(where.right);
		}
	}
}
