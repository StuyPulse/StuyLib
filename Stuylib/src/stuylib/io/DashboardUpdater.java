/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stuylib.io;

import edu.wpi.first.wpilibj.Dashboard;
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
