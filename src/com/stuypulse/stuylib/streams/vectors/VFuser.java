/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.vectors.filters.VFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VHighPassFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VLowPassFilter;

/**
 * A class that combines two VStreams, usually in order to combine some slow data source with a
 * faster one. The Base Measurement should generally drift less while the Fast Measurement should
 * have less delay. The Fast measurement will go through a high pass filter so it is less important
 * if that one drifts compared to the Base Measurement.
 *
 * <p>Example Usage: BaseMeasurement = Limelight, FastMeasurement = Encoders
 *
 * @author Myles Pasetsky
 */
public class VFuser implements VStream {

    private final Number mFilterRC;

    private final VStream mBase;
    private final VStream mFast;

    private VFilter mBaseFilter;
    private VFilter mFastFilter;

    private Vector2D mFastOffset;

    /**
     * Create an VFuser with an RC, Base/Fast Measurement stream
     *
     * @param rc RC value for the lowpass / highpass filters
     * @param baseMeasurement a stream that returns the slow, but accurate measurement values
     * @param fastMeasurement a stream that returns faster, less accurate measurement values
     */
    public VFuser(Number rc, VStream baseMeasurement, VStream fastMeasurement) {
        mBase = baseMeasurement;
        mFast = fastMeasurement;

        mFilterRC = rc;

        reset();
    }

    /** Resets the VFuser so that it can ignore any previous data / reset its initial read */
    public void reset() {
        mBaseFilter = new VLowPassFilter(mFilterRC);
        mFastFilter = new VHighPassFilter(mFilterRC);

        mFastOffset = mBase.get().sub(mFast.get());
    }

    private Vector2D getBase() {
        return mBaseFilter.get(mBase.get());
    }

    private Vector2D getFast() {
        return mFastFilter.get(mFast.get().add(mFastOffset));
    }

    /** Get the result of merging the two datastreams together */
    public Vector2D get() {
        return getBase().add(getFast());
    }
}
