import java.awt.Color;
import java.util.LinkedList;

public class SemiTruck extends Vehicle {

    private boolean isUserCar;

    public SemiTruck(){
        this.size = 3;
        color = Color.green;
    }


    public SemiTruck(boolean isUserCar){
        this.size = 3;
        this.isUserCar = isUserCar;
        if(isUserCar == true){
            this.setColor(Color.red);
        }
        else{
            this.setColor(Color.green);
        }

    }

}