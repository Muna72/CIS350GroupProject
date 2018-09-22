
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author Muna Gigowski
 * @version September 2018
 */
public class Simulation extends JPanel {
    
    private Vehicle[][] cafeteria;

    public ArrayList <Vehicle> allPeople;
    public ArrayList<Double> allAvgTimes;
    public ArrayList<Double> allAvgCheckTimes;
    public ArrayList<Integer> allQueLengths;
    public ArrayList<Integer> allCheckQueLengths;
    public LinkedList<Vehicle> checkout1;
    public LinkedList<Vehicle> checkout2;
    public LinkedList<Vehicle> checkout3;
    public LinkedList<Vehicle> checkout4;
    public LinkedList<Vehicle> rest1;
    public LinkedList<Vehicle> rest2;
    public LinkedList<Vehicle> rest3;
    public LinkedList<Vehicle> rest4;
    public LinkedList<Vehicle> rest5;
    public LinkedList<Vehicle> rest6;
    public LinkedList<Vehicle> rest7;

    private final int ROWS=80, COLUMNS=120, SIZE=10;
    private final int MAX_PEOPLE = 500;

    //Instance variable declarations
    private double secsTillNextVehicle;
    private double pTime;
    private double avgCashSec;
    private double totalTime;
    private double avgEatSec;
    private double secBeforeVehicleLeaves;
    private double simTimeLeft;
    private double timeVehicleAdded;
    private int numOfEateries;
    private int numOfPeople;
    private int numOfRegPpl;
    private int numOfSpecPpl;
    private int numOfLimPpl;
    private int numOfCheckouts;
    private int finished;
    private int maxRestLength;
    private int maxCheckLength;
    private double totalAvgVehicleTime;
    private double totalAvgCheckTime;
    private boolean firstPer = false;
    private Random rand = new Random();
    Vehicle pHolder = new RegularVehicle(10,10,10,10);
    

/**
 * Class Constructor initializes instance variables
 * @param secNext
 * @param avgCash
 * @param totTime
 * @param avgEat
 * @param secLeave
 * @param numEats 
 */
    public Simulation(double secNext, double avgCash, double totTime, double avgEat, double secLeave, int numEats){
        
        cafeteria = new Vehicle[ROWS][COLUMNS];
        allPeople = new ArrayList<Vehicle>();  
        allAvgTimes = new ArrayList<Double>();
        allAvgCheckTimes = new ArrayList<Double>();
        allQueLengths = new ArrayList<Integer>();
        allCheckQueLengths = new ArrayList<Integer>();
        rest1 = new LinkedList<Vehicle>();
        rest2 = new LinkedList<Vehicle>();
        rest3 = new LinkedList<Vehicle>();
        rest4 = new LinkedList<Vehicle>();
        rest5 = new LinkedList<Vehicle>();
        rest6 = new LinkedList<Vehicle>();
        rest7 = new LinkedList<Vehicle>();
        checkout1 = new LinkedList<Vehicle>();
        checkout2 = new LinkedList<Vehicle>();
        checkout3 = new LinkedList<Vehicle>();
        checkout4 = new LinkedList<Vehicle>();

        secsTillNextVehicle = secNext;
        avgCashSec = avgCash;
        totalTime = totTime;
        avgEatSec = avgEat;
        secBeforeVehicleLeaves = secLeave;
        numOfEateries = numEats;
        numOfPeople = 0;
        numOfCheckouts = 2;
        timeVehicleAdded = 0;
        maxRestLength = 0;
        maxCheckLength = 0;
        
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
     * @param avgCash 
     */
    public void setAvgCashSec(double avgCash) {
        avgCashSec = avgCash;
    }
    
    /**
     * Method to set total simulation time
     * @param totTime 
     */
    public void setTotalTime(double totTime) {
        totalTime = totTime;
    }
    
    /**
     * Method to set average eatery time
     * @param avgEat 
     */
    public void setAvgEatSec(double avgEat) {
        avgEatSec = avgEat;
    }
    
    /**
     * Method to set average seconds before a Vehicle leaves
     * @param secLeaves 
     */
    public void setSecBeforeVehicleLeaves(double secLeaves) {
        secBeforeVehicleLeaves = secLeaves;
    }
    
    /**
     * Method to set number of eateries at beginning of simulation
     * @param numEats 
     */
    public void setNumOfEateries(int numEats) {
        numOfEateries = numEats;
    }
    
    /**
     * Method to set
     * @param pt 
     */
    public void setPTime(double pt) {
        pTime = pt;
    }
    
    /**
     * Method to place Vehicle in a restaurant que
     * @param p 
     */
    private void placeVehicle(Vehicle p){   
        
        int gen = rand.nextInt(numOfEateries - 1 + 1) + 1; 
        
        switch(gen) {
            case 1:  gen = 1;
                     rest1.add(p);
                     p.setQue(rest1);
                     break;
            case 2:  gen = 2;
                     rest2.add(p);
                     p.setQue(rest2);
                     break;
            case 3:  gen = 3;
                     rest3.add(p);
                     p.setQue(rest3);
                     break;
            case 4:  gen = 4;
                     rest4.add(p);
                     p.setQue(rest4);
                     break;
            case 5:  gen = 5;
                     rest5.add(p);
                     p.setQue(rest5);
                     break;
            case 6:  gen = 6;
                     rest6.add(p);
                     p.setQue(rest6);
                     break;
            case 7:  gen = 7;
                     rest7.add(p);
                     p.setQue(rest7);
                     break;
            default: 
                     rest1.add(p);
                     p.setQue(rest1);
                     break;         
        } 
        
        if(firstPer == true) {
            p.setBeginEatTime(getSimTimeLeft());
            firstPer = false;
        }
        createLines();
        repaint();
    }
    
    /**Method to reset the simulation *************************
     * 
     */
    public void reset() {
        
        secsTillNextVehicle = 0;
        avgCashSec = 0;
        totalTime = 0;
        avgEatSec = 0;
        secBeforeVehicleLeaves = 0;
        numOfEateries = 0;
        numOfCheckouts = 2;
        timeVehicleAdded = 0;
        finished = 0;
        allPeople.clear();
        allAvgTimes.clear();
        allQueLengths.clear();
        allCheckQueLengths.clear();
        rest1.clear();
        rest2.clear();
        rest3.clear();
        rest4.clear();
        rest5.clear();
        rest6.clear();
        rest7.clear();
        checkout1.clear();
        checkout2.clear();
        checkout3.clear();
        checkout4.clear();
        
        for(int i = 0; i < ROWS; ++i) {
            for(int y = 0; y < COLUMNS; ++y) {
                cafeteria[i][y] = null;
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
        
        cafeteria[p.getLocation().getRow()][p.getLocation().getCol()] = null;
        holder.remove(p);
        allPeople.remove(p);
        repaint();
    }    
    
    /**
     * Method to remove eatery
     */
    public void removeEatery() {
  
        if(numOfEateries == 2) {
            if(rest2.isEmpty()) {
                --numOfEateries;
                repaint();    
            }
            else {
                JOptionPane.showMessageDialog(null, "Cannot remove eatery with people in line");
            }
        }  
        if(numOfEateries == 3) {
            if(rest3.isEmpty()) {
                --numOfEateries;
                repaint();    
            }
            else {
                JOptionPane.showMessageDialog(null, "Cannot remove eatery with people in line");
            }
        } 
        if(numOfEateries == 4) {
            if(rest4.isEmpty()) {
                --numOfEateries;
                repaint();    
            }
            else {
                JOptionPane.showMessageDialog(null, "Cannot remove eatery with people in line");
            }
        }
    }
    
    /**
     * Method to remove a checkout
     */
    public void removeCheckout(){
        
        if(numOfCheckouts == 2) {
            if(checkout2.isEmpty()) {
                --numOfCheckouts;
                repaint();    
            }
            else {
                JOptionPane.showMessageDialog(null, "Cannot remove checkout with people in line");
            }
        } 
        if(numOfCheckouts == 3) {
            if(checkout3.isEmpty()) {
                --numOfCheckouts;
                repaint();    
            }
            else {
                JOptionPane.showMessageDialog(null, "Cannot remove checkout with people in line");
            }
        }
        if(numOfCheckouts == 4) {
            if(checkout4.isEmpty()) {
                --numOfCheckouts;
                repaint();    
            }
            else {
                JOptionPane.showMessageDialog(null, "Cannot remove checkout with people in line");
            }
        }
    }

    /**
     * Method to add a Vehicle to the simulation
     */
    public void addVehicle(){
        
        Vehicle p = null;

        int gen = rand.nextInt(3 - 1 + 1) + 1;
            
            
        if(numOfPeople <= MAX_PEOPLE) {    
            if(gen == 1) {
                p = new RegularVehicle(avgCashSec,avgEatSec, secBeforeVehicleLeaves, getSimTimeLeft());  
                ++numOfRegPpl;
            }
            if(gen == 2) {
                p = new SpecialNeedsVehicle(avgCashSec,avgEatSec, secBeforeVehicleLeaves, getSimTimeLeft()); 
                ++numOfSpecPpl;
            }
            if(gen == 3) {
                p = new LimitedTimeVehicle(avgCashSec,avgEatSec, secBeforeVehicleLeaves, getSimTimeLeft()); 
                ++numOfLimPpl;
            }   
            p.setCreateTime(getSimTimeLeft());
            placeVehicle(p);
            allPeople.add(p);
            ++numOfPeople;
        }    
    }
    
    /**
     * Method to select checkout for a Vehicle (splits them up evenly)
     * @param p 
     */
    public void selectCheckout(Vehicle p) {
        
        if(numOfCheckouts == 2) {
            if(pHolder.getQue() == null) {
                checkout1.add(p);
                p.setQue(checkout1); 
                pHolder.setQue(checkout1);
            } 
            else if(pHolder.getQue() == checkout1){
                checkout2.add(p);
                p.setQue(checkout2);
                pHolder.setQue(checkout2);
            }
            else if(pHolder.getQue() == checkout2) {
                checkout1.add(p);
                p.setQue(checkout1); 
                pHolder.setQue(checkout1);    
            }
        }
        if(numOfCheckouts == 3) {
            if(pHolder.getQue() == null) {
                checkout1.add(p);
                p.setQue(checkout1); 
                pHolder.setQue(checkout1);
            } 
            else if(pHolder.getQue() == checkout1){
                checkout2.add(p);
                p.setQue(checkout2);
                pHolder.setQue(checkout2);
            }
            else if(pHolder.getQue() == checkout2) {
                checkout3.add(p);
                p.setQue(checkout3); 
                pHolder.setQue(checkout3);    
            } 
            else if(pHolder.getQue() == checkout3) {
                checkout1.add(p);
                p.setQue(checkout1); 
                pHolder.setQue(checkout1);   
            }
        }
        if(numOfCheckouts == 4) {
            if(pHolder.getQue() == null) {
                checkout1.add(p);
                p.setQue(checkout1); 
                pHolder.setQue(checkout1);
            } 
            else if(pHolder.getQue() == checkout1){
                checkout2.add(p);
                p.setQue(checkout2);
                pHolder.setQue(checkout2);
            }
            else if(pHolder.getQue() == checkout2) {
                checkout3.add(p);
                p.setQue(checkout3); 
                pHolder.setQue(checkout3);    
            } 
            else if(pHolder.getQue() == checkout3) {
                checkout4.add(p);
                p.setQue(checkout4); 
                pHolder.setQue(checkout4);   
            }    
            else if(pHolder.getQue() == checkout4) {
                checkout1.add(p);
                p.setQue(checkout1); 
                pHolder.setQue(checkout1);   
            }
        }
    }
    
    /**
     * Method to see of a Vehicle is in any of the checkout lines
     * @param p
     * @return 
     */
    public boolean isInCheckout(Vehicle p) {
        
        boolean isInCheck = false;
        
        if(checkout1 != null) {
            if(p.getQue().equals(checkout1)) { 
                isInCheck = true;
            }
        }
        if(checkout2 != null) {
            if(p.getQue().equals(checkout2)) { 
                isInCheck = true;
            }
        }
        if(checkout3 != null) {
            if(p.getQue().equals(checkout3)) { 
                isInCheck = true;
            }
        }
        if(checkout4 != null) {
            if(p.getQue().equals(checkout4)) { 
                isInCheck = true;
            }
        }
        return isInCheck;
    }    
        
    /**
     * Method to create visual lines of people in the ques for the GUI, shows
     * up to fifteen people in each que
     */
    public void createLines() {
        
        int r;
        int y;
        
        if(rest1 != null) {
            
            r = 7;
            y = 32;
            
            if(rest1.size() < 16) {  
                for(int p = 0; p < rest1.size(); ++p) {
                    Location loc = new Location(r,y);
                    rest1.get(p).setLocation(loc);
                    cafeteria[rest1.get(p).getLocation().getRow()][rest1.get(p).getLocation().getCol()] = rest1.get(p);
                    y = y - 2;
                }  
            }
            if(rest1.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,0);
                    rest1.get(p).setLocation(loc);
                    cafeteria[rest1.get(p).getLocation().getRow()][rest1.get(p).getLocation().getCol()] = rest1.get(p);
                    y = y - 2;
                }            
            }    
        }

        if(rest2 != null){
            
            r = 17;
            y = 32;
            
            if(rest2.size() < 16) {
                for(int p = 0; p < rest2.size(); ++p) {
                    Location loc = new Location(r,y);
                    rest2.get(p).setLocation(loc);
                    cafeteria[rest2.get(p).getLocation().getRow()][rest2.get(p).getLocation().getCol()] = rest2.get(p);
                    y = y - 2;
                }  
            }
            if(rest2.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    rest2.get(p).setLocation(loc);
                    cafeteria[rest2.get(p).getLocation().getRow()][rest2.get(p).getLocation().getCol()] = rest2.get(p);
                    y = y - 2;
                }             
            } 
        }
        
        if(rest3 != null) {
            
            r = 27;
            y = 32;
            
            if(rest3.size() < 16) {
                for(int p = 0; p < rest3.size(); ++p) {
                    Location loc = new Location(r,y);
                    rest3.get(p).setLocation(loc);
                    cafeteria[rest3.get(p).getLocation().getRow()][rest3.get(p).getLocation().getCol()] = rest3.get(p);
                    y = y - 2;
                }  
            }
            if(rest3.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    rest3.get(p).setLocation(loc);
                    cafeteria[rest3.get(p).getLocation().getRow()][rest3.get(p).getLocation().getCol()] = rest3.get(p);
                    y = y - 2;
                }             
            }   
        }
        
        if(rest4 != null) {
            
            r = 37;
            y = 32;
            
            if(rest4.size() < 16) {
                for(int p = 0; p < rest4.size(); ++p) {
                    Location loc = new Location(r,y);
                    rest4.get(p).setLocation(loc);
                    cafeteria[rest4.get(p).getLocation().getRow()][rest4.get(p).getLocation().getCol()] = rest4.get(p);
                    y = y - 2;
                }  
            }
            if(rest4.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    rest4.get(p).setLocation(loc);
                    cafeteria[rest4.get(p).getLocation().getRow()][rest4.get(p).getLocation().getCol()] = rest4.get(p);
                    y = y - 2;
                }             
            } 
        }
        
        if(rest5 != null) {
            
            r = 47;
            y = 32;
            
            if(rest5.size() < 16) {
                for(int p = 0; p < rest5.size(); ++p) {
                    Location loc = new Location(r,y);
                    rest5.get(p).setLocation(loc);
                    cafeteria[rest5.get(p).getLocation().getRow()][rest5.get(p).getLocation().getCol()] = rest5.get(p);
                    y = y - 2;
                }  
            }
            if(rest5.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    rest5.get(p).setLocation(loc);
                    cafeteria[rest5.get(p).getLocation().getRow()][rest5.get(p).getLocation().getCol()] = rest5.get(p);
                    y = y - 2;
                }             
            }  
        }
        
        if(rest6 != null) {
            
            r = 57;
            y = 32;
            
            if(rest6.size() < 16) {
                for(int p = 0; p < rest6.size(); ++p) {
                            Location loc = new Location(r,y);
                            rest6.get(p).setLocation(loc);
                            cafeteria[rest6.get(p).getLocation().getRow()][rest6.get(p).getLocation().getCol()] = rest6.get(p);
                    y = y - 2;
                }  
            }
            if(rest6.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    rest6.get(p).setLocation(loc);
                    cafeteria[rest6.get(p).getLocation().getRow()][rest6.get(p).getLocation().getCol()] = rest6.get(p);
                    y = y - 2;
                }             
            }  
        }
        
        if(rest7 != null) {
            
            r = 67;
            y = 32;
            
            if(rest7.size() < 16) {
                for(int p = 0; p < rest7.size(); ++p) {
                    Location loc = new Location(r,y);
                    rest7.get(p).setLocation(loc);
                    cafeteria[rest7.get(p).getLocation().getRow()][rest7.get(p).getLocation().getCol()] = rest7.get(p);
                    y = y - 2;
                }  
            }
            if(rest7.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    rest7.get(p).setLocation(loc);
                    cafeteria[rest7.get(p).getLocation().getRow()][rest7.get(p).getLocation().getCol()] = rest7.get(p);
                    y = y - 2;
                }              
            }  
        }
        
        if(checkout1 != null) {
            
            r = 21;
            y = 88;
            
            if(checkout1.size() < 16) {
                for(int p = 0; p < checkout1.size(); ++p) {
                    Location loc = new Location(r,y);
                    checkout1.get(p).setLocation(loc);
                    cafeteria[checkout1.get(p).getLocation().getRow()][checkout1.get(p).getLocation().getCol()] = checkout1.get(p);
                    y = y - 2;
                }  
            }
            if(checkout1.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    checkout1.get(p).setLocation(loc);
                    cafeteria[checkout1.get(p).getLocation().getRow()][checkout1.get(p).getLocation().getCol()] = checkout1.get(p);
                    y = y - 2;
                }             
            }
        }
        
        if(checkout2 != null) {
             
            r = 31;
            y = 88;
            
            if(checkout2.size() < 16) {
                for(int p = 0; p < checkout2.size(); ++p) {
                    Location loc = new Location(r,y);
                    checkout2.get(p).setLocation(loc);
                    cafeteria[checkout2.get(p).getLocation().getRow()][checkout2.get(p).getLocation().getCol()] = checkout2.get(p);
                    y = y - 2;
                }  
            }
            if(checkout2.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    checkout2.get(p).setLocation(loc);
                    cafeteria[checkout2.get(p).getLocation().getRow()][checkout2.get(p).getLocation().getCol()] = checkout2.get(p);
                    y = y - 2;
                }            
            }
        }
        
        if(checkout3 != null) {
             
            r = 41;
            y = 88;
            
            if(checkout3.size() < 16) {
                for(int p = 0; p < checkout3.size(); ++p) {
                    Location loc = new Location(r,y);
                    checkout3.get(p).setLocation(loc);
                    cafeteria[checkout3.get(p).getLocation().getRow()][checkout3.get(p).getLocation().getCol()] = checkout3.get(p);
                    y = y - 2;
                }  
            }
            if(checkout3.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    checkout3.get(p).setLocation(loc);
                    cafeteria[checkout3.get(p).getLocation().getRow()][checkout3.get(p).getLocation().getCol()] = checkout3.get(p);
                    y = y - 2;
                }              
            } 
        } 
        
        if(checkout4 != null) {
             
            r = 51;
            y = 88;
            
            if(checkout4.size() < 16) {
                for(int p = 0; p < checkout4.size(); ++p) {
                    Location loc = new Location(r,y);
                    checkout4.get(p).setLocation(loc);
                    cafeteria[checkout4.get(p).getLocation().getRow()][checkout4.get(p).getLocation().getCol()] = checkout4.get(p);
                    y = y - 2;
                }  
            }
            if(checkout4.size() >= 16) { 
                for(int p = 0; p < 16; ++p) {
                    Location loc = new Location(r,y);
                    checkout4.get(p).setLocation(loc);
                    cafeteria[checkout4.get(p).getLocation().getRow()][checkout4.get(p).getLocation().getCol()] = checkout4.get(p);
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
         
        if (rest1 != null) {
            allQueLengths.add(rest1.size());
        }    
        if(rest2 != null) {
            allQueLengths.add(rest2.size());
        }
        if(rest3 != null) {
            allQueLengths.add(rest3.size());    
        }
        if(rest4 != null) {
            allQueLengths.add(rest4.size());
        }
        if(rest5 != null) {
            allQueLengths.add(rest5.size());
        }
        if(rest6 != null) {
            allQueLengths.add(rest6.size());
        }
        if(rest7 != null) {
            allQueLengths.add(rest7.size());
        }
        
        if(checkout1 != null) {
            allCheckQueLengths.add(checkout1.size());
        }
        if(checkout2 != null) {
            allCheckQueLengths.add(checkout2.size());
        }
        if(checkout3 != null) {
            allCheckQueLengths.add(checkout3.size());
        }
        if(checkout4 != null) {
            allCheckQueLengths.add(checkout4.size());
        }
        
        for(int i = 0; i < allQueLengths.size(); ++i) {
            if(allQueLengths.get(i) > maxRestLength) {
                maxRestLength = allQueLengths.get(i);
            }
        }
        for(int y = 0; y < allCheckQueLengths.size(); ++y) {
            if(allCheckQueLengths.get(y) > maxCheckLength) {
                maxCheckLength = allCheckQueLengths.get(y);
            }
        }
        
    }
    
    /**
     * Method that progresses the simulation
     */
    public void takeAction(){
        
        double currTime = getSimTimeLeft();
        LinkedList<Vehicle> eatHolder = null;
        LinkedList<Vehicle> checkHolder= null;
         
        
        //generate first Vehicle shortly into simulation
        if(currTime == totalTime - 500) {
            firstPer = true;
            addVehicle();
            timeVehicleAdded = currTime;
        }
        
        //generate another Vehicle at ever pTime seconds (or asap after if delay causes simulation to pass pTime)
        if((timeVehicleAdded - currTime) >= pTime) {
            addVehicle();
            timeVehicleAdded = currTime;
        }
        
        int VehicleNum = 1;
        for(int u = 0; u < allPeople.size(); ++u) { 
            
            Vehicle p = allPeople.get(u);
            
            if(p.getCreateTime() - currTime < p.getLeaveTime()) {
                if(isInCheckout(p) == false) {
                    eatHolder = p.getQue();
            
                    if(p.equals(eatHolder.get(0))) {
                        if(p.getBeginEatTime() == 0) {
                            p.setBeginEatTime(currTime);
                        }
                        if((p.getBeginEatTime() - currTime) >= p.getEateryTime()) {
                            cafeteria[p.getLocation().getRow()][p.getLocation().getCol()] = null;
                            eatHolder.remove(p);
                            selectCheckout(p);
                        }    
                    }   
                }    
                if(isInCheckout(p)) {
                    
                    checkHolder = p.getQue();
                    
                    if(p.equals(checkHolder.get(0))) {
                        if(p.getBeginCheckoutTime() == 0) {  
                            p.setBeginCheckoutTime(currTime);
                        }    
                        if((p.getBeginCheckoutTime() - currTime) >= p.getCashiersTime()) {
                            allAvgTimes.add(p.getCreateTime() - currTime);
                            allAvgCheckTimes.add(p.getBeginCheckoutTime() - currTime);
                            removeVehicle(p); 
                            ++finished;
                        }
                    }
                }    
            }        
            else {
                removeVehicle(p);  
            } 
            ++VehicleNum;
        }  
        checkLengths();
        createLines();
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
    public int getNumOfEateries() {
        return numOfEateries;
    }
    
    /**
     * Method to get number of checkouts
     * @return 
     */
    public int getNumOfCheckouts() {
        return numOfCheckouts;
    }
    
    /**
     * Method to get number of people
     * @return 
     */
    public int getNumOfPeople() {
        return numOfPeople;
    }
    
    /**
     * Method to get number of people that walked out
     * @return 
     */
    public int getPeopleLeft() {
        
        int perLeft = 0;
        
        if(rest1 != null) {
            perLeft = perLeft + rest1.size();
        }
        if(rest2 != null) {
            perLeft = perLeft + rest2.size();
        }
        if(rest3 != null) {
            perLeft = perLeft + rest3.size();
        }
        if(rest4 != null) {
            perLeft = perLeft + rest4.size();
        }
        if(rest5 != null) {
            perLeft = perLeft + rest5.size();
        }
        if(rest6 != null) {
            perLeft = perLeft + rest6.size();
        }
        if(rest7 != null) {
            perLeft = perLeft + rest7.size();
        }
        if(checkout1 != null) {
            perLeft = perLeft + checkout1.size();
        }
        if(checkout2 != null) {
            perLeft = perLeft + checkout2.size();
        }
        if(checkout3 != null) {
            perLeft = perLeft + checkout3.size();
        }
        if(checkout4 != null) {
            perLeft = perLeft + checkout4.size();
        }
        return perLeft;
    }
    
    /**
     * Metho to get max restaurant que length
     * @return 
     */
    public int getMaxRestLength() {
        return maxRestLength;
    }
    
    /**
     * Method to get max checkout que length
     * @return 
     */
    public int getMaxCheckLength() {
        return maxCheckLength;
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
        totalAvgVehicleTime = (sum / numOfPeople) / 1000;
        return totalAvgVehicleTime;
    }
    
    /**
     * Method to get average checkout time
     * @return 
     */
    public double getAvgCheckoutTime() {
        
        double sum = 0;
        
        for(int i = 0; i < allAvgCheckTimes.size(); ++i) {
            sum = sum + allAvgCheckTimes.get(i);    
        }  
        totalAvgCheckTime = (sum / numOfPeople) / 1000;
        return totalAvgCheckTime;
    }
    
    /**
     * Method to get most popular Vehicle type
     * @return 
     */
    public String getMostPop() {
    
        String mostPop = null;
        
        if(numOfRegPpl > numOfSpecPpl) {
            if(numOfRegPpl > numOfLimPpl) {
                mostPop = "Regular Vehicle";
            }
            if(numOfRegPpl == numOfLimPpl) {
                mostPop = "Regular and Limited Time People";
            }
            if(numOfRegPpl < numOfLimPpl){
                mostPop = "Limited Time Vehicle";
            }
        }
        if(numOfRegPpl == numOfSpecPpl) {
            if(numOfRegPpl < numOfLimPpl) {
                mostPop = "Limited Time Vehicle";
            }
            if(numOfRegPpl > numOfLimPpl) {
                mostPop = "Regular and SN People";
            }
        }
        if(numOfRegPpl < numOfSpecPpl) {
            if(numOfSpecPpl < numOfLimPpl) {
                mostPop = "Limited Time Vehicle";
            }
            if(numOfSpecPpl > numOfLimPpl) {
                mostPop = "Special Needs Vehicle";
            }
            if(numOfSpecPpl == numOfLimPpl) {
                mostPop = "Special Needs and LT People";
            }
        }
        return mostPop;
    }
    
    /**
     * Method to get least popular Vehicle type
     * @return 
     */
    public String getLeastPop() {
        
        String leastPop = null;
        
        if(numOfRegPpl > numOfSpecPpl) {
            if(numOfSpecPpl > numOfLimPpl) {
                leastPop = "Limited Time Vehicle";
            }
            if(numOfSpecPpl == numOfLimPpl) {
                leastPop = "Special and LT People";
            }
            if(numOfSpecPpl < numOfLimPpl){
                leastPop = "Special Needs Vehicle";
            }
        }
        if(numOfRegPpl == numOfSpecPpl) {
            if(numOfRegPpl > numOfLimPpl) {
                leastPop = "Limited Time Vehicle";
            }
            if(numOfRegPpl < numOfLimPpl) {
                leastPop = "Regular and SN People";
            }
        }
        if(numOfRegPpl < numOfSpecPpl) {
            if(numOfSpecPpl < numOfLimPpl) {
                leastPop = "Regular Vehicle";
            }
            if(numOfRegPpl == numOfLimPpl) {
                leastPop = "Regular and LT People";
            }
            if(numOfRegPpl > numOfLimPpl) {
                leastPop = "Limited Time Vehicle";
            }
        }
        return leastPop;    
    }

    /**
     * Method to paint the simulation
     * @param g 
     */ 
    public void paintComponent(Graphics g){
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLUMNS; col++){
                Vehicle p = cafeteria[row][col];
                
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
        if(numOfEateries == 1) {
            
            g.setColor(Color.RED);
            g.fillRect(350, 40, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest1", 365, 75);
            
            if(numOfCheckouts == 1) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
            }
            if(numOfCheckouts == 2) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
            }
            if(numOfCheckouts == 3) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
            }
            if(numOfCheckouts == 4) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 480, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check4", 936, 515);
            }
        }
        if(numOfEateries == 2) {
            
            g.setColor(Color.RED);
            g.fillRect(350, 40, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest1", 365, 75);
            g.setColor(Color.CYAN);
            g.fillRect(350, 140, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest2", 365, 175);
            
            if(numOfCheckouts == 1) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
            }
            if(numOfCheckouts == 2) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
            }
            if(numOfCheckouts == 3) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
            }
            if(numOfCheckouts == 4) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 480, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check4", 936, 515);
            }
        }
        if(numOfEateries == 3) {
            
            g.setColor(Color.RED);
            g.fillRect(350, 40, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest1", 365, 75);
            g.setColor(Color.CYAN);
            g.fillRect(350, 140, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest2", 365, 175);
            g.setColor(Color.GREEN);
            g.fillRect(350, 240, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest3", 365, 275);
            
            if(numOfCheckouts == 1) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
            }
            if(numOfCheckouts == 2) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
            }
            if(numOfCheckouts == 3) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
            }
            if(numOfCheckouts == 4) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 480, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check4", 936, 515);
            }
        }
        if(numOfEateries == 4) {
            
            g.setColor(Color.RED);
            g.fillRect(350, 40, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest1", 365, 75);
            g.setColor(Color.CYAN);
            g.fillRect(350, 140, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest2", 365, 175);
            g.setColor(Color.GREEN);
            g.fillRect(350, 240, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest3", 365, 275);
            g.setColor(Color.MAGENTA);
            g.fillRect(350, 340, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest4", 365, 375);
            
            if(numOfCheckouts == 1) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
            }
            if(numOfCheckouts == 2) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
            }
            if(numOfCheckouts == 3) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
            }
            if(numOfCheckouts == 4) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 480, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check4", 936, 515);
            }
        }
        if(numOfEateries == 5) {
            
            g.setColor(Color.RED);
            g.fillRect(350, 40, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest1", 365, 75);
            g.setColor(Color.CYAN);
            g.fillRect(350, 140, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest2", 365, 175);
            g.setColor(Color.GREEN);
            g.fillRect(350, 240, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest3", 365, 275);
            g.setColor(Color.MAGENTA);
            g.fillRect(350, 340, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest4", 365, 375);
            g.setColor(Color.BLUE);
            g.fillRect(350, 440, 65, 65);
            g.setColor(Color.BLACK);
            g.drawString("Rest5", 365, 475);
            
            if(numOfCheckouts == 1) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
            }
            if(numOfCheckouts == 2) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
            }
            if(numOfCheckouts == 3) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
            }
            if(numOfCheckouts == 4) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 180, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check1", 936, 215);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 280, 85, 65); 
                g.setColor(Color.BLACK);
                g.drawString("Check2", 936, 315);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 380, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check3", 936, 415);
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(915, 480, 85, 65);
                g.setColor(Color.BLACK);
                g.drawString("Check4", 936, 515);
            }
        }
    }
}
    
