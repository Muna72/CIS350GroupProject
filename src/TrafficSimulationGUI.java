import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.border.*;
/**
 *
 * @author Muna Gigowski
 * @version September 2018
 */
public class TrafficSimulation extends JFrame implements ActionListener, Runnable{


    //Declaring instance variables
    private final int DELAY = 20;
    private boolean isRunning;
    private boolean firstTimeStartPressed;
    private boolean loop = true;
    private double secsTillNextVehicle;
    private double avgIntersectionWaitTime;
    private double totalTime;
    private double timeLeft;
    private double pTime;
    private double cTime;
    private double eTime;
    private double avgEatSec;
    private double secBeforePersonLeaves;
    private int numOfEateries;
    public Timer simTimer;
    private Location startLoc;
    private Random r = new Random();
    DecimalFormat df = new DecimalFormat("#.00");
    private JPanel input;
    Simulation trafficMap;
    private JPanel simPanel;
    private JPanel statsArea;
    JPanel buttons;

    //define buttons
    JButton start;
    JButton stop;
    JButton pause;

    //define JTextFields
    JTextField in1;
    JTextField in2;
    JTextField in3;
    JTextField in4;
    JTextField in5;
    JTextField in6;

    //define JLabels
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JLabel in1Lab;
    private JLabel in2Lab;
    private JLabel in3Lab;
    private JLabel in4Lab;
    private JLabel in5Lab;
    private JLabel in6Lab;
    private JLabel thru;
    private JLabel avgStarFin;
    private JLabel avgCheckTime;
    private JLabel numPplLeft;
    private JLabel maxQLength;
    private JLabel maxQRestLength;
    private JLabel mostPopPerson;
    private JLabel leastPopPerson;
    private JLabel out1;
    private JLabel out2;
    private JLabel out3;
    private JLabel out4;
    private JLabel out5;
    private JLabel out6;
    private JLabel out7;
    private JLabel out8;


    //define menu items
    private JMenuBar menu;
    JMenu file;
    JMenuItem reset;
    JMenuItem quit;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        try{
            TrafficSimulation gui = new TrafficSimulation();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setTitle("Food Court Simulation");
            gui.setSize(1200,1200);
            gui.pack();
            gui.setVisible(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Class constructor initializes instance variables
     */
    public TrafficSimulation(){

        isRunning = false;
        firstTimeStartPressed = true;

        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();
        Font font = new Font("SansSerif Bold", Font.BOLD, 14);

        //Adding all panels to JFrame
        input = new JPanel(new GridBagLayout());
        input.setBorder(new EmptyBorder(30, 0, 30, 0));
        position = makeConstraints(10,0,1,1,GridBagConstraints.LINE_END);
        add(input,position);

        buttons = new JPanel(new GridBagLayout());
        buttons.setBorder(new EmptyBorder(10, 70, 30, 40));
        position = makeConstraints(10,4,1,1,GridBagConstraints.LINE_END);
        position.anchor = GridBagConstraints.LINE_END;
        add(buttons,position);

        statsArea = new JPanel(new GridBagLayout());
        statsArea.setBorder(new EmptyBorder(30, 200, 0, 120));
        position = makeConstraints(10,5,1,1,GridBagConstraints.LINE_END);
        add(statsArea,position);

        trafficMap = new Simulation(secsTillNextVehicle, avgIntersectionWaitTime,totalTime,avgEatSec,secBeforePersonLeaves,numOfEateries);
        trafficMap.setMinimumSize(trafficMap.getPreferredSize());
        position = makeConstraints(0,0,10,10,GridBagConstraints.FIRST_LINE_START);
        add(trafficMap, position);

        //Adding input text fields and labels
        inputLabel = new JLabel("Input Information");
        inputLabel.setBorder(new EmptyBorder(10, 0, 30, 0));
        inputLabel.setFont(font);
        position = makeConstraints(4,1,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,-35,0,20);
        input.add(inputLabel, position);

        font = new Font("SansSerif Bold", Font.BOLD, 13);

        in1 = new JTextField(8);
        in1.setMinimumSize(in1.getPreferredSize());
        position = makeConstraints(3,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,-20,0,20);
        input.add(in1, position);
        in1Lab = new JLabel("Average Seconds Until Next Person: ");
        in1Lab.setFont(font);
        position = makeConstraints(2,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,0,0,20);
        input.add(in1Lab, position);

        in2 = new JTextField(8);
        in2.setMinimumSize(in2.getPreferredSize());
        position = makeConstraints(3,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-20,0,20);
        input.add(in2, position);
        in2Lab = new JLabel("Average Seconds Per Cashier: ");
        in2Lab.setFont(font);
        position = makeConstraints(2,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        input.add(in2Lab, position);

        in3 = new JTextField(8);
        in3.setMinimumSize(in3.getPreferredSize());
        position = makeConstraints(3,5,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-20,0,20);
        input.add(in3, position);
        in3Lab = new JLabel("Total Time In Seconds: ");
        in3Lab.setFont(font);
        position = makeConstraints(2,5,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        input.add(in3Lab, position);

        in4 = new JTextField(8);
        in4.setMinimumSize(in4.getPreferredSize());
        position = makeConstraints(5,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(-5,-20,0,20);
        input.add(in4, position);
        in4Lab = new JLabel("Average Seconds Per Eatery: ");
        in4Lab.setFont(font);
        position = makeConstraints(4,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(-5,-5,0,20);
        input.add(in4Lab, position);

        in5 = new JTextField(8);
        in5.setMinimumSize(in5.getPreferredSize());
        position = makeConstraints(5,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(5,-20,0,20);
        input.add(in5, position);
        in5Lab = new JLabel("Average Seconds Before Person Leaves: ");
        in5Lab.setFont(font);
        position = makeConstraints(4,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(7,-5,0,20);
        input.add(in5Lab, position);

        in6 = new JTextField(8);
        in6.setMinimumSize(in6.getPreferredSize());
        position = makeConstraints(5,5,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(5,-20,0,20);
        input.add(in6, position);
        in6Lab = new JLabel("Number Of Eateries: ");
        in6Lab.setFont(font);
        position = makeConstraints(4,5,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-5,0,20);
        input.add(in6Lab, position);


        //Adding stats to statsArea JPanel
        outputLabel = new JLabel("Output Information");
        font = new Font("SansSerif Bold", Font.BOLD, 14);
        outputLabel.setFont(font);
        position = makeConstraints(1,0,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,-165,0,0);
        outputLabel.setBorder(new EmptyBorder(10, 0, 30, 0));
        statsArea.add(outputLabel, position);

        font = new Font("SansSerif Bold", Font.BOLD, 13);

        thru = new JLabel("Throughput:");
        thru.setFont(font);
        position = makeConstraints(0,1,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,-110,0,20);
        statsArea.add(thru, position);
        out1 = new JLabel(trafficMap.getFinished() + " with max = 500");
        out1.setFont(font);
        position = makeConstraints(2,1,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out1, position);

        avgStarFin = new JLabel("Average Time for a Person from Start to Finish:");
        avgStarFin.setFont(font);
        position = makeConstraints(0,2,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,-110,0,20);
        statsArea.add(avgStarFin, position);
        out2 = new JLabel("TBD");
        out2.setFont(font);
        position = makeConstraints(2,2,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out2, position);

        avgCheckTime = new JLabel("Average Checkout Time:");
        avgCheckTime.setFont(font);
        position = makeConstraints(0,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(avgCheckTime, position);
        out3 = new JLabel("TBD");
        out3.setFont(font);
        position = makeConstraints(2,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out3, position);

        numPplLeft = new JLabel("Number of People Left in Line:");
        numPplLeft.setFont(font);
        position = makeConstraints(0,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(numPplLeft, position);
        out4 = new JLabel("TBD");
        out4.setFont(font);
        position = makeConstraints(2,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out4, position);

        maxQLength = new JLabel("Max Q Length at Cashier:");
        maxQLength.setFont(font);
        position = makeConstraints(0,5,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(maxQLength, position);
        out5 = new JLabel("TBD");
        out5.setFont(font);
        position = makeConstraints(2,5,1,1,GridBagConstraints.LINE_START);
        position.insets = new Insets(10,0,0,20);
        statsArea.add(out5, position);

        maxQRestLength = new JLabel("Max Q Length at Restaraunt:");
        maxQRestLength.setFont(font);
        position = makeConstraints(0,6,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(maxQRestLength, position);
        out6 = new JLabel("TBD");
        out6.setFont(font);
        position = makeConstraints(2,6,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out6, position);

        mostPopPerson = new JLabel("Most Frequent Customer Type:");
        mostPopPerson.setFont(font);
        position = makeConstraints(0,7,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(mostPopPerson, position);
        out7 = new JLabel("TBD");
        out7.setFont(font);
        position = makeConstraints(2,7,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out7, position);

        leastPopPerson = new JLabel("Least Frequent Customer Type:");
        leastPopPerson.setFont(font);
        position = makeConstraints(0,8,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(leastPopPerson, position);
        out8 = new JLabel("TBD");
        out8.setFont(font);
        position = makeConstraints(2,8,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out8, position);



        //place each button
        start = new JButton( "Start" );
        start.setForeground(Color.GREEN);
        position = makeConstraints(3,7,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(40,-20,0,20);
        input.add(start, position);

        stop = new JButton( "Stop" );
        stop.setForeground(Color.RED);
        position = makeConstraints(5,8,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(-26,-180,0,20);
        input.add(stop, position);

        pause = new JButton( "Add Eatery" );
        pause.setForeground(Color.ORANGE);
        position = makeConstraints(0,0,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,-70,0,0);
        buttons.add(pause, position);

        //create and add menu items
        menu = new JMenuBar();
        file = new JMenu("File");
        quit = new JMenuItem("Quit");
        reset = new JMenuItem("Clear");
        menu.add(file);
        file.add(quit);
        file.add(reset);
        setJMenuBar(menu);

        //add all action listeners
        start.addActionListener(this);
        stop.addActionListener(this);
        pause.addActionListener(this);
        file.addActionListener(this);
        quit.addActionListener(this);
        reset.addActionListener(this);
    }

    /**
     * Action performed method
     * @param e
     */
    public void actionPerformed(ActionEvent e){

        //exit application if QUIT menu item
        if (e.getSource() == quit){
            System.exit(1);
        }

        //set running variable to true if START button
        if (e.getSource() == start){
            if (firstTimeStartPressed) {
                isRunning = true;
                new Thread(this).start();
                firstTimeStartPressed = false;
            }
            else {
                simTimer.start();
            }
        }


        //set running variable to false if STOP button
        if (e.getSource() == stop){
            isRunning = false;
            simTimer.stop();
        }

        //reset simulation if RESET menu item
        if (e.getSource() == reset){
            trafficMap.reset();
            firstTimeStartPressed = true;
        }

        //Add eatery
        if(e.getSource() == pause){

            if(trafficMap.getNumOfEateries() < 7) {
                trafficMap.addIntersection();
            }
            else {
                JOptionPane.showMessageDialog(null, "Cannot add any more eateries.");
            }
        }
        //update GUI
        trafficMap.repaint();
    }

    /**
     * Method to update stats in the GUI
     */
    public void updateGUI() {

        out1.setText(trafficMap.getFinished() + " with max = 500");
        out2.setText(df.format(trafficMap.getTotalAvgPersonTime()) + " seconds");
        out3.setText(df.format(trafficMap.getAvgCheckoutTime()) + " seconds");
        out4.setText(trafficMap.getPeopleLeft() + " people");
        out5.setText(trafficMap.getMaxCheckLength() + " people");
        out6.setText(trafficMap.getMaxRestLength() + " people");
        out7.setText(trafficMap.getMostPop());
        out8.setText(trafficMap.getLeastPop());
    }

    /**
     * Run method called by the thread
     */
    public void run(){
        try {

            secsTillNextVehicle = 1000 * Double.parseDouble(in1.getText());
            avgIntersectionWaitTime = 1000 * Double.parseDouble(in2.getText());
            totalTime = 1000 * Double.parseDouble(in3.getText());
            avgEatSec = 1000 * Double.parseDouble(in4.getText());
            secBeforePersonLeaves = 1000 * Double.parseDouble(in5.getText());
            numOfEateries = Integer.parseInt(in6.getText());

            trafficMap.setsecsTillNextVehicle(secsTillNextVehicle);
            trafficMap.setavgIntersectionWaitTime(avgIntersectionWaitTime);
            trafficMap.setTotalTime(totalTime);
            trafficMap.setAvgEatSec(avgEatSec);
            trafficMap.setSecBeforePersonLeaves(secBeforePersonLeaves);
            trafficMap.setNumOfEateries(numOfEateries);
            trafficMap.setPTime(secsTillNextVehicle*0.1*r.nextGaussian() + secsTillNextVehicle);

            timeLeft = totalTime;

            simTimer = new Timer(DELAY,new ActionListener() {
                public void actionPerformed(ActionEvent evt) {

                    trafficMap.setSimTimeLeft(timeLeft);
                    trafficMap.takeAction();

                    timeLeft = timeLeft - DELAY;

                    if(timeLeft <= 0) {
                        simTimer.stop();
                        updateGUI();
                        isRunning = false;
                        JOptionPane.showMessageDialog(null, "Simulation Over");
                    }
                }
            });
            simTimer.start();

            while(loop) {

                //update simulation if it is running
                if (isRunning) {


                }
                // pause between steps so it isn't too fast
                Thread.sleep(DELAY);
            }
        }
        catch (InterruptedException ex) {
        }
    }

    /**
     * Method to set contraints for gridbag layout
     * @param x
     * @param y
     * @param h
     * @param w
     * @param align
     * @return
     */
    private GridBagConstraints makeConstraints(int x, int y, int h, int w, int align){
        GridBagConstraints rtn = new GridBagConstraints();
        rtn.gridx = x;
        rtn.gridy = y;
        rtn.gridheight = h;
        rtn.gridwidth = w;

        rtn.anchor = align;
        return rtn;
    }
}
