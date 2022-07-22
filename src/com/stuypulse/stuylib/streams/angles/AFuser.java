/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.streams.angles.filters.AFilter;
import com.stuypulse.stuylib.streams.angles.filters.AHighPassFilter;
import com.stuypulse.stuylib.streams.angles.filters.ALowPassFilter;

/**
 * A class that combines two AStreams, usually in order to combine some slow data source with a
 * faster one. The Setpoint AStream will return the error of some system, and the Measurement
 * AStream will return the value of the faster data source. The Measurement AStream should generally
 * move in the opposite direction of the Setpoint AStream.
 *
 * <p>Example Usage: Setpoint = Limelight, Measurement = Encoders
 *
 * @author Myles Pasetsky
 */
public class AFuser implements AStream {

    private final Number mFilterRC;

    private final AStream mSetpoint;
    private final AStream mMeasurement;

    private AFilter mSetpointFilter;
    private AFilter mMeasurementFilter;

    private Angle mInitialTarget;

    /**
     * Create an AFuser with an RC, Setpoint Filter, and Measurement Filter
     *
     * @param rc RC value for the lowpass / highpass filters
     * @param setpoint a filter that returns the error in a control loop
     * @param measurement a filter that returns an encoder / any measurement (should be negative of
     *     setpoint)
     */
    public AFuser(Number rc, AStream setpoint, AStream measurement) {
        mSetpoint = setpoint;
        mMeasurement = measurement;

        mFilterRC = rc;

        initialize();
    }

    /** Resets the AFuser so that it can ignore any previous data / reset its initial read */
    public void initialize() {
        mSetpointFilter = new ALowPassFilter(mFilterRC);
        mMeasurementFilter = new AHighPassFilter(mFilterRC);

        mInitialTarget = mSetpoint.get().add(mMeasurement.get());
    }

    private Angle getSetpoint() {
        return mSetpointFilter.get(mSetpoint.get());
    }

    private Angle getMeasurement() {
        return mMeasurementFilter.get(mInitialTarget.sub(mMeasurement.get()));
    }

    /** Get the result of merging the setpoint and measurement streams */
    public Angle get() {
        return getSetpoint().add(getMeasurement());
    }
}
