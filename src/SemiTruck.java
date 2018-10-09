import java.awt.Color;
import java.util.LinkedList;

public class SemiTruck extends Vehicle {

    public String type() {
        return "SemiTruck";
    }
    private boolean isUserCar;

    public SemiTruck(){
        super();
        isUserCar = false;
    }

    public SemiTruck(int size, int mph, Direction[] path, double createTime,
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
            this.setColor(Color.green);
        }

    }

    void setUserCar(boolean isUserCar){
        this.isUserCar = isUserCar;
    }

    boolean getUserCar(){
        return isUserCar;
    }

    public void validPath (Direction[] path) {
    }

}