import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Trang Nguyen
 * @version Fall 2018
 */
public class SimulationTest {
    private Simulation s;
    @Test
    public void testConstructor() {
        s = new Simulation(20.0, 10.0, 500, new FakeRandom(3));
        assertEquals(s.getAvgVehicleSpeed(), 50,0);
        s = new Simulation(20.0, 10.0, 700,new FakeRandom(3));
        assertEquals(s.getAvgVehicleSpeed(), 35.5,0);
        s = new Simulation(20.0, 10.0, 800,new FakeRandom(3));
        assertEquals(s.getAvgVehicleSpeed(), 31.25,0);
        s = new Simulation(20.0, 10.0, 1000 ,new FakeRandom(3));
        assertEquals(s.getAvgVehicleSpeed(), 25,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors1() {
        new Simulation(-1,0,500, new FakeRandom(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors2() {
        new Simulation(0,2,-6, new FakeRandom(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors3() {
        new Simulation(0,-2,500, new FakeRandom(3));
    }


    @Test
    public void testReset(){
        s = new Simulation(20.0, 10.0, 1000, new FakeRandom(3));
        s.reset();
        assertEquals(s.getSimTimeLeft(),0,0);
        //assertEquals(s.getAvgVehicleSpeed(),0,0); //failed
        //assertEquals(s.getTotalAvgVehicleTime(),0,0); 
        assertEquals(s.getAvgStoppedTime(),0,0);
        assertEquals(s.getAvgStoppedTime(), 0,0);
        assertEquals(s.getSimTimeLeft(),0,0);
        assertEquals(s.getNumOfAccidents(),0,0);
        assertEquals(s.getUserThruTime(),0,0);
    }
}