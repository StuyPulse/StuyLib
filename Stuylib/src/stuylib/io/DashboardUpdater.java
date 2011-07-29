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
    
    /**
     * Starts a timer to update the dashboard every 0.1 seconds.
     */
    public DashboardUpdater() {
        Timer dashTimer = new Timer();
        dashTimer.schedule(new TimerTask() {
            public void run() {
                updateDashboard();
            }
        }, 0, 100);
    }
    
    /**
     * Packs and sends data to be displayed on the dashboard.
     * 
     * Note that any changes to the dashboard packet structure made here must
     * also be reflected in the dashboard's LabVIEW code.
     */
    public void updateDashboard() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        
        // Pack stuff
        
        lowDashData.commit();
    }
}
