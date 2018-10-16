import sun.awt.image.ImageWatched;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author Muna Gigowski
 * @version September 2018
 */
public class Simulation extends JPanel {
    
    private Vehicle[][] route;

    public ArrayList <Vehicle> allVehicles;
    public ArrayList<Double> allAvgTimes;
    public ArrayList<Double> allAvgTimeStopped;
    public ArrayList<Integer> allQueLengths;
    public Intersection intersection1;
    public Intersection intersection2;

    private final int ROWS=80, COLUMNS=120, SIZE=10;
    private final int MAX_VEHICLES = 500;

    //Instance variable declarations
    private double secsTillNextVehicle;
    private double vTime;
    private double avgStoppedSec;
    private double totalTime;
    private double vehicleSpeed;
    private double simTimeLeft;
    private double timeVehicleAdded;
    private int numOfVehicles;
    private int numOfIntersections;
    private int maxLaneLength;
    private int finished;
    private double totalAvgVehicleTime;
    private double totalAvgStoppedTime;
    private boolean firstPer = false;
    private boolean isLanesOneAndThree = false;
    private Random rand = new Random();
    Vehicle vHolder = new Car(); //need to give parameters
    

/**
 * Class Constructor initializes instance variables
 * @param secNext
 * @param totTime
 */
    public Simulation(double secNext, double totTime, int numOfInts){

        route = new Vehicle[ROWS][COLUMNS];
        allVehicles = new ArrayList<Vehicle>();
        allAvgTimes = new ArrayList<Double>();
        allAvgTimeStopped = new ArrayList<Double>();
        allQueLengths = new ArrayList<Integer>();
        intersection1 = new Intersection();
        intersection2 = new Intersection();

        secsTillNextVehicle = secNext;
        totalTime = totTime;
        if(numOfInts > 1) {
            numOfIntersections = numOfInts;
        } else {
            numOfIntersections = 1;
        }
        numOfVehicles = 0;
        timeVehicleAdded = 0;
        maxLaneLength = 0;
        setPreferredSize(new Dimension(COLUMNS*SIZE, ROWS*SIZE));
    }
    
    /**
     * Method to set average seconds until next Vehicle is generated
     * @param secNext 
     */
    public void setSecsTillNextVehicle(double secNext) {
        secsTillNextVehicle = secNext;
    }

    /**
     * Method to set average cashier seconds
     * @param speed
     */
    public void setVehicleSpeed(double speed) { //TODO MAY NOT NEED THIS AT ALL, JUST CHANGE DELAY
        vehicleSpeed = 1000 * speed;
    }
    
    /**
     * Method to set total simulation time
     * @param totTime 
     */
    public void setTotalTime(double totTime) {
        totalTime = totTime;
    }

    
    /**
     * Method to set number of eateries at beginning of simulation
     * @param numInts
     */
    public void setNumOfIntersections(int numInts) {
        numOfIntersections = numInts;
    }
    
    /**
     * Method to set
     * @param pt 
     */
    public void setVTime(double pt) {
        vTime = pt;
    }
    
    /**
     * Method to place Vehicle in a restaurant que
     * @param v
     */
    private void placeVehicle(Vehicle v){
        
        int gen = rand.nextInt(4 - 1 + 1) + 1; //TODO change for two intersections

        switch(gen) {
            case 1:  gen = 1;
                     intersection1.entryPoint[0].add(v);
                     v.setQue(intersection1.entryPoint[0]);
                     setPath(v);
                     break;
            case 2:  gen = 2;
                    intersection1.entryPoint[1].add(v);
                     v.setQue(intersection1.entryPoint[1]);
                     setPath(v);
                     break;
            case 3:  gen = 3;
                    intersection1.entryPoint[2].add(v);
                     v.setQue(intersection1.entryPoint[2]);
                     setPath(v);
                     break;
            case 4:  gen = 4;
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
        
        if(firstPer == true) {
            v.setCreateTime(getSimTimeLeft()); //TODO do we need this?
            firstPer = false;
        }
        System.out.println("vehicle is in que: " + v.getQue());

        createLanes();
        repaint();
    }

    public void setPath(Vehicle v) {

        LinkedList<Vehicle> laneHolder = v.getQue();
        int dirGen = rand.nextInt(3 - 1 + 1) + 1;

        if(laneHolder == intersection1.entryPoint[0]) {
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
        if(laneHolder == intersection1.entryPoint[1]) {
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
        if(laneHolder == intersection1.entryPoint[2]) {
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
        if(laneHolder == intersection1.entryPoint[3]) {
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
    }
    
    /**Method to reset the simulation *************************
     * 
     */
    public void reset() {
        
        secsTillNextVehicle = 0;
        totalTime = 0;
        avgStoppedSec = 0;
        numOfIntersections = 1;
        timeVehicleAdded = 0;
        finished = 0;
        allVehicles.clear();
        allAvgTimes.clear();
        allQueLengths.clear();
        intersection1.clear(); //TODO make sure this happens
        intersection2.clear();
        
        for(int i = 0; i < ROWS; ++i) {
            for(int y = 0; y < COLUMNS; ++y) {
                route[i][y] = null;
            }
        }
    }
    
    /**
     * Method to actively set simulation time left
     * @param s 
     */
    public void setSimTimeLeft(double s) {
        simTimeLeft = s;
    }
    
    /**
     * Method to get simulation time left
     * @return 
     */ 
    public double getSimTimeLeft() {
        return simTimeLeft;
    }
    
   /**
    * Method to remove Vehicle from simulation **************
    * @param p 
    */ 
    public void removeVehicle(Vehicle p) {
        
        LinkedList<Vehicle> holder = p.getQue();
        
        route[p.getLocation().getRow()][p.getLocation().getCol()] = null;
        holder.remove(p);
        allVehicles.remove(p);
        repaint();
    }

    /**
     * Method to add a Vehicle to the simulation
     */
    public void addVehicle(){
        
        Vehicle v = null;

        int typeGen = rand.nextInt(4 - 1 + 1) + 1;
            
        if(numOfVehicles <= MAX_VEHICLES) {
            if(typeGen == 1) {
               v = new Car();
            }
            if(typeGen == 2) {
                v = new UtilityVehicle();
            }
            if(typeGen == 3) {
                v = new SemiTruck();
            }
            if(typeGen == 4) {
                v = new Car();
            }

            v.setCreateTime(getSimTimeLeft());
            placeVehicle(v);
            allVehicles.add(v);
            ++numOfVehicles;
        }    
    }

    /**
     * Method to place user's car in the appropriate place
     * @param place
     * @return
     */
     public void placeUserCar(String place) {

         int gen = rand.nextInt(4 - 1 + 1) + 1; //TODO will need ot change if more than one intersection
         LinkedList<Vehicle> userLane;
         Vehicle userCar = new Car(true);

         switch (gen) {
             case 1:
                 gen = 1;
                 userLane = intersection1.entryPoint[0];
                 userCar.setQue(userLane);
                 break;
             case 2:
                 gen = 2;
                 userLane = intersection1.entryPoint[1];
                 userCar.setQue(userLane);
                 break;
             case 3:
                 gen = 3;
                 userLane = intersection1.entryPoint[2];
                 userCar.setQue(userLane);
                 break;
             case 4:
                 gen = 4;
                 userLane = intersection1.entryPoint[3];
                 userCar.setQue(userLane);
                 break;
             default:
                 userLane = intersection1.entryPoint[0];
                 userCar.setQue(userLane);
                 break;
         }
             if (place == "front") {
                 userCar.getQue().add(0, userCar);
             }
             if (place == "middle") {
                 userCar.getQue().add((userCar.getQue().size()) / 2, userCar);
             }
             if (place == "back") {
                 userCar.getQue().add(userCar.getQue().size() - 1, userCar);
             }
     }
    /**
     * Method to see of a Vehicle is in any of the checkout lines
     * @param v
     * @return 
     */
    public void switchLanes(Vehicle v) {
        
        LinkedList<Vehicle> holder = v.getQue();
        Direction dir = v.getPath();

        switch (dir) {
            case NORTH:
                intersection1.entryPoint[1].add(v);
                holder.remove(v);
                v.setQue(intersection1.entryPoint[1]);
                break;
            case EAST:
                intersection1.entryPoint[2].add(v);
                holder.remove(v);
                v.setQue(intersection1.entryPoint[2]);
                break;
            case SOUTH:
                intersection1.entryPoint[3].add(v);
                holder.remove(v);
                v.setQue(intersection1.entryPoint[3]);
                break;
            case WEST:
                intersection1.entryPoint[0].add(v);
                holder.remove(v);
                v.setQue(intersection1.entryPoint[0]);
                break;
        }
    }

    public void setGreenLanes() { //TODO if this is true, then move cars through one and three, or vice versa
        if(!isLanesOneAndThree) {
            isLanesOneAndThree = true;
        }
    }

        
    /**
     * Method to create visual lines of people in the ques for the GUI, shows
     * up to fifteen people in each que
     */
    public void createLanes() { //TODO Logic for this function is not fulling working yet
        
        int r;
        int y;
        
        if(intersection1.entryPoint[0] != null) {
            
            r = 7;
            y = 32;
            
            if(intersection1.entryPoint[0].size() < 16) {
                for(int p = 0; p < intersection1.entryPoint[0].size(); ++p) {
                    Location loc = new Location(r,y);
                    intersection1.entryPoint[0].get(p).setLocation(loc);
                    route[intersection1.entryPoint[0].get(p).getLocation().getRow()][intersection1.entryPoint[0].get(p).getLocation().getCol()] = intersection1.entryPoint[0].get(p);
                    y = y - 2;
                }  
            }
            if(intersection1.entryPoint[0].size() >= 16) {
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,0);
                    intersection1.entryPoint[0].get(p).setLocation(loc);
                    route[intersection1.entryPoint[0].get(p).getLocation().getRow()][intersection1.entryPoint[0].get(p).getLocation().getCol()] = intersection1.entryPoint[0].get(p);
                    y = y - 2;
                }            
            }    
        }

        if(intersection1.entryPoint[1] != null){
            
            r = 17;
            y = 32;
            
            if(intersection1.entryPoint[1].size() < 16) {
                for(int p = 0; p < intersection1.entryPoint[1].size(); ++p) {
                    Location loc = new Location(r,y);
                    intersection1.entryPoint[1].get(p).setLocation(loc);
                    route[intersection1.entryPoint[1].get(p).getLocation().getRow()][intersection1.entryPoint[1].get(p).getLocation().getCol()] = intersection1.entryPoint[1].get(p);
                    y = y - 2;
                }  
            }
            if(intersection1.entryPoint[1].size() >= 16) {
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    intersection1.entryPoint[1].get(p).setLocation(loc);
                    route[intersection1.entryPoint[1].get(p).getLocation().getRow()][intersection1.entryPoint[1].get(p).getLocation().getCol()] = intersection1.entryPoint[1].get(p);
                    y = y - 2;
                }             
            } 
        }
        
        if(intersection1.entryPoint[2] != null) {
            
            r = 27;
            y = 32;
            
            if(intersection1.entryPoint[2].size() < 16) {
                for(int p = 0; p < intersection1.entryPoint[2].size(); ++p) {
                    Location loc = new Location(r,y);
                    intersection1.entryPoint[2].get(p).setLocation(loc);
                    route[intersection1.entryPoint[2].get(p).getLocation().getRow()][intersection1.entryPoint[2].get(p).getLocation().getCol()] = intersection1.entryPoint[2].get(p);
                    y = y - 2;
                }  
            }
            if(intersection1.entryPoint[2].size() >= 16) {
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    intersection1.entryPoint[2].get(p).setLocation(loc);
                    route[intersection1.entryPoint[2].get(p).getLocation().getRow()][intersection1.entryPoint[2].get(p).getLocation().getCol()] = intersection1.entryPoint[2].get(p);
                    y = y - 2;
                }             
            }   
        }
        
        if(intersection1.entryPoint[3] != null) {
            
            r = 37;
            y = 32;
            
            if(intersection1.entryPoint[3].size() < 16) {
                for(int p = 0; p < intersection1.entryPoint[3].size(); ++p) {
                    Location loc = new Location(r,y);
                    intersection1.entryPoint[3].get(p).setLocation(loc);
                    route[intersection1.entryPoint[3].get(p).getLocation().getRow()][intersection1.entryPoint[3].get(p).getLocation().getCol()] = intersection1.entryPoint[3].get(p);
                    y = y - 2;
                }  
            }
            if(intersection1.entryPoint[3].size() >= 16) {
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    intersection1.entryPoint[3].get(p).setLocation(loc);
                    route[intersection1.entryPoint[3].get(p).getLocation().getRow()][intersection1.entryPoint[3].get(p).getLocation().getCol()] = intersection1.entryPoint[3].get(p);
                    y = y - 2;
                }             
            } 
        }
        repaint();
    }
    
    /**
     * Method to check for longest que length
     */
    public void checkLengths() {
         
        if (intersection1.entryPoint[0] != null) {
            allQueLengths.add(intersection1.entryPoint[0].size());
        }    
        if(intersection1.entryPoint[1] != null) {
            allQueLengths.add(intersection1.entryPoint[1].size());
        }
        if(intersection1.entryPoint[2] != null) {
            allQueLengths.add(intersection1.entryPoint[2].size());
        }
        if(intersection1.entryPoint[3] != null) {
            allQueLengths.add(intersection1.entryPoint[3].size());
        }
        for(int i = 0; i < allQueLengths.size(); ++i) {
            if(allQueLengths.get(i) > maxLaneLength) {
                maxLaneLength = allQueLengths.get(i);
            }
        }
    }
    
    /**
     * Method that progresses the simulation
     */
    public void takeAction(){
        
        double currTime = getSimTimeLeft();
        LinkedList<Vehicle> laneHolder = null;

        //generate first Vehicle shortly into simulation
        if(currTime == totalTime - 500) {
            System.out.println("hit takenAction() loop hit");
            firstPer = true;
            addVehicle();
            System.out.println("First vehicle added at time: " + currTime);
            timeVehicleAdded = currTime;
        }
        
        //generate another Vehicle at ever vTime seconds (or asap after if delay causes simulation to pass vTime)
        if((timeVehicleAdded - currTime) >= vTime) {
            addVehicle();
            timeVehicleAdded = currTime;
        }

        //Every ten seconds switch which lanes have green light
        if(currTime % 10 == 0) {
           if(isLanesOneAndThree) {
               isLanesOneAndThree = false;
           } else {
               isLanesOneAndThree = true;
           }
        }
        
        //loop through all vehicles
        for(int u = 0; u < allVehicles.size(); ++u) {
            
            Vehicle v = allVehicles.get(u);
            laneHolder = v.getQue();

                if(laneHolder == intersection1.entryPoint[1] || laneHolder == intersection1.entryPoint[3]) {
                    if (isLanesOneAndThree) {
                        if(v.getLocation() == new Location(3,4) || v.getLocation() == new Location(3,4)) {
                            switchLanes(v);
                        }
                        if(v.getLocation() == new Location(3,4) || v.getLocation() == new Location(3,4)) {
                            removeVehicle(v);
                        }
                    }
                } else {
                    if(!isLanesOneAndThree) {
                        if(v.getLocation() == new Location(3,4) || v.getLocation() == new Location(3,4)) {
                            switchLanes(v);
                        }
                        if(v.getLocation() == new Location(3,4) || v.getLocation() == new Location(3,4)) {
                            removeVehicle(v);
                        }
                    }
                }
                //TODO check if car is on first or end side of intersection, and
                //TODO remove car if their location is specified as the "end of the road"
                    laneHolder.remove(v);
                    allAvgTimes.add(v.getCreateTime() - currTime);
                    //  route[p.getLocation().getRow()][p.getLocation().getCol()] = null;
                    removeVehicle(v);
                    ++finished;
        }  
        checkLengths();
        createLanes();
        repaint();
    }
    
   /**
    * Get the number of people who finished the simulation
    * @return 
    */ 
    public int getFinished() {
        return finished;
    }
    
    /**
     * Method to get number of eateries
     * @return 
     */
    public int getNumOfIntersections() {
        return numOfIntersections;
    }
    
    /**
     * Method to get number of people
     * @return 
     */
    public int getNumOfVehicles() {
        return numOfVehicles;
    }
    
    /**
     * Method to get number of people that walked out
     * @return 
     */
    public int getVehiclesLeft() {
        
        int vLeft = 0;
        
        if(intersection1.entryPoint[0] != null) {
            vLeft = vLeft + intersection1.entryPoint[0].size();
        }
        if(intersection1.entryPoint[1] != null) {
            vLeft = vLeft + intersection1.entryPoint[1].size();
        }
        if(intersection1.entryPoint[2] != null) {
            vLeft = vLeft + intersection1.entryPoint[2].size();
        }
        if(intersection1.entryPoint[3] != null) {
            vLeft = vLeft + intersection1.entryPoint[3].size();
        }
        return vLeft;
    }
    
    /**
     * Metho to get max lane que length
     * @return 
     */
    public int getMaxLaneLength() {
        return maxLaneLength;
    } //Do we need more than one variation of this?

    public double calculateAvgVehicleThruTime(ArrayList<Double> allTimes) {
        double avgTime = 0;

        return avgTime;
    }

    public double calculateUserThruTime(Double endTime) {
        double thruTime = 0;

        return thruTime;
    }

    public double calculateAvgTimeStopped(ArrayList<Double> allTimes) {
        double avgTime = 0;

        return avgTime;
    }

    /**
     * Get the total average time for a Vehicle from start to finish
     * @return 
     */
    public double getTotalAvgVehicleTime(){
        
        double sum = 0;
        
        for(int i = 0; i < allAvgTimes.size(); ++i) {
            sum = sum + allAvgTimes.get(i);  
        }  
        totalAvgVehicleTime = (sum / numOfVehicles) / 1000;
        return totalAvgVehicleTime;
    }
    
    /**
     * Method to get average checkout time
     * @return 
     */
    public double getAvgStoppedTime() {
        
        double sum = 0;
        
        for(int i = 0; i < allAvgTimeStopped.size(); ++i) {
            sum = sum + allAvgTimeStopped.get(i);
        }  
        totalAvgStoppedTime = (sum / numOfVehicles) / 1000;
        return totalAvgStoppedTime;
    }

    /**
     * Method to paint the simulation
     * @param g 
     */ 
    public void paintComponent(Graphics g){
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLUMNS; col++){
                Vehicle p = route[row][col];
                
                // set color to white if no critter here
                if(p == null){
                    g.setColor(Color.WHITE);
                    // set color to critter color   
                }else{    
                    g.setColor(p.getColor());
                }

                // paint the location
                if(p.getSize() == 1) {
                    g.fillRect(col * SIZE, row * SIZE, SIZE, SIZE);
                } else if (p.getSize() == 2) {
                    g.fillRect(col * SIZE, row * SIZE, SIZE + 2, SIZE + 2);
                } else {
                    g.fillRect(col * SIZE, row * SIZE, SIZE + 4, SIZE + 4);
                }
            }
        }
        if(numOfIntersections == 1) {

            g.setColor(Color.BLACK);
            //top two veritcal
            g.fillRect(370, 20, 35, 270);
            g.fillRect(680, 20, 35, 270);
            //bottom two vertical
            g.fillRect(370, 520, 35, 270);
            g.fillRect(680, 520, 35, 270);
            //top two horizontal
            g.fillRect(115, 280, 290, 35);
            g.fillRect(680, 280, 290, 35);
            //bottom two horizontal
            g.fillRect(115, 490, 290, 35);
            g.fillRect(680, 490, 290, 35);
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


            if(intersection1.getType() == "Two Way") {
                g.drawString("Intersection (Two - Way", 365, 75);
            }
            if(intersection1.getType() == "Four Way") {
                g.drawString("Intersection (Two - Way", 365, 75);
            }
        }
        if(numOfIntersections == 2) {

        }
    }
}
    
