import java.awt.Color;
import java.util.LinkedList;

public class Car extends Vehicle {

    public String type() {
        return "Car";
    }
    private boolean isUserCar;

    public Car(){
        this.size = 1;
        this.color = Color.blue;
    }

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