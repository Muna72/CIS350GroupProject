import java.util.LinkedList;
import java.util.Random;
/**
 * This class creates the intersection and the linked lists of vehicles that are entering and leaving the intersection.
 *
 * @author Brianne Kerr
 * @version 1.0
 */
public class Intersection
{
    /**
     * Holds list of vehicles going in a specified direction
     * entryPoint[0] = NORTH, entryPoint[1] = EAST,
     * entryPoint[2] = SOUTH, entryPoint[3] = WEST
     */
    public LinkedList<Vehicle>[] entryPoint = new LinkedList[8];

    /**
     * Holds the number of vehicles that have passed through
     * a lane in a specified direction; corresponds to entryPoint[]
     */
    private int[] entryPointCounter = new int[8];

    /**
     * Holds the number of vehicles that have passed through the intersection
     */
    private int interCounter = 0;


    /**
     * Holds the number type of vehicle
     */
    private String type;

    /**
     * Used to generate pseudo-random numbers
     */
    private Random rand = new Random();

    /**
     * Default class constructor, initializing the linked lists and counters
     */
    public Intersection()
    {
        entryPoint[0] = new LinkedList<Vehicle>();
        entryPoint[1] = new LinkedList<Vehicle>();
        entryPoint[2] = new LinkedList<Vehicle>();
        entryPoint[3] = new LinkedList<Vehicle>();
        entryPoint[4] = new LinkedList<Vehicle>();
        entryPoint[5] = new LinkedList<Vehicle>();
        entryPoint[6] = new LinkedList<Vehicle>();
        entryPoint[7] = new LinkedList<Vehicle>();

        entryPointCounter[0] = 0;
        entryPointCounter[1] = 0;
        entryPointCounter[2] = 0;
        entryPointCounter[3] = 0;
        entryPointCounter[4] = 0;
        entryPointCounter[5] = 0;
        entryPointCounter[6] = 0;
        entryPointCounter[7] = 0;

    }

    /**
     * Intersection class constructor
     * Initializes each entry point with a linked list with a specified
     * number of random vehicles
     *
     * @param horizCycleCars
     * @param horizCycleTime
     * @param vertCycleCars
     * @param vertCycleTime
     * @param type
     */
    public Intersection(int horizCycleTime, int horizCycleCars, int vertCycleTime,
                        int vertCycleCars, String type)
    {
        entryPoint[0] = new LinkedList<Vehicle>();
        entryPoint[1] = new LinkedList<Vehicle>();
        entryPoint[2] = new LinkedList<Vehicle>();
        entryPoint[3] = new LinkedList<Vehicle>();


        entryPointCounter[0] = 0;
        entryPointCounter[1] = 0;
        entryPointCounter[2] = 0;
        entryPointCounter[3] = 0;

        this.type = type;
    }

    /**
     * Clears all linked lists connected to the intersection
     */
    public void clear() {
        entryPoint[0].clear();
        entryPoint[1].clear();
        entryPoint[2].clear();
        entryPoint[3].clear();
        entryPoint[4].clear();
        entryPoint[5].clear();
        entryPoint[6].clear();
        entryPoint[7].clear();
    }

    /**
     * Sets the given linked list to a lane within the intersection
     *
     * @param list A linked list of vehicles lined up at the intersection
     * @param loc The lane that the linked list will be assigned to
     */
    public void setLocation(LinkedList<Vehicle> list, Location loc) {

    }
    /**
     * Sets the vehicle type
     *
     * @param t The vehicle type
     */
    public void setType(String t) {
        type = t;
    }

    /**
     * Returns the type of the vehicle
     *
     * @return type The type of vehicle
     */
    public String getType() {
        return type;
    }
}