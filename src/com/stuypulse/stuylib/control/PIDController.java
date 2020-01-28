package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.util.StopWatch;

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

    // The Integral of the errors and filter for the I Component
    private double mIntegral;
    private IStreamFilter mIFilter;

    // The last error used for calculating derivatives
    private double mLastError;

    // Timer used to get the time passed since last call
    private StopWatch mTimer;

    /**
     * @param p The Proportional Multiplier
     * @param i The Integral Multiplier
     * @param d The Derivative Multiplier
     */
    public PIDController(double p, double i, double d) {
        mTimer = new StopWatch();
        setIFilter(null);
        setPID(p, i, d);
        reset(0);
    }

    /**
     * @return the P value being used by the PID controller.
     */
    public double getP() {
        return mP;
    }

    /**
     * @return the P value being used by the PID controller.
     */
    public double getI() {
        return mI;
    }

    /**
     * @return the P value being used by the PID controller.
     */
    public double getD() {
        return mD;
    }

    /**
     * @param p new p value used by the PID controller.
     * @return refrence to PIDController (so you can chain the commands together)
     */
    public PIDController setP(double p) {
        mP = Math.max(p, 0);
        return this;
    }

    /**
     * @param i new i value used by the PID controller.
     * @return refrence to PIDController (so you can chain the commands together)
     */
    public PIDController setI(double i) {
        mI = Math.max(i, 0);
        return this;
    }

    /**
     * @param d new d value used by the PID controller.
     * @return refrence to PIDController (so you can chain the commands together)
     */
    public PIDController setD(double d) {
        mD = Math.max(d, 0);
        return this;
    }

    /**
     * @param p new p value used by the PID controller.
     * @param i new i value used by the PID controller.
     * @param d new d value used by the PID controller.
     * @return refrence to PIDController (so you can chain the commands together)
     */
    public PIDController setPID(double p, double i, double d) {
        return setP(p).setI(i).setD(d);
    }

    /**
     * It is common for a limit filter to be put on the I component to prevent
     * Integral Windup. You can use SLMath.limit(x) to do this.
     * 
     * @param filter filter put on the I component of the PID Controller
     * @return refrence to PIDController (so you can chain the commands together)
     */
    public PIDController setIFilter(IStreamFilter filter) {
        // Use default filter if given null
        mIFilter = (filter == null) ? ((x) -> x) : filter;
        return this;
    }

    /**
     * Resets the PIDController. This should be used if there is a large gap in time
     * between a get call. The controller automatically calls this if it detects a
     * gap greater than 1.0 second.
     * 
     * @param error the error that you want to reset to
     */
    private void reset(double error) {
        mTimer.reset();
        mLastError = error;
        mIntegral = 0;
    }

    /**
     * Calculate the value that the PIDController wants to move at.
     * 
     * @param error the error that the controller will use
     * @return the calculated result from the PIDController
     */
    @Override
    public double update(double error) {
        // Get the amount of time since the last get() was called
        double time_passed = mTimer.reset();

        // Calculate P Component
        double p_out = error * mP;

        // Calculate I Component
        mIntegral += error * time_passed;
        mIntegral = mIFilter.get(mIntegral);
        double i_out = mIntegral * mI;

        // Calculate D Componenet
        double slope = (error - mLastError) / time_passed;
        double d_out = slope * mD;

        // Update Last Error for next get() call
        mLastError = error;

        // Check if time passed exceeds reset limit
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
}
