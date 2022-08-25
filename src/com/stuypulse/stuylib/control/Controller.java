/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.streams.filters.IFilter;

/**
 * A controller calculates an output variable given a setpoint and measurement
 * of a single variable. 
 * 
 * This base class can be used to represent single-input single-output control 
 * (SISO) algorithms (commonly PID and feedforward). 
 * 
 * For any controller, digital filters can be applied to the incoming setpoints 
 * and measurements, or the outgoing outputs. This allows for the easy application 
 * of filters often involved with control theory, like motion profile filters for 
 * setpoints and low-pass filters for noisy measurements. *These filters are already
 * provided in the StuyLib filters library.*
 * 
 * Becasue many SISO control schemes can be implemented under this class, they can be
 * concisely composed together in another implementation.
 * 
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public abstract class Controller {

    private double mSetpoint;
    private double mMeasurement;

    private IFilter mSetpointFilter;
    private IFilter mMeasurementFilter;
    private IFilter mOutputFilter;

    public Controller() {
        mSetpoint = 0.0;
        mMeasurement = 0.0;

        mSetpointFilter = x -> x;
        mMeasurementFilter = x -> x;
        mOutputFilter = x -> x;
    }

    public final Controller setSetpointFilter(IFilter... setpointFilter) {
        mSetpointFilter = IFilter.create(setpointFilter);
        return this;
    }

    public final Controller setMeasurementFilter(IFilter... measurementFilter) {
        mMeasurementFilter = IFilter.create(measurementFilter);
        return this;
    }

    public final Controller setOutputFilter(IFilter... outputFilter) {
        mOutputFilter = IFilter.create(outputFilter);
        return this;
    }

    public final double getSetpoint() {
        return mSetpoint;
    }

    public final double getMeasurement() {
        return mMeasurement;
    }

    public final double getError() {
        return getSetpoint() - getMeasurement();
    }

    public final boolean isDone(double acceptableError) {
        return Math.abs(getError()) < acceptableError;
    }

    public final ControllerGroup add(Controller... other) {
        return new ControllerGroup(this, other);
    }

    public final double update(double setpoint, double measurement) {
        mSetpoint = mSetpointFilter.get(setpoint);
        mMeasurement = mMeasurementFilter.get(measurement);

        double output = calculate(mSetpoint, mMeasurement);
        return mOutputFilter.get(output);
    }

    protected abstract double calculate(double setpoint, double measurement);
}
