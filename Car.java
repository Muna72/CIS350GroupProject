import java.awt.Color;

/**
 * Car extends the Vehicle class. The car has a default size of one and is colored blue, unless it is the drivers
 * car, then it will be red
 *
 * @author Johnathon Killeen
 * @version 1.0
 */
public class Car extends Vehicle {

    //Determines if the car belongs to the user
    private boolean isUserCar;

    /**
     * The default constructor of Car
     */
    public Car(){
        size = 1;
        color = Color.blue;
    }
    /**
     * Constructor of Car
     *
     * @param isUserCar is true if the car belongs to the user
     */
    public Car(boolean isUserCar){
        this.size = 1;
        this.isUserCar = isUserCar;
        if(isUserCar == true){
            this.setColor(Color.red);
        }
        else{
            this.setColor(Color.blue);
        }

    }

}