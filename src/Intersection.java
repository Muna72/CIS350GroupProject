import java.util.LinkedList;
import java.util.Random;

public class Intersection
{
    //Holds list of vehicles going in a specified direction
    //entryPoint[0] = NORTH, entryPoint[1] = EAST,
    //entryPoint[2] = SOUTH, entryPoint[3] = WEST
    public LinkedList<Vehicle>[] entryPoint = new LinkedList[8];

    //Holds the number of vehicles that have passed through
    //a lane in a specified direction; corresponds to entryPoint[]
    private int[] entryPointCounter = new int[8];

    //Holds the number of cars that have passed through the intersection
    private int interCounter = 0;

    private String type;

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

    /*
     * Initialize each entry point with a linked list with a specified
     * number of random vehicles
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


    public void setLocation(LinkedList<Vehicle> list, Location loc) {

    }

    public void setType(String t) {
        type = t;
    }
    public String getType() {
        return type;
    }
}