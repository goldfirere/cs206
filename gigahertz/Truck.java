/* Name: Richard Eisenberg
 * File: Truck.java
 * Desc: Represents a truck owned by Gigahertz
 */

public class Truck extends Vehicle
{
	private double freight; // the amount of freight (in pounds) this truck can carry

	/**
	 * constructs a new Truck object with 0 miles on it and currently not
	 * rented.
	 * 
	 * @param makeAndModel The make and model of the truck
	 * @param fr the freight capacity
	 */
	public Truck(String makeAndModel, double fr)
	{
		setName(makeAndModel);
		freight = fr;
	}

	/** @return freight capacity
	 */
	public double getFreight()
	{
		return freight;
	}

	/** @return the number of miles between oil changes
	 */
	@Override
	public double getDistanceBetweenOilChanges()
	{
		return 5000;
	}
	
	/** @return a description of this truck
	 */
	@Override
	public String getDescription()
	{
		String result = getName() + ", "
				+ getMiles() + " miles (" + getMilesUntilOilChange()
				+ " miles until next oil change), ";
		result += "freight capacity of " +
					getFreight() +
					" pounds, ";
		
		String renter = getRenter();
		if(renter == null)
		{
			result += "on the lot";
		}
		else
		{
			result += "rented by " + renter;
		}
		
		return result;
	}
}
