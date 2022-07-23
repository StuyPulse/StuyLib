/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle;

import com.stuypulse.stuylib.math.Angle;

public class AngleControllerGroup extends AngleController {

    private final AngleController mController;
    private final AngleController[] mControllers;

    public AngleControllerGroup(AngleController controller, AngleController... controllers) {
        mController = controller;
        mControllers = controllers;
    }

    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        double output = mController.update(setpoint, measurement);

        for (AngleController controller : mControllers) {
            output += controller.update(setpoint, measurement);
        }

        return output;
    }
}
