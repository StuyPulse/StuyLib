/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle;

import java.util.ArrayList;

import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * Angle controllers can be grouped together in a "controller group" if they have the same setpoint
 * and measurement.
 *
 * <p>This allows different controller implementations that are controlling the same variable to be
 * concisely composed together.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class AngleControllerGroup extends AngleController {

    /** Controllers part of the group */
    private final ArrayList<AngleController> mControllers;

    /** Create a controller group */
    public AngleControllerGroup(AngleController controller, AngleController... controllers) {

        mControllers = new ArrayList<>(controllers.length);
        mControllers.add(controller);
        
        for (AngleController tmpController : controllers) {
            if (tmpController == null) {
                throw new IllegalArgumentException("Controller cannot be null");
            }
            mControllers.add(tmpController);
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        for (AngleController controller : mControllers) {
            controller.initSendable(builder);
        }
    }

    /**
     * Updates the internal controllers with the setpoint and measurement and returns their combined
     * output.
     *
     * @param setpoint setpoint
     * @param measurement measurement
     * @return summed output of the interal controllers
     */
    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        double output = 0;

        for (AngleController controller : mControllers) {
            output += controller.update(setpoint, measurement);
        }

        return output;
    }
}
