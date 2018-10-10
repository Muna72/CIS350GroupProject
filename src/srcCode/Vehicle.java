package srcCode;

import java.awt.Color;
import java.util.LinkedList;

public abstract class Vehicle {

    protected int size;

    protected int mph;

    protected Direction[] path;
    protected Location location;

    protected Color color;

    protected LinkedList<Vehicle> que;
    protected double timeCreated;

    /*******************************************************************
     * The default constructor
     ******************************************************************/
    public Vehicle(){
        size = 0;
        mph = 0;
        path = null;
        color = null;
        que = null;
        location = null;
        timeCreated = 0;
    }

    public Vehicle(int size, int mph, Direction[] path, Color color){
        this.size = size;
        this.mph = mph;
        this.path = path;
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMph() {
        return mph;
    }

    public void setMph(int mph) {
        this.mph = mph;
    }

    public Direction[] getPath() {
        return path;
    }

    public void setPath(Direction[] path) {
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