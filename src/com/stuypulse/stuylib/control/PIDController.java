package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.control.Controller;

/**
 * This PID controller is built by extending the Controller class. It has a
 * dynamic rate, so it can detect how much time has passed between each update.
 * It is made to be easy to use and simple to understand while still being
 * accurate.
 * 
 * This PID controller resets the integral every time the error crosses 0 to
 * prevent integral windup / lag. This means that it may not be suitable for
 * setups involving rate instead of position
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PIDController extends Controller {

    /**
     * Amount of time inbetween .update() calls that is aloud before the controller
     * resets the system
     */
    private static final double kMaxTimeBeforeReset = 1.0; // s

    // Constants used by the PID controller
    private double mP;
    private double mI;
    private double mD;

    // The Integral of the errors for the I Component
    private double mIntegral;

    // The last time and last place used for calculations
    private double mLastTime;
    private double mLastError;

    /**
     * Create PID controller and set each of its values
     * 
     * @param p The Proportional Multiplier
     * @param i The Integral Multiplier
     * @param d The Derivative Multiplier
     */
    public PIDController(double p, double i, double d) {
        setPID(p, i, d);
        reset(0);
    }

    /**
     * Gets the P value being used by the PID controller.
     * 
     * @return the P value being used by the PID controller.
     */
    public double getP() {
        return mP;
    }

    /**
     * Gets the P value being used by the PID controller.
     * 
     * @return the P value being used by the PID controller.
     */
    public double getI() {
        return mI;
    }

    /**
     * Gets the P value being used by the PID controller.
     * 
     * @return the P value being used by the PID controller.
     */
    public double getD() {
        return mD;
    }

    /**
     * Changes the P value that will be used by the PID controller.
     * 
     * @param p new p value used by the PID controller.
     */
    public void setP(double p) {
        mP = Math.max(p, 0);
    }

    /**
     * Changes the I value that will be used by the PID controller.
     * 
     * @param i new i value used by the PID controller.
     */
    public void setI(double i) {
        mI = Math.max(i, 0);
    }

    /**
     * Changes the D value that will be used by the PID controller.
     * 
     * @param d new d value used by the PID controller.
     */
    public void setD(double d) {
        mD = Math.max(d, 0);
    }

    /**
     * Changes the P, I, and D values that will be used by the PID controller.
     * 
     * @param p new p value used by the PID controller.
     * @param i new i value used by the PID controller.
     * @param d new d value used by the PID controller.
     */
    public void setPID(double p, double i, double d) {
        setP(p);
        setI(i);
        setD(d);
    }

    /**
     * Get the time difference since last call. This is done with the
     * getCurrentTime() command and taking the difference since last call.
     * 
     * @return The time difference since last call
     */
    private double getTimeDifference() {
        double time = Controller.getCurrentTime();
        double diff = time - mLastTime;
        mLastTime = time;
        return diff;
    }

    /**
     * Resets the PIDController. This should be used if there is a large gap in time
     * between a get call. The controller automatically calls this if it detects a
     * gap greater than 1.0 second.
     * 
     * @param error the error that you want to reset to
     */
    public void reset(double error) {
        mLastTime = getCurrentTime();
        mLastError = error;
        mIntegral = 0;
    }

    /**
     * Calculate the value that the PIDController wants to move at.
     * 
     * @param error the error that the controller will use
     * @return the calculated result from the PIDController
     */
    public double update(double error) {
        // Get the amount of time since the last get() was called
        double time_passed = getTimeDifference();

        // Calculate P Component
        double p_out = error * mP;

        // Reset Integral if Error Has Crossed 0
        if(Math.signum(mLastError) != Math.signum(error)) {
            mIntegral = 0;
        }

        // Calculate I Component
        mIntegral += error * time_passed;
        double i_out = mIntegral * mI;

        // Calculate D Componenet
        double diff = (error - mLastError) / time_passed;
        double d_out = diff * mD;

        // Update Last Error for next get() call
        mLastError = error;

        if (time_passed < kMaxTimeBeforeReset) {
            // Return the calculated result
            return p_out + i_out + d_out;
        } else {
            // If time in system is broken, then reset and return p part
            // This is because the P part is not relative to time
            reset(error);
            return p_out;
        }
    }

    /**
     * Gets the last error from the error stream. Updates with get() command.
     * 
     * @return The last error from the error stream.
     */
    public double getError() {
        return mLastError;
    }
}
