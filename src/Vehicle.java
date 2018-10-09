import java.awt.Color;


public abstract class Vehicle {

    protected int size;

    protected int mph;

    protected Direction[] path;

    protected Color color;

    /*******************************************************************
     * The default constructor
     ******************************************************************/
    public Vehicle(){
        size = 0;
        mph = 0;
        path = null;
        color = null;
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
}