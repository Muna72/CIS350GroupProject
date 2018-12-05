import org.junit.Test;
import java.awt.Color;
import static org.junit.Assert.*;

/**
 *
 * @author Trang Nguyen
 * @version Fall 2018
 */
public class SimulationTest {
    private Simulation s;
    private FakeRandom r;
    private Vehicle v;

    //UNT 1
    @Test 
    public void testConstructor() {
        s = new Simulation(20.0, 10.0, 500, new FakeRandom(3));
        assertEquals(s.getAvgVehicleSpeed(), 50,50);
        s = new Simulation(20.0, 10.0, 700,new FakeRandom(3));
        assertEquals(s.getAvgVehicleSpeed(), 35.5,0);
        s = new Simulation(20.0, 10.0, 800,new FakeRandom(3));
        assertEquals(s.getAvgVehicleSpeed(), 31.25,0);
        s = new Simulation(20.0, 10.0, 1000 ,new FakeRandom(3));
        assertEquals(s.getAvgVehicleSpeed(), 25,0);
    }

   //UNT 1
   @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors1() {
        new Simulation(-1,0,500, new FakeRandom(3));
    }
    //UNT 1
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors2() {
        new Simulation(0,2,-6, new FakeRandom(3));
    }

    //UNT 1
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors3() {
        new Simulation(0,-2,500, new FakeRandom(3));
    }

    //UNT 1
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors4() {
        new Simulation(-1,0,-500, new FakeRandom(3));
    }

    //UNT 1
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors5() {
        new Simulation(-1,-7,500, new FakeRandom(3));
    }

    //UNT 1
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors6() {
        new Simulation(-1,-10,-500, new FakeRandom(3));
    }

    //UNT 1
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithErrors7() {
        new Simulation(1,-10,-500, new FakeRandom(3));
    }

    //UNT 2
    @Test
    public void testReset1(){
        s = new Simulation(20.0, 10.0, 1000, new FakeRandom(3));
        s.reset();
        assertEquals(s.getSimTimeLeft(),0,0);
        assertEquals(s.getAvgStoppedTime(),0,0);
        assertEquals(s.getAvgStoppedTime(), 0,0);
        assertEquals(s.getNumOfAccidents(),0,0);
        assertEquals(s.getUserThruTime(),0,0);
        assertEquals(s.getNumLightsRun(),0,0);
        assertEquals(s.isLanesZeroAndTwo(), false);
        assertEquals(s.isLanesOneAndThree(),false);
    }

    //UNT 2
    @Test
    public void testReset2(){
        s = new Simulation(20.0, 10.0, 1000, new FakeRandom(3));
        s.reset();
        assertEquals(s.getAvgVehicleSpeed(),0,0); //failed
        assertEquals(s.getTotalAvgVehicleTime(),0,0); //failed
    }

    //UNT 3
    @Test
    public void testIsGoodDriver1() {
        r = new FakeRandom(0);
        s = new Simulation(100, 100000, 300, r);
        v = new Car();

        r.setValue(8);
        s.isGoodDriver(v);
        assertEquals(v.getGoodDriver(), false);
    }

    //UNT 3
    @Test
    public void testIsGoodDriver2(){
        r = new FakeRandom(0);
        s = new Simulation(100, 100000, 300, r);
        v = new Car();

        for (int i = 0 ; i < 14; i++) {
            r.setValue(i);
            s.isGoodDriver(v);
            if (i != 8 ) {
                assertEquals(v.getGoodDriver(), true);
            }
        }
    }

    //UNT 4
    @Test
    public void testGenerateFirstGreen() {
        r = new FakeRandom(0);
        s = new Simulation(100, 100000, 300, r);

        r.setValue(0);
        s.generateFirstGreen();
        assertEquals(s.isLanesOneAndThree(), true);

        r.setValue(1);
        s.generateFirstGreen();
        assertEquals(s.isLanesZeroAndTwo(), true);
    }
    
    //UNT 5
    @Test
    public void testSetPath() {
        r = new FakeRandom(0);
        s = new Simulation(100, 100000, 300, r);
        v = new Car();
        
        //entryPoint[0], all options
        v.setQue(s.intersection1.entryPoint[0]);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.NORTH);
        r.setValue(1);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.EAST);
        r.setValue(2);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.SOUTH);
        r.setValue(3);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.NORTH);
        
        //entryPoint[1], all options
        v.setQue(s.intersection1.entryPoint[1]);
        r.setValue(0);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.WEST);
        r.setValue(1);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.EAST);
        r.setValue(2);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.SOUTH);
        r.setValue(3);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.WEST);
        
        //entryPoint[2], all options
        v.setQue(s.intersection1.entryPoint[2]);
        r.setValue(0);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.NORTH);
        r.setValue(1);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.WEST);
        r.setValue(2);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.SOUTH);
        r.setValue(3);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.WEST);
        
        //entryPoint[3], all options
        v.setQue(s.intersection1.entryPoint[3]);
        r.setValue(0);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.NORTH);
        r.setValue(1);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.EAST);
        r.setValue(2);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.WEST);
        r.setValue(3);
        s.setPath(v);
        assertEquals(v.getPath(), Direction.NORTH);
        
    }
    
    //UNT 6
    @Test
    public void testPlaceVehicle() {
        r = new FakeRandom(0);
        s = new Simulation(100, 100000, 300, r);
        v = new Car();
        
        //test empty list
        assertEquals(s.intersection1.entryPoint[0].size(), 0);
        assertEquals(s.intersection1.entryPoint[1].size(), 0);
        assertEquals(s.intersection1.entryPoint[2].size(), 0);
        assertEquals(s.intersection1.entryPoint[3].size(), 0);
        
        //test adding one to an empty list
        r.setValue(0);
        s.placeVehicle(v);
        r.setValue(1);
        s.placeVehicle(v);
        r.setValue(2);
        s.placeVehicle(v);
        r.setValue(3);
        s.placeVehicle(v);
        
        assertEquals(s.intersection1.entryPoint[0].size(), 1);
        assertEquals(s.intersection1.entryPoint[1].size(), 1);
        assertEquals(s.intersection1.entryPoint[2].size(), 1);
        assertEquals(s.intersection1.entryPoint[3].size(), 1);
        
        //test adding one to an occupied list
        r.setValue(0);
        s.placeVehicle(v);
        r.setValue(1);
        s.placeVehicle(v);
        r.setValue(2);
        s.placeVehicle(v);
        r.setValue(3);
        s.placeVehicle(v);
        
        assertEquals(s.intersection1.entryPoint[0].size(), 2);
        assertEquals(s.intersection1.entryPoint[1].size(), 2);
        assertEquals(s.intersection1.entryPoint[2].size(), 2);
        assertEquals(s.intersection1.entryPoint[3].size(), 2);
        
        //test adding one with the default case
        r.setValue(4);
        s.placeVehicle(v);
        assertEquals(s.intersection1.entryPoint[0].size(), 3);
    }
    
    //UNT 7
    @Test
    public void testAddVehicle() {
        r = new FakeRandom(0);
        s = new Simulation(100, 100000, 300, r);
        
        Vehicle v = s.addVehicle(true);
        assertEquals(v instanceof Car, true);
        assertEquals(v.getColor(), Color.red);
        v = s.addVehicle(false);
        assertEquals(v instanceof Car, true);
        assertEquals(v.getColor(), Color.blue);
        
        r.setValue(1);
        v = s.addVehicle(true);
        assertEquals(v instanceof UtilityVehicle, true);
        assertEquals(v.getColor(), Color.red);
        v = s.addVehicle(false);
        assertEquals(v instanceof UtilityVehicle, true);
        assertEquals(v.getColor(), Color.orange);
        
        r.setValue(2);
        v = s.addVehicle(true);
        assertEquals(v instanceof SemiTruck, true);
        assertEquals(v.getColor(), Color.red);
        v = s.addVehicle(false);
        assertEquals(v instanceof SemiTruck, true);
        assertEquals(v.getColor(), Color.green);
        
        r.setValue(3);
        v = s.addVehicle(true);
        assertEquals(v instanceof Car, true);
        assertEquals(v.getColor(), Color.red);
        v = s.addVehicle(false);
        assertEquals(v instanceof Car, true);
        assertEquals(v.getColor(), Color.blue);
    }
}
