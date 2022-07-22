/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

public class BinaryController extends Controller {

    private final Controller mControllerA;
    private final Controller mControllerB;

    public BinaryController(Controller controllerA, Controller controllerB) {
        mControllerA = controllerA;
        mControllerB = controllerB;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        return mControllerA.update(setpoint, measurement)
                + mControllerB.update(setpoint, measurement);
    }
}
