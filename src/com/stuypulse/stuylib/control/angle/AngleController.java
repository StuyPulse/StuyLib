/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.streams.angles.filters.AFilter;
import com.stuypulse.stuylib.streams.filters.IFilter;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;

/**
 * Base class of controller classes of continuous systems. This means that both the setpoint and
 * measurement are angles, as opposed to just numbers.
 *
 * <p>Other than this, works the same as the Controller class.
 *
 * @see com.stuypulse.stuylib.math.Angle
 * @see com.stuypulse.stuylib.control.Controller
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public abstract class AngleController implements Sendable {

    /** The most recent setpoint of the controller */
    private Angle mSetpoint;

    /** The most recent measurement of the controller */
    private Angle mMeasurement;

    /** The most recent output of the controller */
    private double mOutput;

    /** The filter that is applied to the supplied setpoints */
    private AFilter mSetpointFilter;

    /** The filter that is applied to the supplied measurements */
    private AFilter mMeasurementFilter;

    /** The filter that is applied to the calculated outputs */
    private IFilter mOutputFilter;

    /** Default initialization of an angle controller */
    public AngleController() {
        mSetpoint = Angle.kZero;
        mMeasurement = Angle.kZero;
        mOutput = 0.0;

        mSetpointFilter = x -> x;
        mMeasurementFilter = x -> x;
        mOutputFilter = x -> x;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Angle Controller");
        builder.addDoubleProperty("Angle Controller Setpoint", () -> getSetpoint().toDegrees(), null);
        builder.addDoubleProperty("Angle Controller Measurement", () -> getMeasurement().toDegrees(), null);
        builder.addDoubleProperty("Angle Controller Output", this::getOutput, null);
        builder.addDoubleProperty("Angle Controller Error", () -> getError().toDegrees(), null);
    }

    /**
     * Set the setpoint filter of the controller
     *
     * @param setpointFilter angular setpoint filters
     * @return reference to the controller
     */
    public final AngleController setSetpointFilter(AFilter... setpointFilter) {
        mSetpointFilter = AFilter.create(setpointFilter);
        return this;
    }

    /**
     * Set the measurement filter of the controller
     *
     * @param measurementFilter angular measurement filters
     * @return reference to the controller
     */
    public final AngleController setMeasurementFilter(AFilter... measurementFilter) {
        mMeasurementFilter = AFilter.create(measurementFilter);
        return this;
    }

    /**
     * Set the output filter of the controller
     *
     * @param outputFilter output filters
     * @return reference to the controller
     */
    public final AngleController setOutputFilter(IFilter... outputFilter) {
        mOutputFilter = IFilter.create(outputFilter);
        return this;
    }

    /** @return the most recent setpoint of the controller */
    public final Angle getSetpoint() {
        return mSetpoint;
    }

    /** @return the most recent measurement of the controller */
    public final Angle getMeasurement() {
        return mMeasurement;
    }

    /** @return the most recent output of the controller */
    public final double getOutput() {
        return mOutput;
    }

    /** @return the most recent error of the controller */
    public final Angle getError() {
        return getSetpoint().sub(getMeasurement());
    }

    /**
     * Determines if the controller is finished based on an acceptable radian error.
     *
     * @param acceptableError acceptable error for the controller
     * @return whether or not the controller is done
     */
    public final boolean isDoneRadians(double acceptableError) {
        return Math.abs(getError().toRadians()) < acceptableError;
    }

    /**
     * Determines if the controller is finished based on an acceptable degree error.
     *
     * @param acceptableError acceptable error for the controller
     * @return whether or not the controller is done
     */
    public final boolean isDoneDegrees(double acceptableError) {
        return Math.abs(getError().toDegrees()) < acceptableError;
    }

    /**
     * Combines this controller into a group with other controllers that share the same setpoint and
     * measurement.
     *
     * @param other the other controllers
     * @return the group of controllers that
     */
    public final AngleControllerGroup add(AngleController... other) {
        return new AngleControllerGroup(this, other);
    }

    /**
     * Updates the state of the controller.
     *
     * <p>Applies filters to setpoint and measurement, calculates output with filtered values,
     * filters and returns output
     *
     * @param setpoint setpoint of the variable being controlled
     * @param measurement measurement of the variable being controlled
     * @return the final output
     */
    public final double update(Angle setpoint, Angle measurement) {
        mSetpoint = mSetpointFilter.get(setpoint);
        mMeasurement = mMeasurementFilter.get(measurement);

        return mOutput = mOutputFilter.get(calculate(mSetpoint, mMeasurement));
    }

    /**
     * Calculates the output of the controller given a setpoint and measurement.
     *
     * @param setpoint setpoint
     * @param measurement measurement
     * @return calculated output
     */
    protected abstract double calculate(Angle setpoint, Angle measurement);
}
