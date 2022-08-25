/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.angle.feedforward.AnglePositionFeedforwardController;
import com.stuypulse.stuylib.streams.filters.Derivative;

/**
 * An abstract feedforward model, which can be used to calculate an output voltage given a desired
 * velocity of a system.
 *
 * <p>A feedforward model often uses the dynamics of a system to calculate a voltage for a movement
 * that is specified by a desired velocity.
 *
 * <p>Feedforward models often take into account acceleration as well, but this is implicitly
 * calculated from desired velocities so that only sensible movements can be specified.
 *
 * <p>Feedforward *models* can be converted into *controllers* through 3 decorator methods:
 * `.velocity()`, `.position()`, and `.angle()`. These methods differ on what the controller's
 * setpoint unit is. `.velocity()` returns a velocity feedforward controller, while `.position()`
 * returns a position feedforward controller, and `.angle()` returns an angle feedforward
 * controller.
 *
 * <p>For example, the velocity feedforward controller takes in velocity setpoints, which are
 * directly passed to the model. On the other hand, a position feedforward controller takes in
 * positional setpoints, which are then differentiated to get velocity and then fed to the
 * feedforward controller. An angle feedforward controller is a positional controller that is forced
 * to use angular units, so angular velocity is calculated and then fed to the feedforward model.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public abstract class Feedforward {

    /** The derivative filter that will handle calculating acceleration */
    private final Derivative mDerivative;

    /** Default initialization of a feedforward model */
    public Feedforward() {
        mDerivative = new Derivative();
    }

    /**
     * Creates a controller that uses this feedforward model to calculate a motor output given
     * velocity setpoints.
     *
     * @return a velocity controller for this feedforward model
     */
    public final VelocityFeedforwardController velocity() {
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
    public final PositionFeedforwardController position() {
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
    public final AnglePositionFeedforwardController angle() {
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
        return calculate(velocity, mDerivative.get(velocity));
    }

    /**
     * Calculates a motor output given a desired velocity and acceleration.
     *
     * @param velocity desired velocity
     * @param acceleration desired acceleration
     * @return motor output, often in volts
     */
    protected abstract double calculate(double velocity, double acceleration);

    /**
     * A feedforward model for a DC motor based on the voltage-balance equation.
     *
     * <p>Volts = kS * sgn(velocity) + kV * velocity + kA * acceleration
     *
     * @author Myles Pasetsky (myles.pasetsky@gmail.com)
     */
    public static class Motor extends Feedforward {

        /** Coefficients representing dynamics of the motor */
        private final Number kS, kV, kA;

        /**
         * Create a feedforward model for a motor
         *
         * @param kS volts, describes portion of voltage to overcome static friction
         * @param kV volts * seconds / distance, describes voltage needed to hold constant velocity
         * @param kA volts * seconds^2 / distance, describes voltage needed to move at an
         *     acceleration
         */
        public Motor(Number kS, Number kV, Number kA) {
            this.kS = kS;
            this.kV = kV;
            this.kA = kA;
        }

        /**
         * Calculates volts to move at a desired velocity and acceleration
         *
         * <p>V = kS * sgn(velocity) + kV * velocity + kA * acceleration
         *
         * @param velocity desired velocity
         * @param acceleration desired acceleration
         * @return volts to move at desired velocity and acceleration
         */
        protected double calculate(double velocity, double acceleration) {
            return kS.doubleValue() * Math.signum(velocity)
                    + kV.doubleValue() * velocity
                    + kA.doubleValue() * acceleration;
        }
    }

    /**
     * A feedforward model for a drivetrain, which is the same as a motor feedforward model.
     *
     * @author Myles Pasetsky (myles.pasetsky@gmail.com)
     */
    public static class Drivetrain extends Motor {
        /**
         * Create a feedforward model for a drivetrain
         *
         * @param kS volts, describes portion of voltage to overcome static friction
         * @param kV volts * seconds / distance, describes voltage needed to hold constant velocity
         * @param kA volts * seconds^2 / distance, describes voltage needed to move at an
         *     acceleration
         */
        public Drivetrain(Number kS, Number kV, Number kA) {
            super(kS, kV, kA);
        }
    }

    /**
     * A feedforward model for a flywheel, which is the same as a motor feedforward model.
     *
     * @author Myles Pasetsky (myles.pasetsky@gmail.com)
     */
    public static class Flywheel extends Motor {
        /**
         * Create a feedforward model for a flywheel
         *
         * @param kS volts, describes portion of voltage to overcome static friction
         * @param kV volts * seconds / distance, describes voltage needed to hold constant velocity
         * @param kA volts * seconds^2 / distance, describes voltage needed to move at an
         *     acceleration
         */
        public Flywheel(Number kS, Number kV, Number kA) {
            super(kS, kV, kA);
        }
    }

    /**
     * Create a feedforward model for an elevator, which is a motor model that additionally accounts
     * for gravity.
     *
     * <p>V = kG + kS * sgn(velocity) + kV * velocity + kA * acceleration
     *
     * @author Myles Pasetsky (myles.pasetsky@gmail.com)
     */
    public static class Elevator extends Motor {
        /** Additional term to account for gravity */
        private final Number kG;

        /**
         * Create a feedforward model for an elevator
         *
         * @param kG volts, added to account for gravity
         * @param kS volts, describes portion of voltage to overcome static friction
         * @param kV volts * seconds / distance, describes voltage needed to hold constant velocity
         * @param kA volts * seconds^2 / distance, describes voltage needed to move at an
         *     acceleration
         */
        public Elevator(Number kG, Number kS, Number kV, Number kA) {
            super(kS, kV, kA);
            this.kG = kG;
        }

        /**
         * Calculates a motor voltage for an elevator system.
         *
         * @param velocity desired velocity
         * @param acceleration desired acceleration
         * @return volts to account for elevator movement
         */
        @Override
        protected double calculate(double velocity, double acceleration) {
            return kG.doubleValue() + super.calculate(velocity, acceleration);
        }
    }
}
