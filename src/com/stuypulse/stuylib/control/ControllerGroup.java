/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

public class ControllerGroup extends Controller {

    private final Controller mController;
    private final Controller[] mControllers;

    public ControllerGroup(Controller controller, Controller... controllers) {
        mController = controller;
        mControllers = controllers;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        double output = mController.update(setpoint, measurement);

        for (Controller controller : mControllers) {
            output += controller.update(setpoint, measurement);
        }

        return output;
    }
}
