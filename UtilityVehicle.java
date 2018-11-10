import java.awt.Color;

/**
 * UtilityVehicle extends the Vehicle class. The UtilityVehicle has a default size of two and is colored orange,
 * unless it is the drivers UtilityVehicle, then it will be red.
 *
 * @author Johnathon Killeen
 * @version 1.0
 */
public class UtilityVehicle extends Vehicle {

    //Determines if the UtilityVehicle belongs to the user
    private boolean isUserCar;


    /**
     * The default constructor of UtilityVehicle
     */
    public UtilityVehicle(){
        size = 2;
        color = Color.orange;
    }

    /**
     * The constructor of UtilityVehicle
     *
     * @param isUserCar is true if the UtilityVehicle belongs to the user
     */
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