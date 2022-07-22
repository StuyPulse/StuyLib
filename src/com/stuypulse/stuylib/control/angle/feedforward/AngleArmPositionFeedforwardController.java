/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle.feedforward;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;

public class AngleArmPositionFeedforwardController extends AngleController {

    private final ArmFeedforward mArmFeedforward;

    public AngleArmPositionFeedforwardController(ArmFeedforward armFeedforward) {
        mArmFeedforward = armFeedforward;
    }

    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        return mArmFeedforward.calculate(setpoint);
    }
}
