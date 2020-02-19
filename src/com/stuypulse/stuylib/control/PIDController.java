package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.math.SLMath;

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
     * Amount of time in between .update() calls that is aloud before the
     * controller resets the system
     */
    private static final double kMaxTimeBeforeReset = 0.3; // s

    // Constants used by the PID controller
    private double mP;
    private double mI;
    private double mD;

    // The Integral of the errors and filter for the I Component
    private double mIntegral;
    private IStreamFilter mIFilter;

    /**
     * @param p The Proportional Multiplier
     * @param i The Integral Multiplier
     * @param d The Derivative Multiplier
     */
    public PIDController(double p, double i, double d) {
        setIntegratorFilter(null);
        setPID(p, i, d);
        reset();
    }

    /**
     * Creates a blank PIDController that doesn't move
     */
    public PIDController() {
        this(-1, -1, -1);
    }

    /**
     * Resets the integrator in the PIDController. This automatically gets
     * called if the gap between update() commands is too large
     */
    public void reset() {
        mIntegral = 0;
    }

    /**
     * Calculate the value that the PIDController wants to move at.
     *
     * @param error the error that the controller will use
     * @return the calculated result from the PIDController
     */
    @Override
    protected double calculate(double error) {
        // Calculate P Component
        double p_out = error * mP;

        // Calculate I Component
        mIntegral += error * getRate();
        mIntegral = mIFilter.get(mIntegral);
        double i_out = mIntegral * mI;

        // Calculate D Component
        double d_out = getVelocity() * mD;

        // Check if time passed exceeds reset limit
        if(getRate() < kMaxTimeBeforeReset) {
            // Return the calculated result
            return p_out + i_out + d_out;
        } else {
            // If time in system is broken, then reset and return p part
            // This is because the P part is not relative to time
            reset();
            return p_out;
        }
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
     * @return reference to PIDController (so you can chain the commands
     *         together)
     */
    public PIDController setP(double p) {
        mP = Math.max(p, 0);
        return this;
    }

    /**
     * @param i new i value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands
     *         together)
     */
    public PIDController setI(double i) {
        mI = Math.max(i, 0);
        return this;
    }

    /**
     * @param d new d value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands
     *         together)
     */
    public PIDController setD(double d) {
        mD = Math.max(d, 0);
        return this;
    }

    /**
     * @param p new p value used by the PID controller.
     * @param i new i value used by the PID controller.
     * @param d new d value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands
     *         together)
     */
    public PIDController setPID(double p, double i, double d) {
        return setP(p).setI(i).setD(d);
    }

    /**
     * It is common for a limit filter to be put on the I component to prevent
     * Integral Windup. You can use SLMath.limit(x) to do this.
     *
     * Passing null will disable the filter
     *
     * @param filter filter put on the I component of the PID Controller
     * @return reference to PIDController (so you can chain the commands
     *         together)
     */
    public PIDController setIntegratorFilter(IStreamFilter filter) {
        // Use default filter if given null
        mIFilter = (filter == null) ? ((x) -> x) : filter;
        return this;
    }

    /**
     * @return information about this PIDController
     */
    public String toString() {
        return "(P: " + SLMath.round(getP(), 4) + ", I: "
                + SLMath.round(getI(), 4) + ", D: " + SLMath.round(getD(), 4)
                + ")";
    }
}
