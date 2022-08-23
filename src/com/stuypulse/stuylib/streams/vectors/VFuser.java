/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.vectors.filters.VFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VHighPassFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VLowPassFilter;

/**
 * A class that combines two VStreams, usually in order to combine some slow data source with a
 * faster one. The Setpoint VStream will return the error of some system, and the Measurement
 * VStream will return the value of the faster data source. The Measurement VStream should generally
 * move in the opposite direction of the Setpoint VStream.
 *
 * <p>Example Usage: Setpoint = Limelight, Measurement = Encoders
 *
 * @author Myles Pasetsky
 */
public class VFuser implements VStream {

    private final Number mFilterRC;

    private final VStream mSetpoint;
    private final VStream mMeasurement;

    private VFilter mSetpointFilter;
    private VFilter mMeasurementFilter;

    private Vector2D mInitialTarget;

    /**
     * Create an VFuser with an RC, Setpoint Filter, and Measurement Filter
     *
     * @param rc RC value for the lowpass / highpass filters
     * @param setpoint a filter that returns the error in a control loop
     * @param measurement a filter that returns an encoder / any measurement (should be negative of
     *     setpoint)
     */
    public VFuser(Number rc, VStream setpoint, VStream measurement) {
        mSetpoint = setpoint;
        mMeasurement = measurement;

        mFilterRC = rc;

        initialize();
    }

    /** Resets the VFuser so that it can ignore any previous data / reset its initial read */
    public void initialize() {
        mSetpointFilter = new VLowPassFilter(mFilterRC);
        mMeasurementFilter = new VHighPassFilter(mFilterRC);

        mInitialTarget = mSetpoint.get().add(mMeasurement.get());
    }

    private Vector2D getSetpoint() {
        return mSetpointFilter.get(mSetpoint.get());
    }

    private Vector2D getMeasurement() {
        return mMeasurementFilter.get(mInitialTarget.sub(mMeasurement.get()));
    }

    /** Get the result of merging the setpoint and measurement streams */
    public Vector2D get() {
        return getSetpoint().add(getMeasurement());
    }
}
