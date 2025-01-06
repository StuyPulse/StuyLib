/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.angle.feedforward.AnglePositionFeedforwardController;
import com.stuypulse.stuylib.streams.numbers.filters.Derivative;

/**
 * A motor feedforward model, which can be used to calculate an output voltage given a desired
 * velocity of a motor.
 *
 * <p>A feedforward model often uses the dynamics of a system to calculate a voltage for a movement
 * that is specified by a desired velocity.
 *
 * <p>Feedforward models often take into account acceleration as well, but this is implicitly
 * calculated from desired velocities so that only sensible movements can be specified.
 *
 * <p>A motor feedforward *model* can be converted into *controllers* through 3 decorator methods:
 * `.velocity()`, `.position()`, and `.angle()`. `.velocity()` returns a velocity feedforward
 * controller, while `.position()` returns a position feedforward controller, and `.angle()` returns
 * an angle feedforward controller. These methods differ on what the controller's setpoint unit is.
 *
 * <p>For example, the velocity feedforward controller takes in velocity setpoints, which are
 * directly passed to the model. On the other hand, a position feedforward controller takes in
 * positional setpoints, which are then differentiated to get velocity and then fed to the
 * feedforward controller. An angle feedforward controller is a positional controller that is forced
 * to use angular units, so angular velocity is calculated and then fed to the feedforward model.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class MotorFeedforward {

    /** The derivative filter that will handle calculating acceleration */
    private final Derivative mDerivative;

    /** The constants for the feedforward equation */
    private final Number kS;

    private final Number kV;
    private final Number kA;

    /**
     * Create a feedforward model for a motor
     *
     * @param kS volts, describes portion of voltage to overcome static friction
     * @param kV volts * seconds / distance, describes voltage needed to hold constant velocity
     * @param kA volts * seconds^2 / distance, describes voltage needed to move at an acceleration
     */
    public MotorFeedforward(Number kS, Number kV, Number kA) {
        mDerivative = new Derivative();

        this.kS = kS;
        this.kV = kV;
        this.kA = kA;
    }

    /**
     * Creates a controller that uses this feedforward model to calculate a motor output given
     * velocity setpoints.
     *
     * @return a velocity controller for this feedforward model
     */
    public final Controller velocity() {
        return new VelocityFeedforwardController(this);
    }

    /**
     * Creates a controller that uses this feedforward model to calculate a motor output given
     * positional setpoints.
     *
     * <p>NOTE: The derivative of the position setpoints is calculated and then plugged into this
     * model.
     *
     * @return the position controller for this feedforward model
     */
    public final Controller position() {
        return new PositionFeedforwardController(this);
    }

    /**
     * Creates a controller that uses this feedforward model to calculate a motor output given angle
     * setpoints.
     *
     * <p>NOTE: the angular velocity of the angle setpoints is calculated and then plugged into this
     * model.
     *
     * @return the angle controller for this feedforward model
     */
    public final AngleController angle() {
        return new AnglePositionFeedforwardController(this);
    }

    /**
     * Calculates a motor output given a desired velocity
     *
     * <p>Implicitly determines acceleration by taking the derivative of velocity.
     *
     * @param velocity desired velocity
     * @return motor output, often in volts
     */
    public final double calculate(double velocity) {
        double acceleration = mDerivative.get(velocity);

        return kS.doubleValue() * Math.signum(velocity)
                + kV.doubleValue() * velocity
                + kA.doubleValue() * acceleration;
    }
}
