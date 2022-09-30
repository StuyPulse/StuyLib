/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;

/**
 * A velocity controller that uses a feedforward model to calculate motor outputs given velocity
 * setpoints.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class VelocityFeedforwardController extends Controller {

    /** Internal feedforward model */
    private final Feedforward mFeedforward;

    /**
     * Create a velocity controller with a feedforward model
     *
     * @param feedforward the model
     */
    public VelocityFeedforwardController(Feedforward feedforward) {
        mFeedforward = feedforward;
    }

    /**
     * Calculates a motor output by feeding a velocity setpoint to a feedforward model.
     *
     * @param setpoint velocity setpoint of the controller
     * @param measurement measurement, which is not used in calculating an output
     * @return motor output from feedforward model
     */
    @Override
    protected double calculate(double setpoint, double measurement) {
        return mFeedforward.calculate(setpoint);
    }
}
