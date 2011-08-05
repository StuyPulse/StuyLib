/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stuylib.io;

/**
 *
 * @author Kevin Wang
 */
public class UserOutput {
    DashboardUpdater dashboardUpdater;
    Object robot;
    
    public UserOutput(Object robot) {
        dashboardUpdater = new DashboardUpdater(robot);
        this.robot = robot;
    }
}
