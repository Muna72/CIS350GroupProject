import org.junit.Test;
import static org.junit.Assert.*;

public class SimulationTest {
    private Simulation s;
    @Test
    public void testConstructor() {
        s = new Simulation(20.0, 10.0, 500);
        assertEquals(s.getAvgVehicleSpeed(), 50,0);
        s = new Simulation(20.0, 10.0, 700);
        assertEquals(s.getAvgVehicleSpeed(), 35.5,0);
        s = new Simulation(20.0, 10.0, 800);
        assertEquals(s.getAvgVehicleSpeed(), 31.25,0);
        s = new Simulation(20.0, 10.0, 1000);
        assertEquals(s.getAvgVehicleSpeed(), 25,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors1() {
        new Simulation(-1,0,500);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors2() {
        new Simulation(0,2,-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors3() {
        new Simulation(0,-2,500);
    }


    @Test
    public void testReset(){
        s = new Simulation(20.0, 10.0, 1000);
        s.reset();
        assertEquals(s.getTotalTime(),0,0);
        assertEquals(s.getSimTimeLeft(),0,0);
        assertEquals(s.getAvgVehicleSpeed(),0,0);
        assertEquals(s.getTotalAvgVehicleTime(),0,0);
        assertEquals(s.getAvgStoppedTime(),0,0);
    }
}
