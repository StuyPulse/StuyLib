/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

import java.util.ArrayList;
import java.util.ResourceBundle.Control;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * Controllers can be grouped together in a "controller group" if they have the same setpoint and
 * measurement.
 *
 * <p>This allows different controller implementations that are controlling the same variable to be
 * concisely composed together.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class ControllerGroup extends Controller {

    /** Controllers part of the group */
    private final ArrayList<Controller> mControllers;

    /** Create a controller group */
    public ControllerGroup(Controller controller, Controller... controllers) {
        mControllers = new ArrayList<>();

        mControllers.add(controller);
        for (Controller tmpController : controllers) {
            mControllers.add(tmpController);
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        for (Controller controller : mControllers) {
            controller.initSendable(builder);
        }
    }

    @Override
    public ControllerGroup add(Controller... controller) {
        for (Controller tmpController : controller) {
            if (tmpController == null) {
                throw new IllegalArgumentException("Controller cannot be null");
            }
            mControllers.add(tmpController);
        }
        return this;
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
    protected double calculate(double setpoint, double measurement) {
        double output = 0;

        for (Controller controller : mControllers) {
            output += controller.update(setpoint, measurement);
        }

        return output;
    }
}
