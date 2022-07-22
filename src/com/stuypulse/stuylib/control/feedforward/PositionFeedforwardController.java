/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.streams.filters.Derivative;

public class PositionFeedforwardController extends Controller {

    private final Derivative mVelocity;
    private final Feedforward mFeedforward;

    public PositionFeedforwardController(Feedforward feedforward) {
        mVelocity = new Derivative();
        mFeedforward = feedforward;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        double velocity = mVelocity.get(setpoint);
        return mFeedforward.calculate(velocity);
    }
}
