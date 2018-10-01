//Created by Brianne Kerr, 9/26/18
import java.util.LinkedList;
import java.util.Random;

public class Intersection
{
	//Holds list of vehicles going in a specified direction
	//entryPoint[0] = NORTH, entryPoint[1] = EAST,
	//entryPoint[2] = SOUTH, entryPoint[3] = WEST
	private LinkedList<Vehicle>[] entryPoint = new LinkedList[4];
	
	//Holds the number of vehicles that have passed through
	//a lane in a specified direction; corresponds to entryPoint[]
	private int[] entryPointCounter = new int[4];

	//Holds the number of cars that have passed through the intersection
	private int interCounter = 0;

	//Holds the time that EAST and WEST lanes are green, per cycle
	private int horizCycleTime;
	
	//Holds the number of cars that pass through lanes EAST and WEST, per cycle
	private int horizCycleCars;
	
	//Holds the time that NORTH and SOUTH lanes are green, per cycle
	private int vertCycleTime;
	
	//Holds the number of cars that pass through lanes NORTH and SOUTH, per cycle
	private int vertCycleCars;
	

	
	//Used to generate pseudo-random numbers
	private Random rand = new Random();
	
	/*
	 * Initialize each entry point with an empty linked list
	 */
	public Intersection()
	{
		entryPoint[0] = new LinkedList<Vehicle>();
		entryPoint[1] = new LinkedList<Vehicle>();
		entryPoint[2] = new LinkedList<Vehicle>();
		entryPoint[3] = new LinkedList<Vehicle>();
		
		entryPointCounter[0] = 0;
		entryPointCounter[1] = 0;
		entryPointCounter[2] = 0;
		entryPointCounter[3] = 0;
		
	}
	
	/*
	 * Initialize each entry point with a linked list with a specified 
	 * number of random vehicles
	 */
	public Intersection(int westStart, int northStart, int eastStart, 
			int southStart, int horizCycleTime, int horizCycleCars, int vertCycleTime, 
			int vertCycleCars)
	{
		entryPoint[0] = new LinkedList<Vehicle>();
		entryPoint[1] = new LinkedList<Vehicle>();
		entryPoint[2] = new LinkedList<Vehicle>();
		entryPoint[3] = new LinkedList<Vehicle>();
		
		initializeRandom(Direction.NORTH, northStart);
		initializeRandom(Direction.EAST, eastStart);
		initializeRandom(Direction.SOUTH, southStart);
		initializeRandom(Direction.WEST, westStart);

		
		entryPointCounter[0] = 0;
		entryPointCounter[1] = 0;
		entryPointCounter[2] = 0;
		entryPointCounter[3] = 0;
		
		this.horizCycleTime = horizCycleTime;
		this.horizCycleCars = horizCycleCars;
		this.vertCycleTime = vertCycleTime;
		this.vertCycleCars = vertCycleCars;
	}
	
	/*
	 * creates the specified number of vehicles of a random type
	 * to the specified entryPoint linkedList
	 */
	private void initializeRandom(Direction dir, int numVehicles)
	{	
		for(int x = 0; x < numVehicles; x++)
		{
			int type = rand.nextInt(2);
			Vehicle veh;
			
			switch(type)
			{
			case 0:
				veh = new Car();
				break;
			case 1:
				veh = new SemiTruck();
				break;
			default:
				veh = new Car();
				break;
			}
			
			this.addVehicle(veh, dir);
		}
	}
	
	/*
	 * Takes in a Vehicle and adds it to the specified 
	 * Direction (NORTH, SOUTH, EAST, WEST)
	 */
	public void addVehicle(Vehicle veh, Direction directTo)
	{
		switch (directTo)
		{
		case NORTH:
			entryPoint[0].push(veh);
			break;
		case EAST:
			entryPoint[1].push(veh);
			break;		
		case SOUTH:
			entryPoint[2].push(veh);
			break;
		case WEST:
			entryPoint[3].push(veh);
			break;
		}
	}
	
	/*
	 *Pops the front vehicle from the linked list of the specified
	 *Direction (NORTH, SOUTH, EAST, WEST) and returns the vehicle.
	 *Also adds to the interCounter (number of vehicles that have passed
	 *through the intersection)
	 */
	public Vehicle sendVehicle(Direction directFrom)
	{
		interCounter++;
		
		switch (directFrom) {
		case NORTH:
			entryPointCounter[0]++;
			return entryPoint[0].pop();
		case EAST:
			entryPointCounter[0]++;
			return entryPoint[1].pop();
		case SOUTH:
			entryPointCounter[0]++;
			return entryPoint[2].pop();
		case WEST:
			entryPointCounter[0]++;
			return entryPoint[3].pop();
		default:
			return null;
		}
		
	}

	/*
	 * returns the number of cars that go through each horizontal cycle
	 */
	public int getHorizCycleCars() 
	{
		return horizCycleCars;
	}

	/*
	 * returns the length of time of each horizontal cycle
	 */
	public int getHorizCycleTime() 
	{
		return horizCycleTime;
	}

	/*
	 * returns the number of cars that go through each vertical cycle
	 */
	public int getVertCycleCars() 
	{
		return vertCycleCars;
	}
	
	/*
	 * returns the length of time of each vertical cycle
	 */
	public int getVertCycleTime() 
	{
		return vertCycleTime;
	}


}
