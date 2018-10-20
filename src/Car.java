import java.awt.Color;

public class Car extends Vehicle {

    private boolean isUserCar;

    public Car(){
        size = 1;
        color = Color.blue;
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