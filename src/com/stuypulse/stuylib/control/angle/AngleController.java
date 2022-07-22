/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.streams.angles.filters.AFilter;
import com.stuypulse.stuylib.streams.filters.IFilter;

public abstract class AngleController {
    private Angle mSetpoint;
    private Angle mMeasurement;

    private AFilter mSetpointFilter;
    private AFilter mMeasurementFilter;
    private IFilter mOutputFilter;

    public AngleController() {
        mSetpoint = Angle.kZero;
        mMeasurement = Angle.kZero;

        mSetpointFilter = x -> x;
        mMeasurementFilter = x -> x;
        mOutputFilter = x -> x;
    }

    public AngleController setSetpointFilter(AFilter setpointFilter) {
        mSetpointFilter = setpointFilter;
        return this;
    }

    public AngleController setMeasurementFilter(AFilter measurementFilter) {
        mMeasurementFilter = measurementFilter;
        return this;
    }

    public AngleController setOutputFilter(IFilter outputFilter) {
        mOutputFilter = outputFilter;
        return this;
    }

    public Angle getSetpoint() {
        return mSetpoint;
    }

    public Angle getMeasurement() {
        return mMeasurement;
    }

    public double getError() {
        // TODO: add configurable units (for dummies)
        return getSetpoint().sub(getMeasurement()).toRadians();
    }

    public boolean isDone(double acceptableError) {
        return Math.abs(getError()) < acceptableError;
    }

    public AngleBinaryController and(AngleController other) {
        return new AngleBinaryController(this, other);
    }

    public double update(Angle setpoint, Angle measurement) {
        mSetpoint = mSetpointFilter.get(setpoint);
        mMeasurement = mMeasurementFilter.get(measurement);

        double output = calculate(mSetpoint, mMeasurement);
        return mOutputFilter.get(output);
    }

    protected abstract double calculate(Angle setpoint, Angle measurement);
}
