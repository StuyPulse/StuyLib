package com.stuypulse.stuylib.pid;

import com.stuypulse.stuylib.streams.IStream;

/**
 * This is a PID Controller that works as an IStream. The PIDController takes in
 * the error stream as an IStream, and then using the get() command, is able to
 * give you your result back. It is able to get the polling rate automatically,
 * but it is recommended that you call it at a constant rate. It works as an
 * IStream so that if you have any functions that need to take in an IStream, ie
 * motor controller, than you can use this.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PIDController implements IStream {

    // Stream in which the error is provided
    private IStream mErrorStream;

    private double mP; // Multiplier for P Component
    private double mI; // Multiplier for I Component
    private double mD; // Multiplier for D Component

    // The Integral off the errors for the I Component
    private double mIntegral;

    // The last time and last place used for calculations
    private double mLastTime;
    private double mLastError;

    /**
     * Initialize a PIDController using an error stream and the PID multipliers.
     * 
     * @param errorStream An IStream that provides the error to the PID Controller
     * @param p           The Proportional Multiplier
     * @param i           The Integral Multiplier
     * @param d           The Derivative Multiplier
     */
    public PIDController(IStream errorStream, double p, double i, double d) {
        setErrorStream(errorStream);
        setP(p);
        setI(i);
        setD(d);
        reset();
    }

    /**
     * Initialize a PIDController using PID multipliers.
     * 
     * Warning: Without specifying the errorStream, the PIDController will throw an
     * error when .get() is called with no parameters.
     * 
     * @param p The Proportional Multiplier
     * @param i The Integral Multiplier
     * @param d The Derivative Multiplier
     */
    public PIDController(double p, double i, double d) {
        this(null, p, i, d);
    }

    /**
     * Gets the error stream being used by the controller
     * 
     * @return the error stream being used by the controller
     */
    public IStream getErrorStream() {
        return mErrorStream;
    }

    /**
     * Sets the error stream that will be used by the PID controller.
     * 
     * @param errorStream the new error stream for the PID controller to use.
     */
    public void setErrorStream(IStream errorStream) {
        mErrorStream = errorStream;
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
     * Get the current time in seconds. This is used to get the calculation of PID
     * correct
     * 
     * @return current time in seconds
     */
    private static double getCurrentTime() {
        return System.nanoTime() / 1_000_000_000;
    }

    /**
     * Get the time difference since last call. This is done with the
     * getCurrentTime() command and taking the difference since last call.
     * 
     * @return The time difference since last call
     */
    private double getTimeDifference() {
        double time = getCurrentTime();
        double diff = (time - mLastTime);
        mLastTime = time;
        return diff;
    }

    /**
     * Resets the PIDController. This should be used if there is a large gap in time
     * between a get call. The controller automatically calls this if it detects a
     * gap greater than 1.0 second.
     */
    public void reset() {
        mLastTime = getCurrentTime();
        mLastError = mErrorStream.get();
        mIntegral = 0;
    }

    /**
     * Calculate the value that the PIDController wants to move at.
     * 
     * @param error the error that the controller will use
     * @return the calculated result from the PIDController
     */
    public double get(double error) {
        // Get the amount of time since the last get() was called
        double dt = getTimeDifference();

        // If the PIDController detects that there has been a gap
        // in time, it should reset.
        if (dt > 1.0) {
            reset();
            return 0.0;
        } else {
            // Calculate P Component
            double p_out = error * mP;

            // Calculate I Component
            mIntegral += error * dt;
            double i_out = mIntegral * mI;

            // Calculate D Componenet
            double diff = (error - mLastError) / dt;
            double d_out = diff * mD;

            // Update Last Error for next get() call
            mLastError = error;

            // Return the calculated result
            return p_out + i_out + d_out;
        }

    }

    /**
     * Calculate the value that the PIDController wants to move at. This is the same
     * as using get(error), but it defaults to the mErrorStream.
     * 
     * If no error stream is provided, then it throws a runtime error.
     * 
     * @return The calculated value for the PIDController
     */
    public double get() {
        if (mErrorStream != null) {
            return get(mErrorStream.get());
        } else {
            throw new RuntimeException("Uninitialized Error Stream!");
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
