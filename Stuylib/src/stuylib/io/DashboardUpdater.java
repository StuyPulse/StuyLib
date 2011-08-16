/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stuylib.io;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles the transmission of user-defined data to a PC running LabVIEW dashboard software.
 * Important: All instances of "Object" in this class must be replaced by the name of the main robot class in order to access sensor data.
 * 
 * @author Kevin Wang
 */
public class DashboardUpdater {
    // Module numbers
    private final int ANALOG_MODULE_1 = 1;
    private final int ANALOG_MODULE_2 = 2;
    private final int DIGITAL_MODULE_1 = 4;
    private final int DIGITAL_MODULE_2 = 6;
  
    // Enhanced I/O Stuff. Joystick values will probably change?
    private final int LEFT_STICK_PORT = 1;
    private final int RIGHT_STICK_PORT = 2;
    private final int EXTRA_STICK_1_PORT = 3;
    private final int EXTRA_STICK_2_PORT = 4;
    // Amount of buttons on each joystick. 11 is using the assumption of Attack 3's.
    private final int LEFT_STICK_BUTTONS = 11;
    private final int RIGHT_STICK_BUTTONS = 11;
    private final int EXTRA_STICK_1_BUTTONS = 11;
    private final int EXTRA_STICK_2_BUTTONS = 11;
    
    // Quantities of each variable type
    private final int NUM_BOOLEANS = 20;
    private final int NUM_DOUBLES = 20;
    private final int NUM_INTS = 5;
    private final int NUM_STRINGS = 5;
    
    // Period at which to update the dashboard
    private final int UPDATE_PERIOD_MS = 100;
    
    // Buffer to store data to be sent to the dashboard
    private boolean[] booleans = new boolean[NUM_BOOLEANS];
    private double[] doubles = new double[NUM_DOUBLES];
    private int[] ints = new int[NUM_INTS];
    private String[] strings = new String[NUM_STRINGS];
    
    // Buffer indices for auto-incrementation
    private int booleansIndex, doublesIndex, intsIndex, stringsIndex;
    
    // Reference to the main robot object
    private Object robot; // "Object" MUST be replaced by the main robot class
    
    /**
     * Initializes the buffer and starts a timer to update the dashboard every 0.1 seconds.
     * 
     * @param robot Reference to the main robot object
     */
    public DashboardUpdater(Object robot) { // "Object" MUST be replaced by the main robot class
        this.robot = robot;
        
        Timer dashTimer = new Timer();
        dashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                resetBuffer();
                defineDashboardDatatype();
                commit();
            }
        }, 0, UPDATE_PERIOD_MS);
    }
    
    /**
     * Writes data to the buffer to be committed to the dashboard using commit().
     * This is where changes should be made to specify what data should be sent to the dashboard.
     */
    private void defineDashboardDatatype() {
//        addBoolean(true);
//        addDouble(6.94);
//        addInt(694);
//        addString("Stuypulse");
    }
    
    /**
     * Commits the contents of the buffer to the dashboard, and then resets the buffer.
     * Note that any changes to the dashboard packet structure made here must
     * also be reflected in the dashboard's LabVIEW code.
     */
    private void commit() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        
        // Sensor data
        lowDashData.addCluster();
        {
            // Analog Modules
            lowDashData.addCluster();
            {
                // Iterates through Analogue Ports (on the first Analog Module) and sends data from each to the Dashboard.
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(ANALOG_MODULE_1).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
                // Iterates through Analoge Ports (on the second Analog Module) and sends data from each to the Dashboard.
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(ANALOG_MODULE_2).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

            // Digital Modules
            lowDashData.addCluster();
            {  // Digital Module 1
                lowDashData.addCluster();
                {   // Sends relays and Digital I/O to Dashboard.
                    int module = DIGITAL_MODULE_1;
                    lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                    lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                    lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                    lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                    lowDashData.addCluster();
                    {   // Iterates through PWM ports and sends data to dashboard.
                        for (int i = 1; i <= 10; i++) {
                        lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                    }
                  }
                  lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

                // Digital Module 2
                lowDashData.addCluster();
                {   // Sends relays and Digital I/O to Dashboard.
                    int module = DIGITAL_MODULE_2;
                    lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                    lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                    lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                    lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                    lowDashData.addCluster();
                    {   // Iterates through PWM ports and sends data to dashboard.
                        for (int i = 1; i <= 10; i++) {
                        lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                    }
                  }
                  lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

            }
            lowDashData.finalizeCluster();

            // Not really sure what this byte is for, but it was there with DESDroid...
            lowDashData.addByte((byte) 0);
        }
        lowDashData.finalizeCluster();
        
        
        // Enhanced I/O data
        lowDashData.addCluster();
        {
          // Joysticks
          lowDashData.addCluster();
          { // Left Joystick
            lowDashData.addCluster();
            { // Joystick X and Y Axes
              lowDashData.addCluster();
              {
                lowDashData.addDouble(Joystick(LEFT_STICK_PORT).getX());
                lowDashData.addDouble(Joystick(LEFT_STICK_PORT).getY());
              }
              lowDashData.finalizeCluster();
              // Adds Joystick Buttons
              lowDashData.addCluster();
              {
                for (int i = 1; i <= LEFT_STICK_BUTTONS; i++) {
                        lowDashData.addBoolean(Joystick(LEFT_STICK_PORT).getRawButton(i));
                    }
              }
              lowDashData.finalizeCluster();
            }
            lowDastData.finalizeCluster();
            
            // Right Joystick
            lowDashData.addCluster();
            { // Joystick X and Y Axes
              lowDashData.addCluster();
              {
                lowDashData.addDouble(Joystick(RIGHT_STICK_PORT).getX());
                lowDashData.addDouble(Joystick(RIGHT_STICK_PORT).getY());
              }
              lowDashData.finalizeCluster();
              // Adds Joystick Buttons
              lowDashData.addCluster();
              {
                for (int i = 1; i <= RIGHT_STICK_BUTTONS; i++) {
                        lowDashData.addBoolean(Joystick(RIGHT_STICK_PORT).getRawButton(i));
                    }
              }
              lowDashData.finalizeCluster();
            }
            lowDastData.finalizeCluster();
            
            // Extra Joystick 1
            lowDashData.addCluster();
            { // Joystick X and Y Axes
              lowDashData.addCluster();
              {
                lowDashData.addDouble(Joystick(EXTRA_STICK_1_PORT).getX());
                lowDashData.addDouble(Joystick(EXTRA_STICK_1_PORT).getY());
              }
              lowDashData.finalizeCluster();
              // Adds Joystick Buttons
              lowDashData.addCluster();
              {
                for (int i = 1; i <= EXTRA_STICK_1_BUTTONS; i++) {
                        lowDashData.addBoolean(Joystick(EXTRA_STICK_1_BUTTONS).getRawButton(i));
                    }
              }
              lowDashData.finalizeCluster();
            }
            lowDastData.finalizeCluster();
            
            // Extra Joystick 2
            lowDashData.addCluster();
            { // Joystick X and Y Axes
              lowDashData.addCluster();
              {
                lowDashData.addDouble(Joystick(EXTRA_STICK_2_PORT).getX());
                lowDashData.addDouble(Joystick(EXTRA_STICK_2_PORT).getY());
              }
              lowDashData.finalizeCluster();
              // Adds Joystick Buttons
              lowDashData.addCluster();
              {
                for (int i = 1; i <= EXTRA_STICK_2_BUTTONS; i++) {
                        lowDashData.addBoolean(Joystick(EXTRA_STICK_2_BUTTONS).getRawButton(i));
                    }
              }
              lowDashData.finalizeCluster();
            }
            lowDastData.finalizeCluster();
          }
          lowDashData.finalizeCluster();
          
          // Other Enhanced I/O (put here)
        }
        lowDashData.finalizeCluster();
        
        
        // Non-sensor data
        lowDashData.addCluster();
        {
            // Array of booleans
            lowDashData.addArray();
            {
                for (int i = 0; i < booleans.length; i++)
                    lowDashData.addBoolean(booleans[i]);
            }
            lowDashData.finalizeArray();
            
            // Array of doubles
            lowDashData.addArray();
            {
                for (int i = 0; i < doubles.length; i++)
                    lowDashData.addDouble(doubles[i]);
            }
            lowDashData.finalizeArray();
            
            // Array of ints
            lowDashData.addArray();
            {
                for (int i = 0; i < ints.length; i++)
                    lowDashData.addInt(ints[i]);
            }
            lowDashData.finalizeArray();
            
            // Array of strings
            lowDashData.addArray();
            {
                for (int i = 0; i < strings.length; i++)
                    lowDashData.addString(strings[i]);
            }
            lowDashData.finalizeArray();
        }
        lowDashData.finalizeCluster();
        
        lowDashData.commit();
    }
    
    private void addBoolean(boolean value) {
        booleans[booleansIndex++] = value;
    }
    
    private void addBoolean(boolean value, int index) {
        booleans[index] = value;
    }
    
    private void addDouble(double value) {
        doubles[doublesIndex++] = value;
    }
    
    private void addDouble(double value, int index) {
        doubles[index] = value;
    }
    
    private void addInt(int value) {
        ints[intsIndex++] = value;
    }
    
    private void addInt(int value, int index) {
        ints[index] = value;
    }
    
    private void addString(String value) {
        strings[stringsIndex++] = value;
    }
    
    private void addString(String value, int index) {
        strings[index] = value;
    }
    
    /**
     * Clears the buffer and resets all indices to zero.
     */
    private void resetBuffer() {
        for (int i = 0; i < booleans.length; i++)
            booleans[i] = false;
        for (int i = 0; i < doubles.length; i++)
            doubles[i] = 0.0;
        for (int i = 0; i < ints.length; i++)
            ints[i] = 0;
        for (int i = 0; i < strings.length; i++)
            strings[i] = "";
        booleansIndex = doublesIndex = intsIndex = stringsIndex = 0;
    }
}
