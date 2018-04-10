/* Name: Richard Eisenberg
 * File: BinarySearchTree.java
 * Desc: Implements a binary search tree
 * 
 * Note: This is heavily based on implementations in
 * "Data Structures: Abstraction and Design Using Java", 2nd Edition
 * Pub. John Wiley & Sons, Inc., 2010.
 */

public class BinarySearchTree<E extends Comparable<E>>
  implements BMCSet<E>
{
	private Node<E> root; // the root of the tree
	private int size; // number of elements in this set
	
	/** Constructs an empty tree */
	public BinarySearchTree()
	{
		root = null;
		size = 0;
	}

	@Override
	public boolean add(E item)
	{
		int oldSize = size;
		root = addRec(root, item);
		return oldSize != size;
	}
	
	/** Just like 'add', but uses a different algorithm.
	 *  @param item The item to add.
	 *  @return true if a new item was added; false otherwise
	 */
	public boolean addIter(E item)
	{
		if(root == null)
		{
			root = new Node<>(item);
			size++;
			return true;
		}
		
		Node<E> current = root;
		while(true)
		{
			int cmp = item.compareTo(current.data);
			if(cmp < 0)
			{
				if(current.left == null)
				{
					current.left = new Node<>(item);
					size++;
					return true;
				}
				else
				{
					current = current.left;
				}
			}
			else if(cmp > 0)
			{
				if(current.right == null)
				{
					current.right = new Node<>(item);
					size++;
					return true;
				}
				else
				{
					current = current.right;
				}
			}
			else
			{
				return false; // the node is in the tree
			}
		}
	}
	
	/** Adds an item into the tree rooted at 'where', returning the
	 *  new root of the tree. If a new node is created, increments
	 *  the size.
	 *  @param where The root of the tree to add to.
	 *  @return The new root of the tree.
	 */
	private Node<E> addRec(Node<E> where, E item)
	{
		if(where == null)
		{
			size++;
			return new Node<>(item);
		}
		
		int cmp = item.compareTo(where.data);
		
		if(cmp < 0)
		{
			where.left = addRec(where.left, item);
			return where;
		}
		else if(cmp > 0)
		{
			where.right = addRec(where.right, item);
			return where;
		}
		else
		{
			return where;
		}
	}

	@Override
	public boolean contains(E item)
	{
		return containsRec(root, item);
	}
	
	/** Just like 'contains', but implemented iteratively.
	 *  @param item The item to search for
	 *  @return true if the item is in the tree; false otherwise
	 */
	public boolean containsIter(E item)
	{
		Node<E> current = root;
		
		while(current != null)
		{
			int cmp = item.compareTo(current.data);
			
			if(cmp < 0)
			{
				current = current.left;
			}
			else if(cmp > 0)
			{
				current = current.right;
			}
			else
			{
				return true; // we found the data
			}
		}
		
		return false; // this means that current became null
	}

	/** Recursively searches for an item in the tree rooted at 'where'
	 *  @param where The root of the tree to search in
	 *  @param item The item to search for
	 *  @return true if the item is found; false otherwise
	 */
	private boolean containsRec(Node<E> where, E item)
	{
		if(where == null)
		{
			return false;
		}
		
		int cmp = item.compareTo(where.data);
		
		if(cmp < 0)
		{
			return containsRec(where.left, item);
		}
		else if(cmp > 0)
		{
			return containsRec(where.right, item);
		}
		else
		{
			return true;
		}
	}
	
	@Override
	public boolean remove(E item)
	{
		int oldSize = size;
		root = removeRec(root, item);
		return oldSize != size;
	}
	
	/** Removes an item from the tree rooted at 'where', if it exists.
	 *  (Does nothing if the item is not found.)
	 *  Decrements 'size' as appropriate.
	 *  @param where The tree to look in
	 *  @param item The item to remove.
	 *  @return The new root of the tree, with 'item' removed
	 */
	private Node<E> removeRec(Node<E> where, E item)
	{
		if(where == null)
		{
			return null; // nothing to delete
		}
		
		int cmp = item.compareTo(where.data);
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
					where.data = where.left.data;
					where.left = where.left.left;
					return where;
				}
				else
				{
					// Search for the ip and replace deleted note's data with ip
					where.data = findLargestChild(where.left);
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
	private E findLargestChild(Node<E> parent)
	{
		if(parent.right.right == null)
		{
			E returnValue = parent.right.data;
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
	private void printInOrderRec(Node<E> where)
	{
		if(where != null)
		{
			printInOrderRec(where.left);
			System.out.println(where.data);
			printInOrderRec(where.right);
		}
	}
}
