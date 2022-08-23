/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.math.geometry.Rotation2d;
import java.util.function.Function;

/**
 * A continuous controller whose measurements and setpoints will be angles.
 *
 * <p>An angle controller handles continuous systems that are (most often) measured on a circle,
 * which means the error must be normalized into the range [-180, +180].
 *
 * @author Myles Pasetsky (@selym3)
 */
public class AngleController {

    // the underlying controller
    private Controller mController;

    // a function that will convert an angle into the units
    // that the underlying controller expects
    private Function<Angle, Double> mUnits;

    /**
     * Creates an angle controller from a controller.
     *
     * <p>The controller should be configured BEFORE being passed into the angle controller. Then
     * .angle() should be called to create
     *
     * <p>BY DEFAULT, this controller should expect to receive error in the unit of radians.
     *
     * @param controller controller to wrap
     */
    public AngleController(Controller controller) {
        mController = controller;
        useRadians();
    }

    /**
     * Set what unit the angle error should be converted to before being passed to the underlying
     * controller.
     *
     * @param units conversion function
     * @return reference to this controller (to chain commands together)
     */
    public AngleController setUnits(Function<Angle, Double> units) {
        mUnits = units;
        return this;
    }

    /**
     * Sets the unit being passed to the controller to degrees.
     *
     * @return reference to this controller (to chain commands together)
     */
    public AngleController useDegrees() {
        return setUnits(a -> a.toDegrees());
    }

    /**
     * Sets the unit being passed to the controller to radians.
     *
     * @return reference to this controller (to chain commands together)
     */
    public AngleController useRadians() {
        return setUnits(a -> a.toRadians());
    }

    /**
     * Calculates the desired output based on an angular setpoint and measurement.
     *
     * @param setpoint target angle to reach
     * @param measurement current angle of system
     * @return output that will drive measurement to setpoint
     */
    public double update(Angle setpoint, Angle measurement) {
        return update(setpoint.sub(measurement));
    }

    /**
     * Calculates the desired output based on an angular error
     *
     * @param error the error given to contrller
     * @return output that will drive measurement to setpoint
     */
    public double update(Angle error) {
        return mController.update(mUnits.apply(error));
    }

    /**
     * Calculates the desired output based on a rotation2d measurement and setpoint
     *
     * @param setpoint target angle to reach
     * @param measurement current angle of system
     * @return controller output
     */
    public double update(Rotation2d setpoint, Rotation2d measurement) {
        return update(Angle.fromRadians(setpoint.getRadians() - measurement.getRadians()));
    }

    /**
     * Calculates the desired output based on a rotation2d angle error
     *
     * @param error error given to controller
     * @return controller output
     */
    public double update(Rotation2d error) {
        return update(Angle.fromRadians(error.getRadians()));
    }
}
