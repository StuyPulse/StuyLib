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
    Log logger;
    String longstr;

    public UserOutput(Object robot, int state) {
        dashboardUpdater = new DashboardUpdater(robot);
        this.robot = robot;
        logger = new Log(state);

    }

    public void log(String s){
        logger.write(s, robot);


    }
    public void log(int i ){
        String s = i + "";
        logger.write(s, robot);
    }
    public void log(double d){
        String s = d + "";
        logger.write(s, robot);
    }
    public void log(short s){
        String S = s + "";
        logger.write(S, robot);
    }
    public void log(char c){
        String s = c + "";
        logger.write(s, robot);
    }
    public void log(Object[] a){
        for (int i = 0 ;i < a.length; i++)
            log(a);
    }
    public void log(Object o){
        String s = o.toString();
        logger.write(s, robot);
    }
    public void log(float f){
        String s = f + "";
        logger.write(s, robot);
    }
    public void log(byte b){
        String s = b + "";
        logger.write(s, robot);
    }









}
