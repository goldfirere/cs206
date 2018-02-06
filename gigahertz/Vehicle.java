/* Name: Richard Eisenberg
 * File: Vehicle.java
 * Desc: Base class for vehicles that can be rented from Gigahertz
 */

public abstract class Vehicle
{
	private String name; // the name (make & model) of the car
	private String theRenter; // the person renting; null if not rented
	private double miles; // total number of miles driven
	private double milesUntilOilChange; // # of miles until next oil change is necessary

	/**
	 * constructs a new Vehicle object with 0 miles on it and currently
	 * not rented.
	 */
	public Vehicle()
	{
		theRenter = null; // starts out unrented
		miles = 0; // no miles yet

		changeOil(); // vehicles start with fresh oil
	}

	/** sets the name
	 * @param newName the new name
	 */
	public void setName(String newName)
	{
		name = newName;
	}

	/**
	 * marks the vehicle as rented by the person whose name is given in
	 * the parameter
	 *
	 * pre-condition: the vehicle has not been rented
	 * post-condition: the vehicle is marked as rented by "renter"
	 * 
	 * @param renter the new renter
	 */
	public void rent(String renter)
	{
		if(theRenter != null)
		{
			// we're already rented
			System.out.println("Error: Renting vehicle that "
					+ "is already rented.");
		}
		else
		{
			theRenter = renter;
		}
	}

	/**
	 * marks the vehicle as returned back to the lot. This method should
	 * do nothing if the vehicle was on the lot to begin with.
	 *
	 * pre-condition: the vehicle is rented; milesDriven > 0
	 * post-conditions: the vehicle is no longer rented; its mileage has
	 * 					been updated
	 * 
	 * @param milesDriven The number of miles driven since it was rented
	 */
	public void bringBack(double milesDriven)
	{
		if(theRenter == null)
		{
			// but we never rented it!
			System.out.println("Error: Returning vehicle before renting.");
		}
		else
		{
			theRenter = null; // remove the renter
			miles += milesDriven; // note the mileage

			milesUntilOilChange -= milesDriven;
			if(milesUntilOilChange < 0) // can't be negative
			{
				milesUntilOilChange = 0;
			}
		}
	}

	/**
	 * @return the name of the renter, if there is one. If the vehicle is
	 * not currently rented, returns null.
	 */
	public String getRenter()
	{
		return theRenter;
	}

	/** @return the number of miles this vehicle has driven */
	public double getMiles()
	{
		return miles;
	}

	/** @return the name (make & model) of the vehicle */
	public String getName()
	{
		return name;
	}

	/** @return the number of miles to go until needing an oil change */
	public double getMilesUntilOilChange()
	{
		return milesUntilOilChange;
	}

	/** Changes the oil. Precondition: needs an oil change
	 * Postcondition: milesUntilOilChange is reset
	 */
	public void changeOil()
	{
		milesUntilOilChange = getDistanceBetweenOilChanges();
	}

	/** @returns the number of miles between oil changes
	 */
	public abstract double getDistanceBetweenOilChanges();
	
	/** @return information about this vehicle
	 */
	public abstract String getDescription();
}
