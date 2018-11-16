import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;
/**
 *
 * @author Muna Gigowski
 * @version 1.0 (September 2018)
 */
public class Simulation extends JPanel {

    private Vehicle[][] route;

    public ArrayList<Vehicle> allVehicles;
    public ArrayList<Double> allAvgTimes;
    public ArrayList<Double> allAvgTimeStopped;
    public ArrayList<Integer> allQueLengths;
    public Intersection intersection1;

    private final static int ROWS = 80, COLUMNS = 120, SIZE = 10;
    private final static int MAX_VEHICLES = 80;

    //Instance variable declarations
    private double secsTillNextVehicle;
    private double currTime;
    private double vTime;
    private double lTime;
    private double avgStoppedSec;
    private double totalTime;
    private double simTimeLeft;
    private double timeVehicleAdded;
    private double userThruTime;
    private double avgVehicleSpeed;
    private int numOfVehicles;
    private int numLightsRun;
    private int numOfAccidents;
    private int finished;
    private boolean firstPer;
    private boolean enableYellow;
    private boolean isLanesOneAndThree;
    private boolean isLanesZeroAndTwo;
    private boolean isYellowOneAndThree;
    private boolean isYellowLight;
    private boolean goodToCross;
    private double greenLightTimer;
    private double yellowLightTimer;
    private RandomInterface rand;
    private boolean started;
    private double timeForUserCar;


    /**
     *  Class Constructor initializes instance variables.
     * @param secNext seconds until the next vehicle will be generated
     * @param totTime total simulation run time
     * @param laneTime time when each lane will "move forward"
     */
    public Simulation(double secNext, double totTime, double laneTime, RandomInterface randObject) {

        route = new Vehicle[ROWS][COLUMNS];
        allVehicles = new ArrayList<Vehicle>();
        allAvgTimes = new ArrayList<Double>();
        allAvgTimeStopped = new ArrayList<Double>();
        allQueLengths = new ArrayList<Integer>();
        intersection1 = new Intersection();

        secsTillNextVehicle = secNext;
        totalTime = totTime;
        lTime = laneTime;
        numOfVehicles = 0;
        numOfAccidents = 0;
        avgStoppedSec = 0;
        avgVehicleSpeed = 0;
        timeVehicleAdded = 0;
        firstPer = false;
        enableYellow = false;
        isLanesOneAndThree = false;
        isLanesZeroAndTwo = false;
        isYellowOneAndThree = false;
        isYellowLight = false;
        started = false;
        greenLightTimer = 0;
        yellowLightTimer = 0;
        setPreferredSize(new Dimension(COLUMNS * SIZE, ROWS * SIZE));
        
        rand = randObject;
    }

    /**
     * Method to set average seconds until next Vehicle is generated.
     * @param secNext
     */
    public void setSecsTillNextVehicle(double secNext) {
        secsTillNextVehicle = secNext;
    }

    /**
     * Method to set total simulation time.
     * @param totTime
     */
    public void setTotalTime(double totTime) {
        totalTime = totTime;
    }

    /**
     * Method to set the time between when vehicles are generated.
     * @param pt
     */
    public void setVTime(double pt) {
        vTime = pt;
    }

    /**
     * Method to get the number of accidents.
     * @return
     */
    public int getNumOfAccidents() {
        return numOfAccidents;
    }

    /**
     * Method to calculate the average vehicle speed, based on the value of lTime.
     * @return
     */
    public double getAvgVehicleSpeed() {
        if (lTime == 500) {
            avgVehicleSpeed = 50;
        } else if (lTime == 700) {
            avgVehicleSpeed = 35.5;
        } else if (lTime == 800) {
            avgVehicleSpeed = 31.25;
        } else if (lTime == 1000) {
            avgVehicleSpeed = 25;
        }
        return avgVehicleSpeed;
    }
    /**
     * Method to place Vehicle in a lane que.
     * @param v vehicle to be placed
     */
    private void placeVehicle(Vehicle v) {

        int gen = rand.nextInt(4) + 1;

        switch (gen) {
            case 1:
                intersection1.entryPoint[0].add(v);
                v.setQue(intersection1.entryPoint[0]);
                setPath(v);
                break;
            case 2:
                intersection1.entryPoint[1].add(v);
                v.setQue(intersection1.entryPoint[1]);
                setPath(v);
                break;
            case 3:
                intersection1.entryPoint[2].add(v);
                v.setQue(intersection1.entryPoint[2]);
                setPath(v);
                break;
            case 4:
                intersection1.entryPoint[3].add(v);
                v.setQue(intersection1.entryPoint[3]);
                setPath(v);
                break;
            default:
                intersection1.entryPoint[0].add(v);
                v.setQue(intersection1.entryPoint[0]);
                setPath(v);
                break;
        }

        if (firstPer == true) {
            v.setCreateTime(getSimTimeLeft());
            firstPer = false;
        }
        setStartingPosition(v);
        repaint();
    }

    /**
     * Method to randomly set the direction of the path the vehicle will take.
     * @param v for input vehicle
     */
    public void setPath(Vehicle v) {

        LinkedList<Vehicle> laneHolder = v.getQue();
        int dirGen = rand.nextInt(3 - 1 + 1) + 1;

        if (laneHolder == intersection1.entryPoint[0]) {
            switch (dirGen) {
                case 1:
                    v.setPath(Direction.NORTH);
                    break;
                case 2:
                    v.setPath(Direction.EAST);
                    break;
                case 3:
                    v.setPath(Direction.SOUTH);
                    break;
                default:
                    v.setPath(Direction.NORTH);
                    break;
            }
        }
        if (laneHolder == intersection1.entryPoint[1]) {
            switch (dirGen) {
                case 1:
                    v.setPath(Direction.WEST);
                    break;
                case 2:
                    v.setPath(Direction.EAST);
                    break;
                case 3:
                    v.setPath(Direction.SOUTH);
                    break;
                default:
                    v.setPath(Direction.WEST);
                    break;
            }
        }
        if (laneHolder == intersection1.entryPoint[2]) {
            switch (dirGen) {
                case 1:
                    v.setPath(Direction.NORTH);
                    break;
                case 2:
                    v.setPath(Direction.WEST);
                    break;
                case 3:
                    v.setPath(Direction.SOUTH);
                    break;
                default:
                    v.setPath(Direction.WEST);
                    break;
            }
        }
        if (laneHolder == intersection1.entryPoint[3]) {
            switch (dirGen) {
                case 1:
                    v.setPath(Direction.NORTH);
                    break;
                case 2:
                    v.setPath(Direction.EAST);
                    break;
                case 3:
                    v.setPath(Direction.WEST);
                    break;
                default:
                    v.setPath(Direction.NORTH);
                    break;
            }
        }
        calculateMaxAndTurnSteps(v);
    }

    /**
     * Method to calculate the maximum turn steps and maximum total steps for a vehicle, depending on the path it's taking.
     * @param v vehicle whose stats are being determined
     */
    public void calculateMaxAndTurnSteps(Vehicle v) {
        LinkedList<Vehicle> laneHolder = v.getQue();

        if (laneHolder == intersection1.entryPoint[0]) {
            if (v.getPath() == Direction.NORTH) {
                v.setMaxSteps(31);
                v.setStepsToTurn(16);
            } else if (v.getPath() == Direction.EAST) {
                v.setMaxSteps(28);
            } else {
                v.setMaxSteps(23);
            }
        }
        if (laneHolder == intersection1.entryPoint[1]) {
            if (v.getPath() == Direction.EAST) {
                v.setMaxSteps(32);
                v.setStepsToTurn(13);
            } else if (v.getPath() == Direction.SOUTH){
                v.setMaxSteps(27);
            } else {
                v.setMaxSteps(23);
            }
        }
        if (laneHolder == intersection1.entryPoint[2]) {
            if (v.getPath() == Direction.SOUTH) {
                v.setMaxSteps(30);
                v.setStepsToTurn(15);
            } else if (v.getPath() == Direction.WEST) {
                v.setMaxSteps(28);
            } else {
                v.setMaxSteps(23);
            }
        }
        if (laneHolder == intersection1.entryPoint[3]) {
            if (v.getPath() == Direction.WEST) {
                v.setMaxSteps(32);
                v.setStepsToTurn(14);
            } else if (v.getPath() == Direction.NORTH) {
                v.setMaxSteps(27);
            } else {
                v.setMaxSteps(23);
            }
        }
    }

    /**
     * Method to reset the simulation.
     *
     */
    public void reset() {

        secsTillNextVehicle = 0;
        numOfAccidents = 0;
        numOfVehicles = 0;
        numLightsRun = 0;
        totalTime = 0;
        avgStoppedSec = 0;
        avgVehicleSpeed = 0;
        timeVehicleAdded = 0;
        finished = 0;
        userThruTime = 0;
        currTime = 0;
        allVehicles.clear();
        allAvgTimes.clear();
        allAvgTimeStopped.clear();
        intersection1.clear();
        enableYellow = false;
        isYellowLight = false;
        isYellowOneAndThree = false;
        isLanesOneAndThree = false;
        isLanesZeroAndTwo = false;
        greenLightTimer = 0;
        yellowLightTimer = 0;
        started = false;

        for (int i = 0; i < ROWS; ++i) {
            for (int y = 0; y < COLUMNS; ++y) {
                route[i][y] = null;
            }
        }
    }

    /**
     * Method to actively set simulation time left.
     * @param s time being passed in
     */
    public void setSimTimeLeft(double s) {
        simTimeLeft = s;
    }

    /**
     * Method to get the simulation time left.
     * @return the time left
     */
    public double getSimTimeLeft() {
        return simTimeLeft;
    }

    /**
     * Method to get the number of lights run.
     * @return numLightsRun
     */
    public int getNumLightsRun() {
        return numLightsRun;
    }

    /**
     * Method to remove a vehicle from the simulation
     * @param v vehicle to be removed
     * @param timeNow current time in the simulation
     */
    public void removeVehicle(Vehicle v, double timeNow) {

        LinkedList<Vehicle> holder = v.getQue();

        route[v.getLocation().getRow()][v.getLocation().getCol()] = null;
        allAvgTimes.add(v.getCreateTime() - timeNow);
        if (v.getColor() == Color.RED) {
            userThruTime = (v.getCreateTime() - timeNow) / 1000;
        }
        holder.remove(v);
        allVehicles.remove(v);
        ++finished;
        repaint();
    }

    /**
     * Method to add new vehicle to the simulation
     * @param isUserCar to determine if the car being added is the user's car or not
     */
    public void addVehicle(boolean isUserCar) {

        Vehicle v = null;
        int typeGen = rand.nextInt(4 - 1 + 1) + 1;

        if (numOfVehicles <= MAX_VEHICLES) {
            if (!isUserCar) {
                    if (typeGen == 1) {
                        v = new Car();
                    }
                    if (typeGen == 2) {
                        v = new UtilityVehicle();
                    }
                    if (typeGen == 3) {
                        v = new SemiTruck();
                    }
                    if (typeGen == 4) {
                        v = new Car();
                    }
            } else {
                if (typeGen == 1) {
                    v = new Car(true);
                }
                if (typeGen == 2) {
                    v = new UtilityVehicle(true);
                }
                if (typeGen == 3) {
                    v = new SemiTruck(true);
                }
                if (typeGen == 4) {
                    v = new Car(true);
                }
            }
        }
        if (getSimTimeLeft() > 0) {
            v.setCreateTime(getSimTimeLeft());
        }
        if(v != null) {
            isGoodDriver(v);
            placeVehicle(v);
            allVehicles.add(v);
            ++numOfVehicles;
        }
    }

    /**
     * Method to switch vehicle to whatever lane associates with the direction it is trying to go
     * @param v vehicle that is switching lanes
     */
    public void switchLanes(Vehicle v) {

        LinkedList<Vehicle> holder = v.getQue();
        Direction dir = v.getPath();

        switch (dir) {
            case NORTH:
                holder.remove(v);
                intersection1.entryPoint[5].add(v);
                v.setQue(intersection1.entryPoint[5]);
                break;
            case EAST:
                holder.remove(v);
                intersection1.entryPoint[6].add(v);
                v.setQue(intersection1.entryPoint[6]);
                break;
            case SOUTH:
                holder.remove(v);
                intersection1.entryPoint[7].add(v);
                v.setQue(intersection1.entryPoint[7]);
                break;
            case WEST:
                holder.remove(v);
                intersection1.entryPoint[4].add(v);
                v.setQue(intersection1.entryPoint[4]);
                break;
        }
    }

    /**
     * Method to move entire lane of cars forward one step
     * @param lane lane to be moved forward
     * @param currTime current simulation time
     */
    public void moveForward(LinkedList<Vehicle> lane, double currTime) {

        Vehicle last = null;

        if (lane == intersection1.entryPoint[0] || lane == intersection1.entryPoint[6]) {
            for (int v = 0; v < lane.size(); ++v ) {
                Vehicle current = lane.get(v);
                goodToCross = false;
                if (current.getNumSteps() == 10) {
                    if (isLanesZeroAndTwo) {
                        crossIntersection(current);
                    } else {
                        if (current.getGoodDriver() == true) {
                            //Continue so steps do not get incremented for this vehicle
                            current.setTimeStopped(currTime);
                            last = current;
                            continue;
                        } else {
                            crossIntersection(current);
                            ++numLightsRun;
                        }
                    }
                }
                if (current.getNumSteps() != 10 || goodToCross) {
                    if (current.getNumSteps() < 10 && last != null) {
                        //continue if you are about to take as many steps as the guy in front of you
                        if (current.getNumSteps() == (last.getNumSteps() - 1)) {
                            current.setTimeStopped(currTime);
                            last = current;
                            continue;
                        }
                    }
                    //Now that car is moving again, calculate how long they had been stopped
                    if (current.getTimeStopped() > 0) {
                        allAvgTimeStopped.add(current.getTimeStopped() - currTime);
                    }
                    Location temp;
                    if (current.getLocation().getCol() <= 100) {
                        temp = new Location(current.getLocation().getRow(), (current.getLocation().getCol() + 3));
                    } else {
                        temp = new Location(current.getLocation().getRow(), 100);
                    }
                    route[current.getLocation().getRow()][current.getLocation().getCol()] = null;
                    //set location to current location plus [] columns to the right
                    current.setLocation(temp);
                    route[current.getLocation().getRow()][current.getLocation().getCol()] = current;
                    //switch lanes
                    if (current.getNumSteps() == current.getStepsToTurn()) {
                        switchLanes(current);
                        v = v - 1;
                    }
                    //remove from simulation
                    if (current.getNumSteps() == current.getMaxSteps()) {
                        removeVehicle(current, currTime);
                        v = v - 1;
                    }
                }
                current.setNumSteps(current.getNumSteps() + 1);
                last = current;
            }
        }
        if (lane == intersection1.entryPoint[1] || lane == intersection1.entryPoint[7]) {
            for (int v = 0; v < lane.size(); ++v ) {

                Vehicle current = lane.get(v);
                goodToCross = false;
                if (current.getNumSteps() == 10) {
                    if (isLanesOneAndThree) {
                        crossIntersection(current);
                    } else {
                        if (current.getGoodDriver() == true) {
                            //Continue so steps do not get incremented for this vehicle
                            current.setTimeStopped(currTime);
                            last = current;
                            continue;
                        } else {
                            crossIntersection(current);
                            ++numLightsRun;
                        }
                    }
                }
                if (current.getNumSteps() != 10 || goodToCross) {
                    if (current.getNumSteps() < 10 && last != null) {
                        //continue if you are about to take as many steps as the guy in front of you
                        if (current.getNumSteps() == (last.getNumSteps() - 1)) { //Never entered
                            last = current;
                            continue;
                        }
                    }
                    Location temp;
                    if (current.getLocation().getRow() <= 76) {
                        temp = new Location((current.getLocation().getRow() + 3), (current.getLocation().getCol()));
                    } else {
                        temp = new Location(79, (current.getLocation().getCol()));
                    }
                    route[current.getLocation().getRow()][current.getLocation().getCol()] = null;
                    //set location to current location plus [] rows down
                    current.setLocation(temp);
                    route[current.getLocation().getRow()][current.getLocation().getCol()] = current;
                    //switch lanes
                    if (current.getNumSteps() == current.getStepsToTurn()) {
                        switchLanes(current);
                        v = v - 1;
                    }
                    //remove from simulation
                    if (current.getNumSteps() == current.getMaxSteps()) {
                        removeVehicle(current, currTime);
                        v = v - 1;
                    }
                }
                current.setNumSteps(current.getNumSteps() + 1);
                last = current;
            }
        }
        if (lane == intersection1.entryPoint[4] || lane == intersection1.entryPoint[2]) {
            for (int v = 0; v < lane.size(); ++v ) {

                Vehicle current = lane.get(v);
                goodToCross = false;
                //move to next linkedList
                if (current.getNumSteps() == 10) {
                    if (isLanesZeroAndTwo) {
                        crossIntersection(current);
                    } else {
                        if (current.getGoodDriver() == true) {
                            //Continue so steps do not get incremented for this vehicle
                            current.setTimeStopped(currTime);
                            last = current;
                            continue;
                        } else {
                            crossIntersection(current);
                            ++numLightsRun;
                        }
                    }
                }
                if (current.getNumSteps() != 10 || goodToCross) {
                    if (current.getNumSteps() < 10 && last != null) {
                        //continue if you are about to take as many steps as the guy in front of you
                        if (current.getNumSteps() == (last.getNumSteps() - 1)) { //Never entered
                            last = current;
                            continue;
                        }
                    }
                    Location temp;
                    if (current.getLocation().getCol() >= 3) {
                        temp = new Location(current.getLocation().getRow(), (current.getLocation().getCol() - 3));
                    } else {
                        temp = new Location(current.getLocation().getRow(), 0);
                    }
                    route[current.getLocation().getRow()][current.getLocation().getCol()] = null;
                    //set location to current location minus [] columns (to the left)
                    current.setLocation(temp);
                    route[current.getLocation().getRow()][current.getLocation().getCol()] = current;
                    //switch lanes
                    if (current.getNumSteps() == current.getStepsToTurn()) {
                        switchLanes(current);
                        v = v - 1;
                    }
                    //remove from simulation
                    if (current.getNumSteps() == current.getMaxSteps()) {
                        removeVehicle(current, currTime);
                        v = v - 1;
                    }
                }
                current.setNumSteps(current.getNumSteps() + 1);
                last = current;
            }
        }
        if (lane == intersection1.entryPoint[3] || lane == intersection1.entryPoint[5]) {
            for (int v = 0; v < lane.size(); ++v ) {

                Vehicle current = lane.get(v);
                goodToCross = false;
                //move to next linkedList
                if (current.getNumSteps() == 10) {
                    if (isLanesOneAndThree) {
                        crossIntersection(current);
                    } else {
                        if (current.getGoodDriver() == true) {
                            //Continue so steps do not get incremented for this vehicle
                            current.setTimeStopped(currTime);
                            last = current;
                            continue;
                        } else {
                            crossIntersection(current);
                            ++numLightsRun;
                        }
                    }
                }
                if (current.getNumSteps() != 10 || goodToCross) {
                    if (current.getNumSteps() < 10 && last != null) {
                        //continue if you are about to take as many steps as the guy in front of you
                        if (current.getNumSteps() == (last.getNumSteps() - 1)) { //Never entered
                            last = current;
                            continue;
                        }
                    }
                    Location temp;
                    if (current.getLocation().getRow() >= 3) {
                        temp = new Location((current.getLocation().getRow() - 3), (current.getLocation().getCol()));
                    } else {
                        temp = new Location(0, (current.getLocation().getCol()));
                    }
                    route[current.getLocation().getRow()][current.getLocation().getCol()] = null;
                    //set location to current location minus [] rows up
                    current.setLocation(temp);
                    route[current.getLocation().getRow()][current.getLocation().getCol()] = current;
                    //switch lanes
                    if (current.getNumSteps() == current.getStepsToTurn()) {
                        switchLanes(current);
                        v = v - 1;
                    }
                    //remove from simulation
                    if (current.getNumSteps() == current.getMaxSteps()) {
                        removeVehicle(current,currTime);
                        v = v - 1;
                    }
                }
                current.setNumSteps(current.getNumSteps() + 1);
                last = current;
            }
        }
        checkForAccident();
    }

    /**
     * Method to determine whether or not a vehicle is safe to cross the intersection, then allow or halt it accordingly
     * @param v vehicle to be moved/halted
     */
    public void crossIntersection(Vehicle v) {

        LinkedList<Vehicle> lane = v.getQue();
        boolean collisionChance = false;
        boolean impasse = false;

        if (lane == intersection1.entryPoint[0]) {
            if (v.getPath() == Direction.NORTH) {
                if (intersection1.entryPoint[2].size() > 0) {
                    for (int t = 0; t < intersection1.entryPoint[2].size(); ++t) {
                        Vehicle curr = intersection1.entryPoint[2].get(t);
                        if (curr.getNumSteps() == 3) {
                            if (curr.getPath() == Direction.WEST) {
                                collisionChance = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (collisionChance){
                    Vehicle oncoming = intersection1.entryPoint[2].get(0);
                    if (oncoming.getPath() == Direction.SOUTH) {
                        for (int h = 0; h < intersection1.entryPoint[0].size(); ++h) {
                            Vehicle c = intersection1.entryPoint[0].get(h);
                            if (c.getNumSteps() == 3) {
                                impasse = true;
                                break;
                            }
                        }
                    }
                    if (impasse) {
                        goodToCross = true; //If there is an impass, lane 0 gets the right-of-way
                    } else {
                        v.setNumSteps(9);
                    }

            } else {
                goodToCross = true;
            }
        }
        if (lane == intersection1.entryPoint[1]) {
            if (v.getPath() == Direction.EAST) {
                if (intersection1.entryPoint[3].size() > 0) {
                    for (int t = 0; t < intersection1.entryPoint[3].size(); ++t) {
                        Vehicle curr = intersection1.entryPoint[3].get(t);
                        if (curr.getNumSteps() == 5 || curr.getNumSteps() == 6) {
                            if (curr.getPath() == Direction.NORTH) {
                                collisionChance = true;
                            }
                        }
                    }
                }
            }
            if (collisionChance){
                Vehicle oncoming = intersection1.entryPoint[3].get(0);
                if (oncoming.getPath() == Direction.WEST) {
                    for (int h = 0; h < intersection1.entryPoint[1].size(); ++h) {
                        Vehicle c = intersection1.entryPoint[1].get(h);
                        if (c.getNumSteps() == 6 || c.getNumSteps() == 5) {
                            impasse = true;
                            break;
                        }
                    }
                }
                if (impasse) {
                    goodToCross = true; //If there is an impass, lane 1 gets the right-of-way
                } else {
                    v.setNumSteps(9);
                }
            } else {
                goodToCross = true;
            }
        }
        if (lane == intersection1.entryPoint[2]) {
            if (v.getPath() == Direction.SOUTH) {
                if (intersection1.entryPoint[0].size() > 0) {
                    for (int t = 0; t < intersection1.entryPoint[0].size(); ++t) {
                        Vehicle curr = intersection1.entryPoint[0].get(t);
                        if (curr.getNumSteps() == 6 || curr.getNumSteps() == 5) {
                            if (curr.getPath() == Direction.EAST) {
                                collisionChance = true;
                            }
                        }
                    }
                }
            }
            if (collisionChance){
                v.setNumSteps(9);
            } else {
                goodToCross = true;
            }
        }
        if (lane == intersection1.entryPoint[3]) {
            if (v.getPath() == Direction.WEST) {
                if (intersection1.entryPoint[1].size() > 0) {
                    for (int t = 0; t < intersection1.entryPoint[1].size(); ++t) {
                        Vehicle curr = intersection1.entryPoint[1].get(t);
                        if (curr.getNumSteps() == 5 || curr.getNumSteps() == 6) {
                            if (curr.getPath() == Direction.SOUTH) {
                                collisionChance = true;
                            }
                        }
                    }
                }
            }
            if (collisionChance){
                v.setNumSteps(9);
            } else {
                goodToCross = true;
            }
        }
    }

    /**
     * Method to check and see if any two vehicle have moved to the same place, causing them to collide
     */
    public void checkForAccident() {
        for (int i = 0; i < allVehicles.size(); ++i) {
            Vehicle v1 = allVehicles.get(i);
            int v1Row = v1.getLocation().getRow();
            int v1Col = v1.getLocation().getCol();
            for (int y = allVehicles.size() - 1; y > 0; --y) {
                Vehicle v2 = allVehicles.get(y);
                int v2Row = v2.getLocation().getRow();
                int v2Col = v2.getLocation().getCol();
                if (v1Row == v2Row && v1Col == v2Col && v1 != v2) {
                    removeVehicle(v1, currTime);
                    removeVehicle(v2, currTime);
                    ++numOfAccidents;
                    break;
                }
            }
        }
        repaint();
    }


    public void isGoodDriver(Vehicle v) {
        int gen = rand.nextInt(14) + 1;

        if (gen == 9) {
            v.setGoodDriver(false);
        } else {
            v.setGoodDriver(true);
        }

    }

    /**
     * Method to initially place vehicle visually in the GUI in a location depending on their queue
     * @param v vehicle to be visually represented
     */
    public void setStartingPosition(Vehicle v) {

        int r = 0;
        int y = 0;

        if (v.getQue() == intersection1.entryPoint[0]) {
            r = 45;
            y = 9;
        }

        if (v.getQue() == intersection1.entryPoint[1]){
            r = 0;
            y = 45;
        }

        if (v.getQue() == intersection1.entryPoint[2]) {
            r = 35;
            y = 96;
        }

        if (v.getQue() == intersection1.entryPoint[3]) {
            r = 79;
            y = 60;
        }

        if (v.getQue() == intersection1.entryPoint[4]) {
            r = 35;
            y = 11;
        }

        if (v.getQue() == intersection1.entryPoint[5]) {
            r = 2;
            y = 60;
        }

        if(v.getQue() == intersection1.entryPoint[6]) {
            r = 45;
            y = 94;
        }

        if(v.getQue() == intersection1.entryPoint[7]) {
            r = 77;
            y = 45;
        }

        Location loc = new Location(r, y);
        v.setLocation(loc);
        route[v.getLocation().getRow()][v.getLocation().getCol()] = v;
        repaint();
    }

    /**
     * Method to randomly choose which lanes will be first to have a green light in the simulation
     */
    public void generateFirstGreen() {

        int gen = rand.nextInt(2 - 1 + 1) + 1;

        switch(gen) {
            case 1:
                isLanesOneAndThree = true;
                break;
            case 2:
                isLanesZeroAndTwo = true;
                break;
            default:
                isLanesOneAndThree = true;
                break;
        }
    }

    /**
     * Method that progresses the simulation
     */
    public void takeAction() {

        currTime = getSimTimeLeft();
        LinkedList<Vehicle> laneHolder = null;
        started = true;

        //generate first Vehicle shortly into simulation
        if (Double.compare(currTime, totalTime - 500) == 0) {
            firstPer = true;
            addVehicle(false);
            timeVehicleAdded = currTime;
        }

        //generate another Vehicle at every vTime seconds (or asap after if delay causes simulation to pass vTime)
        if ((timeVehicleAdded - currTime) >= vTime) {
            addVehicle(false);
            timeVehicleAdded = currTime;
        }

        //Generate user car at specified time
        if (currTime == totalTime - timeForUserCar) {
            addVehicle(true);
        }

        //Generate first green/red light soon after simulation starts
        if (currTime == totalTime - 100) {
            generateFirstGreen();
            greenLightTimer = currTime;
            enableYellow = true;
        }

        //Every alternate green/red lights for ten second increments
        if (!isYellowLight && (greenLightTimer - currTime) >= 10000) {
            //Turn whichever lane had green light previously to yellow, stopping the flow of traffic from that lane
            if (isLanesOneAndThree) {
                isLanesOneAndThree = false;
                isYellowOneAndThree = true;
            } else {
                isLanesZeroAndTwo = false;
                isYellowOneAndThree = false;
            }
            isYellowLight = true;
            yellowLightTimer = currTime;
        }
        //After tens seconds of green light, run yellow light for five seconds
        if (isYellowLight && (yellowLightTimer - currTime) >= 5000) {
            if (isYellowOneAndThree) {
                isLanesZeroAndTwo = true;
            } else {
                isLanesOneAndThree = true;
            }
            isYellowLight = false;
            greenLightTimer = currTime;
        }

        //loop through all lanes and move them if they have the right-of-way
        if ((totalTime - currTime) % lTime == 0) {
            for (int u = 7; u >= 0; --u) {

                laneHolder = intersection1.entryPoint[u];
                moveForward(laneHolder, currTime);
            }
        }
        repaint();
    }

    /**
     * Get the number of vehicles that finished the simulation
     * @return finished
     */
    public int getFinished() {
        return finished;
    }

    /**
     * Method to get the amount of time it took the user's car to finish the simulation
     * @return
     */
    public double getUserThruTime() {
        return userThruTime;
    }

    /**
     * Method to get the time that the user's car will enter the simulation
     * @param time
     */
    public void setTimeForUserCar(double time) {
        timeForUserCar = time;
    }

    /**
     * Method to set the time increment by which lanes to be updated
     * @param time
     */
    public void setLTime(double time) {
        lTime = time;
    }

    /**
     * Get the total average time for a Vehicle from start to finish //TODO this is not done yet
     * @return
     */
    public double getTotalAvgVehicleTime() {

        double sum = 0;
        double totalAvgVehicleTime;

        for (int i = 0; i < allAvgTimes.size(); ++i) {
            sum = sum + allAvgTimes.get(i);
        }
        totalAvgVehicleTime = (sum / numOfVehicles) / 1000;
        return totalAvgVehicleTime;
    }

    /**
     * Method to get average time stopped
     * @return
     */
    public double getAvgStoppedTime() {

        double sum = 0;

        if (allAvgTimeStopped.size() > 0) {
            for (int i = 0; i < allAvgTimeStopped.size(); ++i) {
                sum = sum + allAvgTimeStopped.get(i);
            }
            avgStoppedSec = (sum / allAvgTimeStopped.size()) / 1000;
        } else {
            avgStoppedSec = 0;
        }
        return avgStoppedSec;
    }

    /**
     * Method to paint the simulation
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row=0; row<ROWS; row++){
            for (int col=0; col<COLUMNS; col++) {
                Vehicle v = route[row][col];

                // set color to white if no vehicle here
                if (v == null) {
                    g.setColor(Color.WHITE);
                    // set color to vehicle color
                } else {
                    g.setColor(v.getColor());
                }

                // paint the location of the vehicle
                if (v != null) {
                    if (v.getSize() == 1) {
                        g.fillRect(col * SIZE, row * SIZE, SIZE + 9, SIZE + 9);
                    } else if (v.getSize() == 2) {
                        g.fillRect(col * SIZE, row * SIZE, SIZE + 12, SIZE + 10);
                    } else {
                        g.fillRect(col * SIZE, row * SIZE, SIZE + 16, SIZE + 11);
                    }
                }
            }
        }
        if (started == false) {

            g.setColor(Color.BLACK);
            //top two veritcal
            g.fillRect(370, 10, 35, 280);
            g.fillRect(680, 10, 35, 280);
            //bottom two vertical
            g.fillRect(370, 520, 35, 280);
            g.fillRect(680, 520, 35, 280);
            //top two horizontal
            g.fillRect(105, 280, 300, 35);
            g.fillRect(680, 280, 300, 35);
            //bottom two horizontal
            g.fillRect(105, 490, 300, 35);
            g.fillRect(680, 490, 300, 35);
            //Horizontal lane dashes
            g.fillRect(115, 395, 35, 10);
            g.fillRect(195, 395, 35, 10);
            g.fillRect(275, 395, 35, 10);
            g.fillRect(355, 395, 35, 10);
            g.fillRect(680, 395, 35, 10);
            g.fillRect(760, 395, 35, 10);
            g.fillRect(840, 395, 35, 10);
            g.fillRect(920, 395, 35, 10);
            //Vertical lane dashes
            g.fillRect(540, 20, 10, 35);
            g.fillRect(540, 100, 10, 35);
            g.fillRect(540, 180, 10, 35);
            g.fillRect(540, 260, 10, 35);
            g.fillRect(540, 520, 10, 35);
            g.fillRect(540, 600, 10, 35);
            g.fillRect(540, 680, 10, 35);
            g.fillRect(540, 760, 10, 35);
        } else {
            if (isLanesOneAndThree) {
                g.setColor(Color.GREEN);
                //top two veritcal
                g.fillRect(370, 10, 35, 280);
                g.fillRect(680, 10, 35, 280);
                //bottom two vertical
                g.fillRect(370, 520, 35, 280);
                g.fillRect(680, 520, 35, 280);
                g.setColor(Color.RED);
                //top two horizontal
                g.fillRect(105, 280, 300, 35);
                g.fillRect(680, 280, 300, 35);
                //bottom two horizontal
                g.fillRect(105, 490, 300, 35);
                g.fillRect(680, 490, 300, 35);
                g.setColor(Color.BLACK);
                //Horizontal lane dashes
                g.fillRect(115, 395, 35, 10);
                g.fillRect(195, 395, 35, 10);
                g.fillRect(275, 395, 35, 10);
                g.fillRect(355, 395, 35, 10);
                g.fillRect(680, 395, 35, 10);
                g.fillRect(760, 395, 35, 10);
                g.fillRect(840, 395, 35, 10);
                g.fillRect(920, 395, 35, 10);
                //Vertical lane dashes
                g.fillRect(540, 20, 10, 35);
                g.fillRect(540, 100, 10, 35);
                g.fillRect(540, 180, 10, 35);
                g.fillRect(540, 260, 10, 35);
                g.fillRect(540, 520, 10, 35);
                g.fillRect(540, 600, 10, 35);
                g.fillRect(540, 680, 10, 35);
                g.fillRect(540, 760, 10, 35);
            } else if (isLanesZeroAndTwo) {
                g.setColor(Color.RED);
                //top two veritcal
                g.fillRect(370, 20, 35, 270);
                g.fillRect(680, 20, 35, 270);
                //bottom two vertical
                g.fillRect(370, 520, 35, 270);
                g.fillRect(680, 520, 35, 270);
                g.setColor(Color.GREEN);
                //top two horizontal
                g.fillRect(115, 280, 290, 35);
                g.fillRect(680, 280, 290, 35);
                //bottom two horizontal
                g.fillRect(115, 490, 290, 35);
                g.fillRect(680, 490, 290, 35);
                g.setColor(Color.BLACK);
                //Horizontal lane dashes
                g.fillRect(115, 395, 35, 10);
                g.fillRect(195, 395, 35, 10);
                g.fillRect(275, 395, 35, 10);
                g.fillRect(355, 395, 35, 10);
                g.fillRect(680, 395, 35, 10);
                g.fillRect(760, 395, 35, 10);
                g.fillRect(840, 395, 35, 10);
                g.fillRect(920, 395, 35, 10);
                //Vertical lane dashes
                g.fillRect(540, 20, 10, 35);
                g.fillRect(540, 100, 10, 35);
                g.fillRect(540, 180, 10, 35);
                g.fillRect(540, 260, 10, 35);
                g.fillRect(540, 520, 10, 35);
                g.fillRect(540, 600, 10, 35);
                g.fillRect(540, 680, 10, 35);
                g.fillRect(540, 760, 10, 35);
            }
            else if (isYellowOneAndThree) {
                g.setColor(Color.YELLOW);
                //top two veritcal
                g.fillRect(370, 20, 35, 270);
                g.fillRect(680, 20, 35, 270);
                //bottom two vertical
                g.fillRect(370, 520, 35, 270);
                g.fillRect(680, 520, 35, 270);
                g.setColor(Color.RED);
                //top two horizontal
                g.fillRect(115, 280, 290, 35);
                g.fillRect(680, 280, 290, 35);
                //bottom two horizontal
                g.fillRect(115, 490, 290, 35);
                g.fillRect(680, 490, 290, 35);
                g.setColor(Color.BLACK);
                //Horizontal lane dashes
                g.fillRect(115, 395, 35, 10);
                g.fillRect(195, 395, 35, 10);
                g.fillRect(275, 395, 35, 10);
                g.fillRect(355, 395, 35, 10);
                g.fillRect(680, 395, 35, 10);
                g.fillRect(760, 395, 35, 10);
                g.fillRect(840, 395, 35, 10);
                g.fillRect(920, 395, 35, 10);
                //Vertical lane dashes
                g.fillRect(540, 20, 10, 35);
                g.fillRect(540, 100, 10, 35);
                g.fillRect(540, 180, 10, 35);
                g.fillRect(540, 260, 10, 35);
                g.fillRect(540, 520, 10, 35);
                g.fillRect(540, 600, 10, 35);
                g.fillRect(540, 680, 10, 35);
                g.fillRect(540, 760, 10, 35);
            }
            else if (enableYellow) {
                g.setColor(Color.RED);
                //top two vertical
                g.fillRect(370, 20, 35, 270);
                g.fillRect(680, 20, 35, 270);
                //bottom two vertical
                g.fillRect(370, 520, 35, 270);
                g.fillRect(680, 520, 35, 270);
                g.setColor(Color.YELLOW);
                //top two horizontal
                g.fillRect(115, 280, 290, 35);
                g.fillRect(680, 280, 290, 35);
                //bottom two horizontal
                g.fillRect(115, 490, 290, 35);
                g.fillRect(680, 490, 290, 35);
                g.setColor(Color.BLACK);
                //Horizontal lane dashes
                g.fillRect(115, 395, 35, 10);
                g.fillRect(195, 395, 35, 10);
                g.fillRect(275, 395, 35, 10);
                g.fillRect(355, 395, 35, 10);
                g.fillRect(680, 395, 35, 10);
                g.fillRect(760, 395, 35, 10);
                g.fillRect(840, 395, 35, 10);
                g.fillRect(920, 395, 35, 10);
                //Vertical lane dashes
                g.fillRect(540, 20, 10, 35);
                g.fillRect(540, 100, 10, 35);
                g.fillRect(540, 180, 10, 35);
                g.fillRect(540, 260, 10, 35);
                g.fillRect(540, 520, 10, 35);
                g.fillRect(540, 600, 10, 35);
                g.fillRect(540, 680, 10, 35);
                g.fillRect(540, 760, 10, 35);
            }
        }
    }
}
    
