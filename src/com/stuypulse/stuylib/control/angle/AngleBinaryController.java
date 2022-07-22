/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle;

import com.stuypulse.stuylib.math.Angle;

public class AngleBinaryController extends AngleController {

    private final AngleController mControllerA;
    private final AngleController mControllerB;

    public AngleBinaryController(AngleController controllerA, AngleController controllerB) {
        mControllerA = controllerA;
        mControllerB = controllerB;
    }

    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        return mControllerA.calculate(setpoint, measurement)
                + mControllerB.calculate(setpoint, measurement);
    }
}
