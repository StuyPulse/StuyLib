/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

/**
 * Controllers can be grouped together in a "controller group"
 * if they have the same setpoint and measurement. 
 * 
 * This allows different controller implementations that are controlling the 
 * same variable to be concisely composed together.
 * 
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class ControllerGroup extends Controller {

    /** Controller part of the group */
    private final Controller mController;

    /** Controllers part of the group */
    private final Controller[] mControllers;

    /** Create a controller group */
    public ControllerGroup(Controller controller, Controller... controllers) {
        mController = controller;
        mControllers = controllers;
    }

    /**
     * Updates the internal controllers with the setpoint and measurement
     * and returns their combined output.
     * 
     * @param setpoint setpoint
     * @param measurement measurement
     * @return summed output of the interal controllers
     */
    @Override
    protected double calculate(double setpoint, double measurement) {
        double output = mController.update(setpoint, measurement);

        for (Controller controller : mControllers) {
            output += controller.update(setpoint, measurement);
        }

        return output;
    }
}
