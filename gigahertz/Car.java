/* Name: Richard Eisenberg
 * File: Car.java
 * Desc: Represents a Car to be rented at Gigahertz Rental Agency
 */

public class Car extends Vehicle
{
	private int numPassengers; // the number of passengers (including driver) this car can hold

	/**
	 * Constructs a new Car object with 0 miles on it and currently not
	 * rented. 
	 * @param makeAndModel The make and model of the car
	 * @param numPass the number of passengers
	 */
	public Car(String makeAndModel, int numPass)
	{
		setName(makeAndModel);

		numPassengers = numPass;
	}

	/** @return the number of passengers
	 */
	public int getNumPassengers()
	{
		return numPassengers;
	}

	/** @return the distance between necessary oil changes
	 */
	@Override
	public double getDistanceBetweenOilChanges()
	{
		return 3000;
	}
	
	/** @return a description of this vehicle
	 */
	@Override
	public String getDescription()
	{
		String result = getName() + ", "
				+ getMiles() + " miles (" + getMilesUntilOilChange()
				+ " miles until next oil change), ";
		result += "capacity of " +
					getNumPassengers() +
					" passengers, ";
		
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
