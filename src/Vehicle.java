import java.io.Serializable;
import java.awt.Color;
/*******************************************************************
 * Vehicle is a parent class for all types of vehicles that can be
 * on the road
 ******************************************************************/
public class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int size;

    protected int mph;

    protected int[] path;

    protected int[] color;

    /*******************************************************************
     * The default constructor
     ******************************************************************/
    public Vehicle(){
        size = 0;
        mph = 0;
        path = null;
        color = null;
    }

    public Vehicle(int size, int mph, int[] path, Color color){
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


    public int[] getPath() {
        return path;
    }

    public void setPath(int[] path) {
        this.path = path;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public int[] getColor() {
        return color;
    }

}
