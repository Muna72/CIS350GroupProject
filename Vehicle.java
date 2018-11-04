import java.awt.Color;
import java.util.LinkedList;

/**
 * The Vehicle Class is abstract, used by the types of vehicles that can be used in the simulation.
 *
 * @author Trang Nguyen
 * @version 1.0
 */
public abstract class Vehicle {

    protected int size;

    protected Direction path;

    protected Location location;

    protected Color color;

    protected LinkedList<Vehicle> que;

    protected double timeCreated;

    protected boolean isUserCar;

    protected int numSteps;

    protected int maxSteps;

    protected int stepsToTurn = 12;

    /**
     * Returns the location of a vehicle within the linked list
     *
     * @return stepsToTurn number of vehicles away from the front of the list
     */
    public int getStepsToTurn() {
        return stepsToTurn;
    }

    /**
     * Sets the location of a vehicle within the linked list
     *
     * @param steps location within the linked list
     */
    public void setStepsToTurn(int steps) {
        stepsToTurn = steps;
    }

    /**
     * Returns the maximum length of the list
     *
     * @return maxSteps the maximum length of the linked list
     */
    public int getMaxSteps() {
        return maxSteps;
    }

    /**
     * Sets the maximum length of the linked list
     *
     * @param steps the maximum length of the linked list
     */
    public void setMaxSteps(int steps) {
        maxSteps = steps;
    }

    /**
     * Sets current length of the linked list
     *
     * @param step the length of the linked list
     */
    public void setNumSteps(int step) {
        numSteps = step;
    }

    /**
     * Returns the current length of the linked list
     *
     * @return numSteps the length of the linked list
     */
    public int getNumSteps() {
        return numSteps;
    }


    /**
     * Sets the vehicle to be the users car
     *
     * @return isUserCar whether or not the vehicle belongs to the user
     */
    public void setUserCar(boolean isUserCar){
        this.isUserCar = isUserCar;
    }

    /**
     * Returns whether the vehicle belongs to the user or not
     *
     * @return isUserCar whether the vehicle belongs to the user
     */
    public boolean getUserCar(){
        return isUserCar;
    }

    /**
     * Returns the size of the vehicle in question
     *
     * @return size the size of the vehicle
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the vehicle
     *
     * @param size the new size of the vehicle
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the planned route of the vehicle
     *
     * @return path the direction that the vehicle will turn at the intersection
     */
    public Direction getPath() {
        return path;
    }

    /**
     * Sets the new direction a vehicle will turn at the intersection
     *
     * @param path the new direction the vehicle will turn
     */
    public void setPath(Direction path) {
        this.path = path;
    }

    /**
     * Sets the color of the vehicle
     *
     * @param color the new color of the vehicle
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the color of the vehicle
     *
     * @return color the color of the vehicle
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the linked list that the vehicle is currently located in
     *
     * @return que the linked list of the vehicle
     */
    public LinkedList<Vehicle> getQue() {
        return que;
    }

    /**
     * Adds the vehicle to a new linked list
     *
     * @param q the linked list that the vehicle will be added to
     */
    public void setQue(LinkedList<Vehicle> q) {
        que = q;
    }

    /**
     * Returns the lane in which the vehicle is located
     *
     * @return location the current lane of the vehicle
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the lane in which the vehicle is located
     *
     * @param loc the lane in to which the vehicle will be added
     */
    public void setLocation(Location loc) {
        location = loc;
    }

    /**
     * Sets the time at which the vehicle was created
     *
     * @param time the current time on the clock
     */
    public void setCreateTime(double time) {
     timeCreated = time;
    }

    /**
     * Returns the time at which the vehicle was created
     *
     * @return the time the vehicle was created
     */
    public double getCreateTime() {
        return timeCreated;
    }
}