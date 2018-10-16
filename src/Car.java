import java.awt.Color;
import java.util.LinkedList;

public class Car extends Vehicle {

    public String type() {
        return "Car";
    }
    private boolean isUserCar;

    public Car(){
        super();
        isUserCar = false;
    }

    public Car(Direction[] path, double createTime,
               LinkedList<Vehicle> currentList, boolean isUserCar){
        this.size = size;
        this.mph = mph;
        this.path = path;
        this.isUserCar = isUserCar;
        this.color = null;
        if(isUserCar == true){
            this.setColor(Color.red);
        }
        else{
            this.setColor(Color.blue);
        }

    }

    void setUserCar(boolean isUserCar){
        this.isUserCar = isUserCar;
    }

    boolean getUserCar(){
        return isUserCar;
    }
}