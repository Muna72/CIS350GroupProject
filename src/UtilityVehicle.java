import java.awt.Color;
import java.util.LinkedList;


public class UtilityVehicle extends Vehicle{

    public String type() {
        return "UtilityVehicle";
    }

    private boolean isUserCar;

    public UtilityVehicle (){
        super();
        isUserCar = false;
    }

    public UtilityVehicle(int size, int mph, Direction[] path, double createTime,
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
            this.setColor(Color.orange);
        }

    }

    void setUserCar(boolean isUserCar){
        this.isUserCar = isUserCar;
    }

    boolean getUserCar(){
        return isUserCar;
    }