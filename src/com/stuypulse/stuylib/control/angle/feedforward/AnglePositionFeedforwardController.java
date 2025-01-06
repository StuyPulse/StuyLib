/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle.feedforward;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.feedforward.MotorFeedforward;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.AngleVelocity;

/**
 * A positional feedforward controller for angular systems.
 *
 * @see com.stuypulse.stuylib.control.feedforward.PositionFeedforwardController
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class AnglePositionFeedforwardController extends AngleController {

    /** the feedforward model */
    private final MotorFeedforward mFeedforward;

    /** find the derivative of angular setpoints */
    private final AngleVelocity mDerivative;

    /**
     * Create an angle position feedforward controller
     *
     * @param feedforward model
     */
    public AnglePositionFeedforwardController(MotorFeedforward feedforward) {
        mFeedforward = feedforward;
        mDerivative = new AngleVelocity();
    }

    /**
     * Calculates a motor output by feeding the derivative of a positional setpoint to a feedforward
     * model
     *
     * @param setpoint angular positional setpoint
     * @param measurement angular position measurement, which is not used by the feedforward model
     * @return motor output from feedforward model
     */
    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        return mFeedforward.calculate(mDerivative.get(setpoint));
    }
}
