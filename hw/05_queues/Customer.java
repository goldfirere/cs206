/* Name: Richard Eisenberg
   File: Customer.java
   Desc: Implements a customer shopping at QMart
*/
import java.util.*;

public class Customer
{
  private Queue<String> items; // what items this customer has
  private String name; // the customer's name

  /** Creates a new Customer with the given number of items. The Customer's
   *  name is randomly chosen, as are the contents of the Customer's cart.
   *  This constructor prints the Customer's name and cart contents.
   *  @param numItems The number of items in the customer's cart
   */
  public Customer(int numItems)
  {
    items = new LinkedList<String>(); // make the items queue

    name = Words.getRandomName(); // choose a name
    System.out.println("A new customer " + name + " has arrived.");
    System.out.println("That customer has the following items:");

    for( ; numItems > 0; numItems--)
    {
      String item = Words.getRandomWord();
      System.out.println(item);
      items.add(item);
    }
  }

  /** @return true iff the customer has more items in their cart */
  public boolean hasMoreItems()
  {
    return !items.isEmpty();
  }

  /** Removes and returns one item from the customer's cart.
   *  precondition: the customer has at least one more ite.
   *  @return The next item in the cart
   */
  public String nextItem()
  {
    return items.remove();
  }

  /** @return The name of the customer */
  public String getName()
  {
    return name;
  }
}
