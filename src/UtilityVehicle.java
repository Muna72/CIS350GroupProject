import java.awt.Color;
import java.util.LinkedList;


public class UtilityVehicle extends Vehicle {

    private boolean isUserCar;

    public UtilityVehicle(){
        size = 2;
        color = Color.orange;
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