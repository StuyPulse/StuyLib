/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.streams.filters.IFilter;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A feedforward term to account for gravity for motorized arms.
 *
 * <p>The motor feedforward used in the context of an arm will not account for gravity that is
 * acting on the arm.
 *
 * <p>Can be paired with MotorFeedforward or other controllers with .add
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class ArmFeedforward extends Controller {

    /** voltage to hold arm horizontal */
    private final Number kG;

    /**
     * function to configure units of cosine (or even use sin if angles are measured differently)
     */
    private final IFilter cosine;

    /**
     * Create arm feedforward
     *
     * @param kG term to hold arm vertical against gravity (volts)
     */
    public ArmFeedforward(Number kG) {
        this(kG, x -> Math.cos(Math.toRadians(x)));
    }

    /**
     * Create arm feedforward
     *
     * @param kG term to hold arm vertical against gravity (volts)
     * @param cosine function to calculate cosine of setpoint
     */
    public ArmFeedforward(Number kG, IFilter cosine) {
        this.kG = kG;
        this.cosine = cosine;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Arm Feed Forward");
        builder.addDoubleProperty("Setpoint", this::getSetpoint, null);
        builder.addDoubleProperty("Measurement", this::getMeasurement, null);
        builder.addDoubleProperty("Output", this::getOutput, null);
        builder.addDoubleProperty("Error", this::getError, null);
    }

    /**
     * Calculates voltage to hold arm at the setpoint angle
     *
     * @param setpoint setpoint
     * @param measurement measurement
     * @return kG * cos(setpoint)
     */
    @Override
    protected double calculate(double setpoint, double measurement) {
        return kG.doubleValue() * cosine.get(setpoint);
    }
}
