/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.streams.filters.IFilter;

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
