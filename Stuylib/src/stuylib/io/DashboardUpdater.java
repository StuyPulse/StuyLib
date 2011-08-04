/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stuylib.io;

import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Handles the transmission of user-defined data to a PC running LabVIEW dashboard software.
 * 
 * @author Kevin Wang
 */
public class DashboardUpdater {
    Object robot;
    Vector booleans, doubles, ints, strings;
    
    final int NUM_BOOLEANS = 20;
    final int NUM_DOUBLES = 20;
    final int NUM_INTS = 5;
    final int NUM_STRINGS = 5;
    
    /**
     * Starts a timer to update the dashboard every 0.1 seconds.
     */
    public DashboardUpdater(Object robot) {
        this.robot = robot;
        
        booleans = new Vector();
        doubles = new Vector();
        ints = new Vector();
        strings = new Vector();
        
        Timer dashTimer = new Timer();
        dashTimer.schedule(new TimerTask() {
            public void run() {
                packData();
            }
        }, 0, 100);
    }
    
    public void packData() {
//        addBoolean(true);
//        addDouble(6.94);
//        addInt(694);
//        addString("Stuypulse");
        
        updateDashboard();
        resetVectors();
    }
    
    /**
     * Packs and sends data to be displayed on the dashboard.
     * 
     * Note that any changes to the dashboard packet structure made here must
     * also be reflected in the dashboard's LabVIEW code.
     */
    public void updateDashboard() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        
        { // Booleans
            int i = 0;
            while (i < booleans.size()) {
                lowDashData.addBoolean((Boolean)booleans.elementAt(i));
                i++;
            }
            while (i < NUM_BOOLEANS) {
                lowDashData.addBoolean(false);
                i++;
            }
        }
        
        { // Doubles
            int i = 0;
            while (i < doubles.size()) {
                lowDashData.addDouble((Double)doubles.elementAt(i));
                i++;
            }
            while (i < NUM_DOUBLES) {
                lowDashData.addDouble(0.0);
                i++;
            }
        }
        
        { // Ints
            int i = 0;
            while (i < ints.size()) {
                lowDashData.addInt((Integer)ints.elementAt(i));
                i++;
            }
            while (i < NUM_INTS) {
                lowDashData.addInt(0);
                i++;
            }
        }
        
        { // Strings
            int i = 0;
            while (i < strings.size()) {
                lowDashData.addString((String)strings.elementAt(i));
                i++;
            }
            while (i < NUM_STRINGS) {
                lowDashData.addString("");
                i++;
            }
        }
        
        lowDashData.commit();
    }
    
    public void addBoolean(boolean value) {
        booleans.add(value);
    }
    
    public void addDouble(double value) {
        doubles.add(value);
    }
    
    public void addInt(int value) {
        ints.add(value);
    }
    
    public void addString(String value) {
        strings.add(value);
    }
    
    public void resetVectors() {
        booleans.removeAllElements();
        doubles.removeAllElements();
        ints.removeAllElements();
        strings.removeAllElements();
    }
}
