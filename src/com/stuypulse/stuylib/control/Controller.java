package com.stuypulse.stuylib.control;

/**
 * The controller class is an abstract class that is used to create different
 * controllers. All a class will need to do is implement update(double error)
 * and the update(double measurement, double setpoint) function will also work.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public abstract class Controller {
    
    /**
     * Update the controller with the measurement that was just made and the set
     * point you would like it to approach
     * 
     * @param measurement measurement of device just made
     * @param setpoint    desired result
     * @return controller output
     */
    public final double update(double measurement, double setpoint) {
        return update(measurement - setpoint);
    }

    /**
     * Update the controller with the error just measured
     * 
     * @param error error that was just measured
     * @return controller output.
     */
    public abstract double update(double error);
}