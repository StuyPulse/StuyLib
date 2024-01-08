/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle.feedback;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.numbers.filters.IFilter;
import com.stuypulse.stuylib.streams.numbers.filters.IFilterGroup;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * This PID controller is built by extending the AngleController class. It has a dynamic rate, so it
 * can detect how much time has passed between each update. It is made to be easy to use and simple
 * to understand while still being accurate.
 *
 * <p>The angle PID controller class is ALWAYS in units of *RADIANS*
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class AnglePIDController extends AngleController {

    /**
     * Amount of time in between .update() calls that is aloud before the controller resets the
     * system
     */
    private static final double kMaxTimeBeforeReset = 0.5;

    // Internal timer used by Controller
    private StopWatch mTimer;

    // Constants used by the PID controller
    private Number mP;
    private Number mI;
    private Number mD;

    // The Integral of the errors and filter for the I Component
    private double mIntegral;
    private IFilter mIFilter;

    // The Derivative of the error and the filter for the D Component
    private Angle mLastError;
    private IFilter mDFilter;

    /**
     * @param p The Proportional Multiplier
     * @param i The Integral Multiplier
     * @param d The Derivative Multiplier
     */
    public AnglePIDController(Number p, Number i, Number d) {
        mTimer = new StopWatch();

        mLastError = Angle.kZero;
        setIntegratorFilter(0, 0);
        setDerivativeFilter(x -> x);
        setPID(p, i, d);
        reset();
    }

    /** Creates a blank PIDController that doesn't move */
    public AnglePIDController() {
        this(-1, -1, -1);
    }

    /**
     * Resets the integrator in the PIDController. This automatically gets called if the gap between
     * update() commands is too large
     */
    public void reset() {
        mIntegral = 0;
    }

    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        // Calculate error & time step
        Angle error = setpoint.sub(measurement);
        double dt = mTimer.reset();

        // Calculate P Component
        double p_out = error.toRadians() * getP();

        // Calculate I Component
        mIntegral += error.toRadians() * dt;
        mIntegral = mIFilter.get(mIntegral);
        double i_out = mIntegral * getI();

        // Calculate D Component
        double derivative = mDFilter.get(error.velocityRadians(mLastError, dt));
        mLastError = error;
        double d_out = derivative * getD();

        // Check if time passed exceeds reset limit
        if (dt < kMaxTimeBeforeReset) {
            // Return the calculated result
            return p_out + i_out + d_out;
        } else {
            // If time in system is broken, then reset and return p part
            // This is because the P part is not relative to time
            reset();
            return p_out;
        }
    }

    /** @return the P value being used by the PID controller. */
    public double getP() {
        return Math.max(mP.doubleValue(), 0.0);
    }

    /** @return the P value being used by the PID controller. */
    public double getI() {
        return Math.max(mI.doubleValue(), 0.0);
    }

    /** @return the P value being used by the PID controller. */
    public double getD() {
        return Math.max(mD.doubleValue(), 0.0);
    }

    /**
     * @param p new p value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands together)
     */
    public AnglePIDController setP(Number p) {
        mP = SmartNumber.setNumber(mP, p);
        return this;
    }

    /**
     * @param i new i value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands together)
     */
    public AnglePIDController setI(Number i) {
        mI = SmartNumber.setNumber(mI, i);
        return this;
    }

    /**
     * @param d new d value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands together)
     */
    public AnglePIDController setD(Number d) {
        mD = SmartNumber.setNumber(mD, d);
        return this;
    }

    /**
     * @param p new p value used by the PID controller.
     * @param i new i value used by the PID controller.
     * @param d new d value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands together)
     */
    public AnglePIDController setPID(Number p, Number i, Number d) {
        return setP(p).setI(i).setD(d);
    }

    /**
     * @param pidValues PIDController that stores the PID values
     * @return reference to PIDController (so you can chain the commands together)
     */
    public AnglePIDController setPID(AnglePIDController pidValues) {
        return setPID(pidValues.getP(), pidValues.getI(), pidValues.getD());
    }

    /**
     * Add constraints to the integral of the PID Controller
     *
     * @param range the range of error in which the integral is allowed to accumulate (0 will
     *     disable)
     * @param limit the max / min the integral is allowed to accumulate to (0 will disables)
     * @return reference to PIDController (so you can chain the commands together)
     */
    public AnglePIDController setIntegratorFilter(Number range, Number limit) {
        mIFilter =
                new IFilterGroup(
                        x -> range.doubleValue() <= 0 || isDoneRadians(range.doubleValue()) ? x : 0,
                        x -> limit.doubleValue() <= 0 ? x : SLMath.clamp(x, limit.doubleValue()));
        return this;
    }

    /**
     * Add a filter to the error velocity / derivative of the PID controller.
     *
     * @param derivativeFilter the filter to apply to derivative
     * @return reference to PIDController (so you can chain the commands together)
     */
    public AnglePIDController setDerivativeFilter(IFilter... derivativeFilter) {
        mDFilter = IFilter.create(derivativeFilter);
        return this;
    }

    /** @return information about this PIDController */
    public String toString() {
        return "(P: "
                + SLMath.round(getP(), 4)
                + ", I: "
                + SLMath.round(getI(), 4)
                + ", D: "
                + SLMath.round(getD(), 4)
                + ")";
    }
}
