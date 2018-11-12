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
 * @version 1.0 (September 2018)
 */
public class TrafficSimulationGUI extends JFrame implements ActionListener, Runnable {


    //Declaring instance variables
    private int DELAY = 20;
    private boolean isRunning;
    private boolean firstTimeStartPressed;
    private boolean loop = true;
    private double secsTillNextVehicle;
    private double totalTime;
    private double timeLeft;
    private double moveForwardTime;
    private double uCarTime;
    public Timer simTimer;
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
        try {
            TrafficSimulationGUI gui = new TrafficSimulationGUI();
            gui.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            gui.setTitle("Traffic Simulation");
            gui.setPreferredSize(new Dimension(1800, 1000));
            gui.pack();
            gui.setVisible(true);
            gui.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {

                    int dialogResult = JOptionPane.showConfirmDialog(gui,
                            "Closing window while simulation is running" +
                                    " will cause you to lose all simulation data. Proceed in closing?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Class constructor initializes instance variables
     */
    public TrafficSimulationGUI() {

        isRunning = false;
        firstTimeStartPressed = true;
        secsTillNextVehicle = 1000 * 5;
        uCarTime = 10200;
        moveForwardTime = 500;

        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();
        Font font = new Font("SansSerif Bold", Font.BOLD, 14);

        //Adding all panels to JFrame
        input = new JPanel(new GridBagLayout());
        input.setBorder(new EmptyBorder(30, 0, 30, 120));
        position = makeConstraints(10, 0, 1, 1, GridBagConstraints.LINE_END);
        add(input,position);

        statsArea = new JPanel(new GridBagLayout());
        statsArea.setBorder(new EmptyBorder(30, 200, 0, 120));
        position = makeConstraints(10, 5, 1, 1, GridBagConstraints.LINE_END);
        add(statsArea,position);

        trafficMap = new Simulation(secsTillNextVehicle, totalTime, moveForwardTime);
        trafficMap.setMinimumSize(trafficMap.getPreferredSize());
        position = makeConstraints(0, 0, 10, 10, GridBagConstraints.FIRST_LINE_START);
        add(trafficMap, position);

        //Adding input text fields and labels
        inputLabel = new JLabel("Input Information");
        inputLabel.setBorder(new EmptyBorder(10, 0, 30, 0));
        inputLabel.setFont(font);
        position = makeConstraints(2, 1, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(0, 120, 0, 20);
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
        position = makeConstraints(3, 3, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(0, -20, 0, 20);
        input.add(leaveTime, position);
        in1Lab = new JLabel("Commute departure time: ");
        in1Lab.setFont(font);
        position = makeConstraints(2, 3, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(0, 0, 0, 20);
        input.add(in1Lab, position);

        congestionLevel = new JComboBox<>(congestionOptions);
        congestionLevel.setMinimumSize(congestionLevel.getPreferredSize());
        position = makeConstraints(3, 4, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, -20, 0, 20);
        input.add(congestionLevel, position);
        in2Lab = new JLabel("Current level of road congestion: ");
        in2Lab.setFont(font);
        position = makeConstraints(2, 4, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, 0, 0, 20);
        input.add(in2Lab, position);

        weatherConditions = new JComboBox<>(weatherOptions);
        weatherConditions.setMinimumSize(weatherConditions.getPreferredSize());
        position = makeConstraints(3, 5, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, -20, 0, 20);
        input.add(weatherConditions, position);
        in3Lab = new JLabel("Current weather conditions: ");
        in3Lab.setFont(font);
        position = makeConstraints(2, 5, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, 0, 0, 20);
        input.add(in3Lab, position);


        //Adding stats to statsArea JPanel
        outputLabel = new JLabel("Output Information");
        font = new Font("SansSerif Bold", Font.BOLD, 14);
        outputLabel.setFont(font);
        position = makeConstraints(1, 0, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(0, -185, 0, 0);
        outputLabel.setBorder(new EmptyBorder(10, 0, 30, 0));
        statsArea.add(outputLabel, position);

        font = new Font("SansSerif Bold", Font.BOLD, 13);

        thru = new JLabel("Throughput:");
        thru.setFont(font);
        position = makeConstraints(0, 1, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(0, -110, 0, 20);
        statsArea.add(thru, position);
        out1 = new JLabel("TBD");
        out1 = new JLabel(trafficMap.getFinished() + " with max = 80");
        out1.setFont(font);
        position = makeConstraints(2, 1, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, 0, 0, 20);
        statsArea.add(out1, position);

        numLRun = new JLabel("Number of Lights Run");
        numLRun.setFont(font);
        position = makeConstraints(0, 2, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(0, -110, 0, 20);
        statsArea.add(numLRun, position);
        out2 = new JLabel("TBD");
        out2.setFont(font);
        position = makeConstraints(2, 2, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, 0, 0, 20);
        statsArea.add(out2, position);

        numOfAcc = new JLabel("Number of Accidents:");
        numOfAcc.setFont(font);
        position = makeConstraints(0, 3, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, -110, 0, 20);
        statsArea.add(numOfAcc, position);
        out3 = new JLabel("TBD");
        out3.setFont(font);
        position = makeConstraints(2, 3, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, 0, 0, 20);
        statsArea.add(out3, position);

        avgTimStop = new JLabel("Average Vehicle Time Stopped:");
        avgTimStop.setFont(font);
        position = makeConstraints(0, 4, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, -110, 0, 20);
        statsArea.add(avgTimStop, position);
        out4 = new JLabel("TBD");
        out4.setFont(font);
        position = makeConstraints(2, 4, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, 0, 0, 20);
        statsArea.add(out4, position);

        avgVSpeed = new JLabel("Average Vehicle Speed:");
        avgVSpeed.setFont(font);
        position = makeConstraints(0, 5, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, -110, 0, 20);
        statsArea.add(avgVSpeed, position);
        out5 = new JLabel("TBD");
        out5.setFont(font);
        position = makeConstraints(2, 5, 1, 1, GridBagConstraints.LINE_START);
        position.insets = new Insets(10, 0, 0, 20);
        statsArea.add(out5, position);

        userStarFin = new JLabel("Your Route Completion Time:");
        userStarFin.setFont(font);
        position = makeConstraints(0, 6, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, -110, 0, 20);
        statsArea.add(userStarFin, position);
        out6 = new JLabel("TBD");
        out6.setFont(font);
        position = makeConstraints(2, 6, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, 0, 0, 20);
        statsArea.add(out6, position);

        avgStarFin = new JLabel("Average Vehicle Route Completion Time:");
        avgStarFin.setFont(font);
        position = makeConstraints(0, 7, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, -110, 0, 20);
        statsArea.add(avgStarFin, position);
        out7 = new JLabel("TBD");
        out7.setFont(font);
        position = makeConstraints(2, 7, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(10, 0, 0, 20);
        statsArea.add(out7, position);


        //place each button
        start = new JButton( "Start" );
        start.setForeground(Color.GREEN);
        position = makeConstraints(3, 7, 1, 1, GridBagConstraints.LINE_START);
        position.insets =  new Insets(40, -170, 0, 20);
        input.add(start, position);

        stop = new JButton( "Stop" );
        stop.setForeground(Color.RED);
        position = makeConstraints(4,8,1,1,GridBagConstraints.LINE_START);
        position.insets =  new Insets(-26,-120,0,20);
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
            trafficMap.reset();
            firstTimeStartPressed = true;
        }

        //set route congestion level based on user input
        if (e.getSource() == congestionLevel) {
            isRunning = false;
            if (congestionLevel.getSelectedItem().toString() == "Low") {
                secsTillNextVehicle = 1000 * 5;
            }
            if (congestionLevel.getSelectedItem().toString() == "Medium") {
                secsTillNextVehicle = 1000 * 3;
            }
            if (congestionLevel.getSelectedItem().toString() == "High") {
                secsTillNextVehicle = 1000 * 2;
            }
            if (congestionLevel.getSelectedItem().toString() == "Rush Hour") {
                secsTillNextVehicle = 1000 * 1;
            }
        }

        //set weather condition variables based on user input
        if (e.getSource() == weatherConditions) {
            isRunning = false;
            switch (weatherConditions.getSelectedItem().toString()) {
                case "Clear Day":
                    moveForwardTime = 500;
                    break;
                case "Light Rain":
                    moveForwardTime = 700;
                    break;
                case "Light Snow":
                    moveForwardTime = 700;
                    break;
                case "Heavy Rain":
                    moveForwardTime = 800;
                    break;
                case "Heavy Snow":
                    moveForwardTime = 1000;
                    break;
                case "Fog":
                    moveForwardTime = 700;
                    break;
                default:
                    moveForwardTime = 500;
                    break;
            }
        }

            //set where user's car generates based on input
            if (e.getSource() == leaveTime) {
                isRunning = false;
                if (leaveTime.getSelectedItem().toString().equals("Left Early")) {
                    uCarTime = 900;
                }
                if (leaveTime.getSelectedItem().toString().equals("On Time")) {
                    uCarTime = 10200;
                }
                if (leaveTime.getSelectedItem().toString().equals("Left Late")) {
                    uCarTime = 25200;
                }
            }

            //update GUI
            trafficMap.repaint();
    }
    /**
     * Method to update stats in the GUI
     */
    public void updateGUI() {

        //Will up actively as simulation runs
        out1.setText(trafficMap.getFinished() + " with max = 80");
        out2.setText(String.valueOf(trafficMap.getNumLightsRun()));
        out3.setText(String.valueOf(trafficMap.getNumOfAccidents()));
        out4.setText(df.format(trafficMap.getAvgStoppedTime()) + " seconds");
        out5.setText(df.format(trafficMap.getAvgVehicleSpeed()) + " mph");
        out6.setText(df.format(trafficMap.getUserThruTime()) + " seconds");
        out7.setText(df.format(trafficMap.getTotalAvgVehicleTime()) + " seconds");

        //WILL NEED TO UPDATE LIGHTS RUN AND ACCIDENTS HERE ONLY (out2 nad out3)
    }

    /**
     * Run method called by the thread
     */
    public void run() {
        try {

             totalTime = 1000 * 69; //set 50 seconds simulation run time (for now)
             trafficMap.setSecsTillNextVehicle(secsTillNextVehicle);
             trafficMap.setTotalTime(totalTime);
             trafficMap.setTimeForUserCar(uCarTime);
             trafficMap.setLTime(moveForwardTime);
            trafficMap.setVTime(secsTillNextVehicle*0.1*r.nextGaussian() + secsTillNextVehicle);

            timeLeft = totalTime;

            simTimer = new Timer(DELAY,new ActionListener() {
                public void actionPerformed(ActionEvent evt) {

                    trafficMap.setSimTimeLeft(timeLeft);
                    trafficMap.takeAction();

                    timeLeft = timeLeft - DELAY;

                    if (timeLeft <= 0) {
                        simTimer.stop();
                        updateGUI();
                        isRunning = false;
                        JOptionPane.showMessageDialog(null, "Simulation Over");
                    }
                     else {
                         updateGUI();
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
    private GridBagConstraints makeConstraints(int x, int y, int h, int w, int align) {
        GridBagConstraints rtn = new GridBagConstraints();
        rtn.gridx = x;
        rtn.gridy = y;
        rtn.gridheight = h;
        rtn.gridwidth = w;

        rtn.anchor = align;
        return rtn;
    }
}
