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
     * Get the current time relative time in seconds. This is accurate to the nano
     * second, but there are a few issues:
     * 
     * 1. Results are meaningless if not compared to past result 
     * 2. Clocks on different threads give incorrect results 
     * 3. Even through its to the nano second, its not 100% accurate
     * 4. Calls made more than 292 years appart will give incorrect results
     * 
     * But we use it any way for controllers because:
     * 
     * 1. All time sampling is relative anyways 
     * 2. A controller is only run on 1 thread 
     * 3. Its better than getTimeMillis() due to resolution
     * 
     * @return time represented in seconds
     */
    protected static final double getCurrentTime() {
        return System.nanoTime() / 1_000_000_000;
    }

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