/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.IFilter;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * This PID controller is built by extending the Controller class. It has a dynamic rate, so it can
 * detect how much time has passed between each update. It is made to be easy to use and simple to
 * understand while still being accurate.
 *
 * <p>This PID controller resets the integral every time the error crosses 0 to prevent integral
 * windup / lag. This means that it may not be suitable for setups involving rate instead of
 * position
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PIDController extends Controller {

    /**
     * Amount of time in between .update() calls that is aloud before the controller resets the
     * system
     */
    private static final double kMaxTimeBeforeReset = 0.5;

    // Constants used by the PID controller
    private Number mP;
    private Number mI;
    private Number mD;

    // The Integral of the errors and filter for the I Component
    private double mIntegral;
    private IFilter mIFilter;

    /**
     * @param p The Proportional Multiplier
     * @param i The Integral Multiplier
     * @param d The Derivative Multiplier
     */
    public PIDController(Number p, Number i, Number d) {
        setIntegratorFilter(null);
        setPID(p, i, d);
        reset();
    }

    /** Creates a blank PIDController that doesn't move */
    public PIDController() {
        this(-1, -1, -1);
    }

    /**
     * Resets the integrator in the PIDController. This automatically gets called if the gap between
     * update() commands is too large
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
        double p_out = error * getP();

        // Calculate I Component
        mIntegral += error * getRate();
        mIntegral = mIFilter.get(mIntegral);
        double i_out = mIntegral * getI();

        // Calculate D Component
        double d_out = getVelocity() * getD();

        // Check if time passed exceeds reset limit
        if (getRate() < kMaxTimeBeforeReset) {
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
    public PIDController setP(Number p) {
        mP = SmartNumber.setNumber(mP, p);
        return this;
    }

    /**
     * @param i new i value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands together)
     */
    public PIDController setI(Number i) {
        mI = SmartNumber.setNumber(mI, i);
        return this;
    }

    /**
     * @param d new d value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands together)
     */
    public PIDController setD(Number d) {
        mD = SmartNumber.setNumber(mD, d);
        return this;
    }

    /**
     * @param p new p value used by the PID controller.
     * @param i new i value used by the PID controller.
     * @param d new d value used by the PID controller.
     * @return reference to PIDController (so you can chain the commands together)
     */
    public PIDController setPID(Number p, Number i, Number d) {
        return setP(p).setI(i).setD(d);
    }

    /**
     * @param pidValues PIDController that stores the PID values
     * @return reference to PIDController (so you can chain the commands together)
     */
    public PIDController setPID(PIDController pidValues) {
        return setPID(pidValues.getP(), pidValues.getI(), pidValues.getD());
    }

    /**
     * It is common for a limit filter to be put on the I component to prevent Integral Windup. You
     * can use SLMath.limit(x) to do this.
     *
     * <p>Passing null will disable the filter
     *
     * @param filter filter put on the I component of the PID Controller
     * @return reference to PIDController (so you can chain the commands together)
     */
    public PIDController setIntegratorFilter(IFilter filter) {
        // Use default filter if given null
        mIFilter = (filter == null) ? ((x) -> x) : filter;
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

    /*********************/
    /*** Sendable Data ***/
    /*********************/

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);

        builder.addDoubleProperty("(PID) kP", this::getP, this::setP);
        builder.addDoubleProperty("(PID) kI", this::getI, this::setI);
        builder.addDoubleProperty("(PID) kD", this::getD, this::setD);

        builder.addDoubleProperty("(PID) Integral", () -> this.mIntegral, x -> {});
    }
}
