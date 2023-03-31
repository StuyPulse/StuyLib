/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.streams.filters.Derivative;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A positional controller that uses a feedforward model to calculate motor outputs given positional
 * setpoints.
 *
 * <p>The feedforward model takes in velocity setpoints, so the derivative of the positional
 * setpoints must be calculated. This is done implicitly by this controller though.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class PositionFeedforwardController extends Controller {

    /** Filter to implicitly calculate velocity from position setpoints */
    private final Derivative mVelocity;

    /** Internal feedforward model */
    private final MotorFeedforward mFeedforward;

    /**
     * Create a position controller with a feedforward model
     *
     * @param feedforward feedforward model
     */
    public PositionFeedforwardController(MotorFeedforward feedforward) {
        mVelocity = new Derivative();
        mFeedforward = feedforward;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Position Feed Forward Controller");
        builder.addDoubleProperty("Position FF Setpoint", this::getSetpoint, null);
        builder.addDoubleProperty("Position FF Measurement", this::getMeasurement, null);
        builder.addDoubleProperty("Position FF Output", this::getOutput, null);
        builder.addDoubleProperty("Position FF Error", this::getError, null);
    }

    /**
     * Calculates a motor output by feeding the derivative of a positional setpoint to a feedforward
     * model
     *
     * @param setpoint positional setpoint
     * @param measurement position measurement, which is not used by the feedforward model
     * @return motor output from feedforward model
     */
    @Override
    protected double calculate(double setpoint, double measurement) {
        double velocity = mVelocity.get(setpoint);
        return mFeedforward.calculate(velocity);
    }
}
