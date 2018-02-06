/* Name: Richard Eisenberg
 * File: Gigahertz.java
 * Desc: User interface for rental agency software
 */
import java.util.*;

public class Gigahertz
{
	// prints out a menu of all the vehicles on the lot
	public static void displayVehiclesOnLot(ArrayList<Vehicle> vehicles)
	{
		int vehNum = 0;
		for(Vehicle v : vehicles)
		{
			if(v.getRenter() == null) // if it's on the lot:
			{
				System.out.println(vehNum + ". " + v.getName());
			}
			vehNum++;
		}
	}

	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

		System.out.println("Welcome to Gigahertz!");

		// set up inventories:
		ArrayList<Vehicle> vehicles = new ArrayList<>();

		boolean keepGoing = true;
		while(keepGoing)
		{
			System.out.println();

			// print main menu:
			System.out.println("Main menu:");
			System.out.println("\t1. Rent vehicle");
			System.out.println("\t2. Return vehicle");
			System.out.println("\t3. View inventory");
			System.out.println("\t4. Add car to inventory");
			System.out.println("\t5. Add truck to inventory");
			System.out.println("\t6. Quit");

			System.out.print(">");

			int choice = in.nextInt();
			in.nextLine(); // flush the buffer

			if(choice == 1)
			{
				// rent car
				System.out.println("Which vehicle would " +
								   "you like to rent?");
				displayVehiclesOnLot(vehicles);
				System.out.print(">");

				int vehicleIndex = in.nextInt();
				in.nextLine();

				if(vehicleIndex < 0 || vehicleIndex >= vehicles.size())
				{
					System.out.println("Error: vehicle index " +
									   "out of range.");
				}
				else
				{
					Vehicle vehicleToBeRented =
									vehicles.get(vehicleIndex);
					if(vehicleToBeRented.getRenter() != null)
					{
						System.out.println("Error: user chose to "
								+ "rent a rented vehicle.");
					}
					else
					{
						System.out.print("And your name is? ");
						String name = in.nextLine();

						vehicleToBeRented.rent(name);
					}
				}
			}
			else if(choice == 2)
			{
				// return vehicle
				System.out.println("Which vehicle would " +
								   "you like to return?");

				int vehNum = 0;
				for(Vehicle v : vehicles)
				{
					if(v.getRenter() != null)
					{
						System.out.println(vehNum + ". " + v.getName());
					}

					vehNum++;
				}

				System.out.print(">");

				int vehicleIndex = in.nextInt();
				in.nextLine();

				if(vehicleIndex < 0 || vehicleIndex >= vehicles.size())
				{
					System.out.println("Error: vehicle index " +
									   "out of range.");
				}
				else
				{
					Vehicle toBeReturned = vehicles.get(vehicleIndex);
					if(toBeReturned.getRenter() == null)
					{
						System.out.println("Error: user chose to "
								+ "return an unrented vehicle.");
					}
					else
					{
						System.out.print("How many miles did "
								+ "you drive? ");
						double miles = in.nextDouble();
						in.nextLine();

						toBeReturned.bringBack(miles);
					}
				}
			}
			else if(choice == 3)
			{
				// view vehicles
				int vehNum = 0;
				for(Vehicle v : vehicles)
				{
					System.out.print(vehNum + ". " + v.getDescription() + ", ");

					String renter = v.getRenter();
					if(renter == null)
					{
						System.out.println("on the lot");
					}
					else
					{
						System.out.println("rented by " + renter);
					}

					vehNum++;
				}
			}
			else if(choice == 4)
			{
				// add car to inventory
				System.out.print("What is the make and "
						+ "model of the car? ");
				String carName = in.nextLine();
				
				System.out.print("How many passengers can it seat? ");
				int numPassengers = in.nextInt();
				in.nextLine(); // necessary to avoid glitch in Scanner

				vehicles.add(new Car(carName, numPassengers));
			}
			else if(choice == 5)
			{
				// add truck to inventory
				System.out.print("What is the make and "
						+ "model of the truck? ");
				String truckName = in.nextLine();
				
				System.out.print("How many pounds can it carry? ");
				double freight = in.nextDouble();
				in.nextLine(); // necessary to avoid glitch in Scanner

				vehicles.add(new Truck(truckName, freight));
			}
			else if(choice == 6)
			{
				// quit
				keepGoing = false;
			}
			else
			{
				System.out.println("Invalid selection.  Try again.");
			}
		} // end of while(keepGoing)

		System.out.println("Thanks for using Gigahertz!");
	} // end of main
}
