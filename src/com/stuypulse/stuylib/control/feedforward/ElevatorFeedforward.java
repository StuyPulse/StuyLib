/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A feedforward term to account for gravity for motorized lifts.
 *
 * <p>The motor feedforward used in the context of a lift will not account for gravity that is
 * acting on the lift.
 *
 * <p>Can be paired with MotorFeedforward or other controllers with .add
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class ElevatorFeedforward extends Controller {

    /** voltage to hold arm in place against gravity */
    private final Number kG;

    /**
     * Create ElevatorFeedforward
     *
     * @param kG voltage to hold lift
     */
    public ElevatorFeedforward(Number kG) {
        this.kG = kG;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Elevator Feed Forward");
        builder.addDoubleProperty("Elevator FF Setpoint", this::getSetpoint, null);
        builder.addDoubleProperty("Elevator FF Measurement", this::getMeasurement, null);
        builder.addDoubleProperty("Elevator FF Output", this::getOutput, null);
        builder.addDoubleProperty("Elevator FF Error", this::getError, null);
    }

    /**
     * Calculate voltage to hold elevator at setpoint height
     *
     * @param setpoint setpoint
     * @param measurement measurement
     * @return kG
     */
    @Override
    protected double calculate(double setpoint, double measurement) {
        return kG.doubleValue();
    }
}
