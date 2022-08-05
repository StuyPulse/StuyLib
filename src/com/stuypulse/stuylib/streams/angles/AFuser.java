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
 * faster one. The Base Measurement should generally drift less while the Fast Measurement should
 * have less delay. The Fast measurement will go through a high pass filter so it is less important
 * if that one drifts compared to the Base Measurement.
 *
 * <p>Example Usage: BaseMeasurement = Limelight, FastMeasurement = Encoders
 *
 * @author Myles Pasetsky
 */
public class AFuser implements AStream {

    private final Number mFilterRC;

    private final AStream mBase;
    private final AStream mFast;

    private AFilter mBaseFilter;
    private AFilter mFastFilter;

    private Angle mFastOffset;

    /**
     * Create an AFuser with an RC, Base/Fast Measurement stream
     *
     * @param rc RC value for the lowpass / highpass filters
     * @param baseMeasurement a stream that returns the slow, but accurate measurement values
     * @param fastMeasurement a stream that returns faster, less accurate measurement values
     */
    public AFuser(Number rc, AStream baseMeasurement, AStream fastMeasurement) {
        mBase = baseMeasurement;
        mFast = fastMeasurement;

        mFilterRC = rc;

        reset();
    }

    /** Resets the AFuser so that it can ignore any previous data / reset its initial read */
    public void reset() {
        mBaseFilter = new ALowPassFilter(mFilterRC);
        mFastFilter = new AHighPassFilter(mFilterRC);

        mFastOffset = mBase.get().sub(mFast.get());
    }

    private Angle getBase() {
        return mBaseFilter.get(mBase.get());
    }

    private Angle getFast() {
        return mFastFilter.get(mFast.get().add(mFastOffset));
    }

    /** Get the result of merging the two datastreams together */
    public Angle get() {
        return getBase().add(getFast());
    }
}
