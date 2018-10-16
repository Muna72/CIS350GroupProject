import java.awt.Color;
import java.util.LinkedList;


public class UtilityVehicle extends Vehicle {

    public String type() {
        return "UtilityVehicle";
    }

    private boolean isUserCar;

    public UtilityVehicle(){
        this.size = 2;
        this.color = Color.orange;
    }


    public UtilityVehicle(boolean isUserCar) {
        this.size = 2;
        this.isUserCar = isUserCar;
        if (isUserCar == true) {
            this.setColor(Color.red);
        } else {
            this.setColor(Color.orange);
        }

    }

}