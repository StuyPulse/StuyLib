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
 * 
 * @author Kevin Wang
 */
public class DashboardUpdater {
    private final int NUM_BOOLEANS = 20;
    private final int NUM_DOUBLES = 20;
    private final int NUM_INTS = 5;
    private final int NUM_STRINGS = 5;
    
    private final int UPDATE_PERIOD_MS = 100;
    
    private boolean[] booleans = new boolean[NUM_BOOLEANS];
    private double[] doubles = new double[NUM_DOUBLES];
    private int[] ints = new int[NUM_INTS];
    private String[] strings = new String[NUM_STRINGS];
    
    private int booleansIndex, doublesIndex, intsIndex, stringsIndex;
    
    private Object robot;
    
    /**
     * Starts a timer to update the dashboard every 0.1 seconds.
     */
    public DashboardUpdater(Object robot) {
        this.robot = robot;
        resetBuffer();
        
        Timer dashTimer = new Timer();
        dashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateDashboard();
            }
        }, 0, UPDATE_PERIOD_MS);
    }
    
    private void updateDashboard() {
//        addBoolean(true);
//        addDouble(6.94);
//        addInt(694);
//        addString("Stuypulse");
        
        commit();
    }
    
    /**
     * Packs and sends data to be displayed on the dashboard.
     * 
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
        resetBuffer();
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
