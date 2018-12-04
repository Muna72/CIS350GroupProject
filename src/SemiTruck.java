import java.awt.Color;
/**
 * Semitruck extends the Vehicle class. The semi has a default size of three and is colored green,
 * unless it is the drivers truck, then it will be red.
 *
 * @author Trang Nguyen
 * @version 1.0
 */
public class SemiTruck extends Vehicle {

    /**
     * The default constructor of Semitruck.
     */
    public SemiTruck() {
        size = 3;
        color = Color.green;
    }

    /**
     * Constructor for Semitruck.
     *
     * @param isUserCar is true if the truck belongs to the user
     */
    public SemiTruck(boolean isUserCar) {
        this.size = 3;
        if (isUserCar == true) {
            this.setColor(Color.red);
        }
        else {
            this.setColor(Color.green);
        }

    }

}