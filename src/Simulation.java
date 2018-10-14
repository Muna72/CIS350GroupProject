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
     * @param p 
     */
    private void placeVehicle(Vehicle p){   
        
        int gen = rand.nextInt(4 - 1 + 1) + 1; //TODO will need ot change if more than one intersection

        switch(gen) {
            case 1:  gen = 1;
                     intersection1.entryPoint[0].add(p);
                     p.setQue(intersection1.entryPoint[0]);
                     break;
            case 2:  gen = 2;
                    intersection1.entryPoint[1].add(p);
                     p.setQue(intersection1.entryPoint[1]);
                     break;
            case 3:  gen = 3;
                    intersection1.entryPoint[2].add(p);
                     p.setQue(intersection1.entryPoint[2]);
                     break;
            case 4:  gen = 4;
                     intersection1.entryPoint[3].add(p);
                     p.setQue(intersection1.entryPoint[3]);
                     break;
            default:
                    intersection1.entryPoint[0].add(p);
                     p.setQue(intersection1.entryPoint[0]);
                     break;         
        } 
        
        if(firstPer == true) {
            p.setCreateTime(getSimTimeLeft()); //TODO do we need this?
            firstPer = false;
        }
        createLanes();
        repaint();
    }
    
    /**Method to reset the simulation *************************
     * 
     */
    public void reset() {
        
        secsTillNextVehicle = 0;
        totalTime = 0;
        avgStoppedSec = 0;
        numOfIntersections = 1; //base case until user input allowed
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
        
        Vehicle p = null;

        int gen = rand.nextInt(3 - 1 + 1) + 1;
            
            
        if(numOfVehicles <= MAX_VEHICLES) {
            if(gen == 1) {
               p = new Car();
            }
            if(gen == 2) {
                p = new UtilityVehicle();
            }
            if(gen == 3) {
                p = new SemiTruck();
            }
            p.setCreateTime(getSimTimeLeft());
            placeVehicle(p);
            allVehicles.add(p);
            ++numOfVehicles;
        }    
    }
    
    /**
     * Method to select checkout for a Vehicle (splits them up evenly)
     * @param p 
     */
    public void selectIntersection(Vehicle p) {
        
        if(numOfIntersections == 1) {
            if(vHolder.getQue() == null) {
                intersection1.entryPoint[0].add(p);
                p.setQue(intersection1.entryPoint[0]);
                vHolder.setQue(intersection1.entryPoint[0]);
            } 
            else if(vHolder.getQue() == intersection1.entryPoint[0]){
                intersection1.entryPoint[1].add(p);
                p.setQue(intersection1.entryPoint[1]);
                vHolder.setQue(intersection1.entryPoint[1]);
            }
            else if(vHolder.getQue() == intersection1.entryPoint[1]) {
                intersection1.entryPoint[2].add(p);
                p.setQue(intersection1.entryPoint[2]);
                vHolder.setQue(intersection1.entryPoint[2]);
            }
            else if (vHolder.getQue() == intersection1.entryPoint[2]) {
                intersection1.entryPoint[3].add(p);
                p.setQue(intersection1.entryPoint[3]);
                vHolder.setQue(intersection1.entryPoint[3]);
            }
        }
        if(numOfIntersections == 2) {
            // TODO logic here
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
         Vehicle userCar = new Car(); //TODO setIsUserCar to true

         switch (gen) {
             case 1:
                 gen = 1;
                 userLane = intersection1.entryPoint[0];
                 userCar.setQue(intersection1.entryPoint[0]);
                 break;
             case 2:
                 gen = 2;
                 userLane = intersection1.entryPoint[1];
                 userCar.setQue(intersection1.entryPoint[1]);
                 break;
             case 3:
                 gen = 3;
                 userLane = intersection1.entryPoint[2];
                 userCar.setQue(intersection1.entryPoint[2]);
                 break;
             case 4:
                 gen = 4;
                 userLane = intersection1.entryPoint[3];
                 userCar.setQue(intersection1.entryPoint[3]);
                 break;
             default:
                 userLane = intersection1.entryPoint[0];
                 userCar.setQue(intersection1.entryPoint[0]);
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
     * @param p
     * @return 
     */
    public void switchLanes(Vehicle p) {
        
        boolean isInCheck = false;
        

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
        LinkedList<Vehicle> checkHolder= null;
         
        
        //generate first Vehicle shortly into simulation
        if(currTime == totalTime - 500) {
            firstPer = true;
            addVehicle();
            timeVehicleAdded = currTime;
        }
        
        //generate another Vehicle at ever vTime seconds (or asap after if delay causes simulation to pass vTime)
        if((timeVehicleAdded - currTime) >= vTime) {
            addVehicle();
            timeVehicleAdded = currTime;
        }
        
        int VehicleNum = 1;
        for(int u = 0; u < allVehicles.size(); ++u) {
            
            Vehicle p = allVehicles.get(u);
            

//TODO determine whether vehicle should stya, switch lanes, or leave the map
                //  route[p.getLocation().getRow()][p.getLocation().getCol()] = null;
                //   eatHolder.remove(p);
                //  selectCheckout(p);
                //removeVehicle(p);
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
                g.fillRect(col*SIZE, row*SIZE, SIZE, SIZE);
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
    
