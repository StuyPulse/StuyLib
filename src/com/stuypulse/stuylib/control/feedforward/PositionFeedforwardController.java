/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.streams.filters.Derivative;

/**
 * A positional controller that uses a feedforward model to calculate
 * motor outputs given positional setpoints. 
 * 
 * The feedforward model takes in velocity setpoints, so the derivative of the 
 * positional setpoints must be calculated. This is done implicitly by this 
 * controller though.
 * 
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class PositionFeedforwardController extends Controller {

    /** Filter to implicitly calculate velocity from position setpoints */
    private final Derivative mVelocity;

    /** Internal feedforward model */
    private final Feedforward mFeedforward;

    /**
     * Create a position controller with a feedforward model
     * 
     * @param feedforward feedforward model
     */
    public PositionFeedforwardController(Feedforward feedforward) {
        mVelocity = new Derivative();
        mFeedforward = feedforward;
    }

    /**
     * Calculates a motor output by feeding the derivative of a positional setpoint
     * to a feedforward model
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
