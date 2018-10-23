import java.awt.Color;
import java.util.LinkedList;

public abstract class Vehicle {

    protected int size;

    protected Direction path;

    protected Location location;

    protected Color color;

    protected LinkedList<Vehicle> que;

    protected double timeCreated;

    protected boolean isUserCar;

    protected int numSteps;

    public void setNumSteps(int step) {
        numSteps = step;
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void setUserCar(boolean isUserCar){
        this.isUserCar = isUserCar;
    }

    public boolean getUserCar(){
        return isUserCar;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Direction getPath() {
        return path;
    }

    public void setPath(Direction path) {
        this.path = path;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public LinkedList<Vehicle> getQue() {
        return que;
    }

    public void setQue(LinkedList<Vehicle> q) {
        que = q;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    public void setCreateTime(double time) {
     timeCreated = time;
    }

    public double getCreateTime() {
        return timeCreated;
    }
}