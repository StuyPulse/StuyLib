/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.streams.filters.IFilter;

/**
 * A controller calculates an output variable given a setpoint and measurement of a single variable.
 *
 * <p>This base class can be used to represent single-input single-output control (SISO) algorithms
 * (commonly PID and feedforward).
 *
 * <p>For any controller, digital filters can be applied to the incoming setpoints and measurements,
 * or the outgoing outputs. This allows for the easy application of filters often involved with
 * control theory, like motion profile filters for setpoints and low-pass filters for noisy
 * measurements. *These filters are already provided in the StuyLib filters library.*
 *
 * <p>Different control schemes that share the same setpoint and measurement can also be concisely
 * composed together if they all implement this class.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public abstract class Controller {

    /** The most recent setpoint of the controller */
    private double mSetpoint;

    /** The most recent measurement of the controller */
    private double mMeasurement;

    /** The most recent output of the controller */
    private double mOutput;

    /** The filter that is applied to the supplied setpoints */
    private IFilter mSetpointFilter;

    /** The filter that is applied to the supplied measurements */
    private IFilter mMeasurementFilter;

    /** The filter that is applied to the calculated outputs */
    private IFilter mOutputFilter;

    /** Default initialization of a controller */
    public Controller() {
        mSetpoint = 0.0;
        mMeasurement = 0.0;
        mOutput = 0.0;

        mSetpointFilter = x -> x;
        mMeasurementFilter = x -> x;
        mOutputFilter = x -> x;
    }

    /**
     * Set the setpoint filter of the controller
     *
     * @param setpointFilter setpoint filters
     * @return reference to the controller
     */
    public final Controller setSetpointFilter(IFilter... setpointFilter) {
        mSetpointFilter = IFilter.create(setpointFilter);
        return this;
    }

    /**
     * Set the measurement filter of the controller
     *
     * @param measurementFilter measurement filters
     * @return reference to the controller
     */
    public final Controller setMeasurementFilter(IFilter... measurementFilter) {
        mMeasurementFilter = IFilter.create(measurementFilter);
        return this;
    }

    /**
     * Set the output filter of the controller
     *
     * @param outputFilter output filters
     * @return reference to the controller
     */
    public final Controller setOutputFilter(IFilter... outputFilter) {
        mOutputFilter = IFilter.create(outputFilter);
        return this;
    }

    /**
     * @return the most recent setpoint of the controller
     */
    public final double getSetpoint() {
        return mSetpoint;
    }

    /**
     * @return the most recent measurement of the controller
     */
    public final double getMeasurement() {
        return mMeasurement;
    }

    /**
     * @return the most recent output of the controller
     */
    public final double getOutput() {
        return mOutput;
    }

    /**
     * @return the most recent error of the controller
     */
    public final double getError() {
        return getSetpoint() - getMeasurement();
    }

    /**
     * Determines if the controller is finished based on an acceptable error.
     *
     * @param acceptableError acceptable error for the controller
     * @return whether or not the controller is done
     */
    public final boolean isDone(double acceptableError) {
        return Math.abs(getError()) < acceptableError;
    }

    /**
     * Combines this controller into a group with other controllers that share the same setpoint and
     * measurement.
     *
     * @param other the other controllers
     * @return the group of controllers that
     */
    public final ControllerGroup add(Controller... other) {
        return new ControllerGroup(this, other);
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
    public final double update(double setpoint, double measurement) {
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
    protected abstract double calculate(double setpoint, double measurement);
}
