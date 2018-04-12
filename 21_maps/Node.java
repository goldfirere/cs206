// This file is taken from Koffman & Wolfgang's
// "Data Structures: Abstraction and Design Using Java", 2nd Edition
// Pub. John Wiley & Sons, Inc., 2010.
// with minor modifications by Richard Eisenberg
// These minor modifications include making definitions public instead
// of protected, for easier experiments.

// This class implements a binary tree node

public class Node<K,V>
{
	// Data Fields
	/** The information stored in this node. */
	public K key;
	public V value;
	
	/** Reference to the left child. */
	public Node<K,V> left;
	/** Reference to the right child. */
	public Node<K,V> right;
	
	// Constructors
	/** Construct a node with given data and no children.
	 *  @param d The data to store in this node
	 */
	public Node(K k, V v)
	{
		key = k;
		value = v;
		left = null;
		right = null;
	}
	
	/** Construct a node with the given data and children.
	 *  @param d The data to store in this node
	 *  @param l This node's left child
	 *  @param r This node's right child
	 */
	public Node(K k, V v, Node<K,V> l, Node<K,V> r)
	{
		key = k;
		value = v;
		left = l;
		right = r;
	}
	
	// Methods
	/** Return a string representation of the node, calling toString on
	 *  the data
	 *  @return A string representation of this node's data
	 */
	@Override
	public String toString()
	{
		return key + ": " + value;
	}
}

