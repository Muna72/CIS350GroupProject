import java.util.LinkedList;
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
}