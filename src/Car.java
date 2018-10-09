import java.awt.Color;

public class Car extends Vehicle {

    private boolean isUserCar;
    double creTime;
    Location loc;

    public Car(){
        super();
        isUserCar = false;
    }

    public Car(int size, int mph, Direction[] path,
               Location loc, double createTime, boolean isUserCar){
        this.size = size;
        this.mph = mph;
        this.path = path;
        this.loc = loc;
        creTime = createTime;
        this.isUserCar = isUserCar;
        this.color = null;
        if(isUserCar == true){
            this.setColor(Color.red);
        }
        else{
            this.setColor(Color.blue);
        }

    }

    void setUserCar(boolean isUserCar){
        this.isUserCar = isUserCar;
    }

    boolean getUserCar(){
        return isUserCar;
    }
}
