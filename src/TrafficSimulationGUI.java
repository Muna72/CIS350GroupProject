import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.border.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 *
 * @author Muna Gigowski
 * @version September 2018
 */
public class TrafficSimulationGUI extends JFrame implements ActionListener, Runnable{


    //Declaring instance variables
    private int DELAY = 20;
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
    private int numOfIntersections;
    public Timer simTimer;
    //private Location startLoc;
    private Random r = new Random();
    DecimalFormat df = new DecimalFormat("#.00");
    private JPanel input;
    //Simulation trafficMap;
    JPanel trafficMap;
    private JPanel simPanel;
    private JPanel statsArea;
    JPanel buttons;

    //define buttons
    JButton start;
    JButton stop;
    JButton pause;

    //define JComboBoxes
    JComboBox<String> congestionLevel;
    JComboBox<String> weatherConditions;
    JComboBox<String> leaveTime;

    //define JLabels
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JLabel in1Lab;
    private JLabel in2Lab;
    private JLabel in3Lab;
    private JLabel in4Lab;
    private JLabel in5Lab;
    private JLabel thru;
    private JLabel avgStarFin;
    private JLabel userStarFin;
    private JLabel avgTimStop;
    private JLabel numOfAcc;
    private JLabel avgVSpeed;
    private JLabel numLRun;
    private JLabel out1;
    private JLabel out2;
    private JLabel out3;
    private JLabel out4;
    private JLabel out5;
    private JLabel out6;
    private JLabel out7;

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
            TrafficSimulationGUI gui = new TrafficSimulationGUI();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setTitle("Food Court Simulation");
            gui.setSize(1200,1200);
            gui.pack();
            gui.setVisible(true);
            gui.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    if(JOptionPane.showConfirmDialog(gui,
                            "Closing window while simulation is running" +
                                    " will cause you to lose all simulation data. Proceed in closing?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        System.exit(0);
                    }
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Class constructor initializes instance variables
     */
    public TrafficSimulationGUI(){

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

        // trafficMap = new Simulation(secsTillNextVehicle, avgIntersectionWaitTime,totalTime,avgEatSec, 4); //TODO ADD TO THIS
        trafficMap = new JPanel();
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

        String[] congestionOptions = new String[] {"Low", "Medium",
                "High", "Rush Hour"};
        String[] weatherOptions = new String[] {"Clear Day", "Light Rain",
                "Light Snow", "Heavy Rain", "Heavy Snow", "Fog"};
        String[] leaveTimes = new String[] {"Left Early", "On Time",
                "Left Late"};

        leaveTime = new JComboBox<>(leaveTimes);
        leaveTime.setMinimumSize(leaveTime.getPreferredSize());
        position = makeConstraints(3,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,-20,0,20);
        input.add(leaveTime, position);
        in1Lab = new JLabel("Commute departure time: ");
        in1Lab.setFont(font);
        position = makeConstraints(2,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,0,0,20);
        input.add(in1Lab, position);

        congestionLevel = new JComboBox<>(congestionOptions);
        congestionLevel.setMinimumSize(congestionLevel.getPreferredSize());
        position = makeConstraints(3,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-20,0,20);
        input.add(congestionLevel, position);
        in2Lab = new JLabel("Current level of road congestion: ");
        in2Lab.setFont(font);
        position = makeConstraints(2,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        input.add(in2Lab, position);

        weatherConditions = new JComboBox<>(weatherOptions);
        weatherConditions.setMinimumSize(weatherConditions.getPreferredSize());
        position = makeConstraints(3,5,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-20,0,20);
        input.add(weatherConditions, position);
        in3Lab = new JLabel("Current weather conditions: ");
        in3Lab.setFont(font);
        position = makeConstraints(2,5,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        input.add(in3Lab, position);

        /* in4 = new JTextField(8);
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
        input.add(in5Lab, position); */


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
        out1 = new JLabel("TBD");
        //out1 = new JLabel(trafficMap.getFinished() + " with max = 500");
        out1.setFont(font);
        position = makeConstraints(2,1,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out1, position);

        numLRun = new JLabel("Number of Lights Run");
        numLRun.setFont(font);
        position = makeConstraints(0,2,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(0,-110,0,20);
        statsArea.add(numLRun, position);
        out2 = new JLabel("TBD");
        out2.setFont(font);
        position = makeConstraints(2,2,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out2, position);

        numOfAcc = new JLabel("Number of Accidents:");
        numOfAcc.setFont(font);
        position = makeConstraints(0,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(numOfAcc, position);
        out3 = new JLabel("TBD");
        out3.setFont(font);
        position = makeConstraints(2,3,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out3, position);

        avgTimStop = new JLabel("Average Vehicle Time Stopped:");
        avgTimStop.setFont(font);
        position = makeConstraints(0,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(avgTimStop, position);
        out4 = new JLabel("TBD");
        out4.setFont(font);
        position = makeConstraints(2,4,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out4, position);

        avgVSpeed = new JLabel("Average Vehicle Speed:");
        avgVSpeed.setFont(font);
        position = makeConstraints(0,5,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(avgVSpeed, position);
        out5 = new JLabel("TBD");
        out5.setFont(font);
        position = makeConstraints(2,5,1,1,GridBagConstraints.LINE_START);
        position.insets = new Insets(10,0,0,20);
        statsArea.add(out5, position);

        userStarFin = new JLabel("Your Route Completion Time:");
        userStarFin.setFont(font);
        position = makeConstraints(0,6,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(userStarFin, position);
        out6 = new JLabel("TBD");
        out6.setFont(font);
        position = makeConstraints(2,6,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out6, position);

        avgStarFin = new JLabel("Average Vehicle Route Completion Time:");
        avgStarFin.setFont(font);
        position = makeConstraints(0,7,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,-110,0,20);
        statsArea.add(avgStarFin, position);
        out7 = new JLabel("TBD");
        out7.setFont(font);
        position = makeConstraints(2,7,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(10,0,0,20);
        statsArea.add(out7, position);


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
        congestionLevel.addActionListener(this);
        weatherConditions.addActionListener(this);
        leaveTime.addActionListener(this);
        start.addActionListener(this);
        stop.addActionListener(this);
        file.addActionListener(this);
        quit.addActionListener(this);
        reset.addActionListener(this);
    }

    /**
     * Action performed method
     * @param e
     */
    public void actionPerformed(ActionEvent e) {

        //set route congestion level based on user input
        if (e.getSource() == congestionLevel) {
            isRunning = false;
            if (congestionLevel.getSelectedItem() == "Low") {
                secsTillNextVehicle = 5;
            }
            if (congestionLevel.getSelectedItem() == "Medium") {
                secsTillNextVehicle = 3;
            }
            if (congestionLevel.getSelectedItem() == "High") {
                secsTillNextVehicle = 2;
            }
            if (congestionLevel.getSelectedItem() == "Rush Hour") {
                secsTillNextVehicle = 1;
            }
        }

        //set weather condition variables based on user input
        if (e.getSource() == weatherConditions) {
            isRunning = false;

            switch (weatherConditions.getSelectedItem().toString()) {
                case "Clear Day":
                    DELAY = 20;
                    break;
                case "Light Rain":
                    DELAY = 18;
                    System.out.print(DELAY);
                    break;
                case "Light Snow":
                    DELAY = 18;
                    break;
                case "Heavy Rain":
                    DELAY = 17;
                    break;
                case "Heavy Snow":
                    DELAY = 15;
                    break;
                case "Fog":
                    DELAY = 18;
                    break;
                default:
                    DELAY = 20;
                    break;
            }

            //set where user's car generates based on input
            if (e.getSource() == leaveTime) {
                isRunning = false;
                if (leaveTime.getSelectedItem() == "Left Early") {
                    //  trafficMap.placeUserCar("front");
                }
                if (leaveTime.getSelectedItem() == "On Time") {
                    //trafficMap.placeUserCar("middle");
                }
                if (leaveTime.getSelectedItem() == "Left Late") {
                    //trafficMap.placeUserCar("back");
                }
            }

            //exit application if QUIT menu item
            if (e.getSource() == quit) {
                System.exit(1);
            }

            //set running variable to true if START button
            if (e.getSource() == start) {
                if (firstTimeStartPressed) {
                    isRunning = true;
                    new Thread(this).start();
                    firstTimeStartPressed = false;
                } else {
                    simTimer.start();
                }
            }


            //set running variable to false if STOP button
            if (e.getSource() == stop) {
                isRunning = false;
                simTimer.stop();
            }

            //reset simulation if RESET menu item
            if (e.getSource() == reset) {
                // trafficMap.reset();
                firstTimeStartPressed = true;
            }

            //update GUI
            trafficMap.repaint();
        }
    }
    /**
     * Method to update stats in the GUI
     */
    public void updateGUI() {

        // out1.setText(trafficMap.getFinished() + " with max = 500");
        //out3.setText(df.format(trafficMap.getAvgStoppedTime()) + " seconds"); //ONLY ONES THAT ARE CALCULATED HERE?
        //out4.setText(df.format(trafficMap.getTotalAvgVehicleTime()) + " seconds");
        // out5.setText(trafficMap.getMaxCheckLength() + " people");
        //out6.setText(trafficMap.getMaxLaneLength() + " people");

        //WILL NEED TO UPDATE LIGHTS RUN AND ACCIDENTS HERE ONLY
    }

    /**
     * Run method called by the thread
     */
    public void run(){
        try {

            // secsTillNextVehicle = 1000 * Double.parseDouble(in1.getText());
            //avgIntersectionWaitTime = 1000 * Double.parseDouble(in2.getText());
            //totalTime = 1000 * Double.parseDouble(in3.getText());
            //avgEatSec = 1000 * Double.parseDouble(in4.getText());
            //numOfIntersections = Integer.parseInt(in6.getText());

            // trafficMap.setSecsTillNextVehicle(secsTillNextVehicle);
            //trafficMap.calculateAvgTimeStopped();
            //trafficMap.calculateAvgVehicleThruTime();
            //trafficMap.calculateUserVehicleThurTime();
            //trafficMap.setTotalTime(totalTime);
            //trafficMap.setNumOfIntersections(numOfIntersections);
            //trafficMap.setPTime(secsTillNextVehicle*0.1*r.nextGaussian() + secsTillNextVehicle);

            timeLeft = totalTime;

            simTimer = new Timer(DELAY,new ActionListener() {
                public void actionPerformed(ActionEvent evt) {

                    //  trafficMap.setSimTimeLeft(timeLeft);
                    // trafficMap.takeAction();

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
