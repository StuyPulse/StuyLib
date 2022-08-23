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

    public final AngleController setSetpointFilter(AFilter... setpointFilter) {
        mSetpointFilter = AFilter.create(setpointFilter);
        return this;
    }

    public final AngleController setMeasurementFilter(AFilter... measurementFilter) {
        mMeasurementFilter = AFilter.create(measurementFilter);
        return this;
    }

    public final AngleController setOutputFilter(IFilter... outputFilter) {
        mOutputFilter = IFilter.create(outputFilter);
        return this;
    }

    public final Angle getSetpoint() {
        return mSetpoint;
    }

    public final Angle getMeasurement() {
        return mMeasurement;
    }

    public final Angle getError() {
        return getSetpoint().sub(getMeasurement());
    }

    public final boolean isDoneRadians(double acceptableError) {
        return Math.abs(getError().toRadians()) < acceptableError;
    }

    public final boolean isDoneDegrees(double acceptableError) {
        return Math.abs(getError().toDegrees()) < acceptableError;
    }

    public final AngleControllerGroup add(AngleController... other) {
        return new AngleControllerGroup(this, other);
    }

    public final double update(Angle setpoint, Angle measurement) {
        mSetpoint = mSetpointFilter.get(setpoint);
        mMeasurement = mMeasurementFilter.get(measurement);

        double output = calculate(mSetpoint, mMeasurement);
        return mOutputFilter.get(output);
    }

    protected abstract double calculate(Angle setpoint, Angle measurement);
}
