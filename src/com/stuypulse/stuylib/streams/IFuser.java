/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.streams.filters.HighPassFilter;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

/**
 * A class that combines two IStreams, usually in order to combine some slow data source with a
 * faster one. The Base Measurement should generally drift less while the Fast Measurement should
 * have less delay. The Fast measurement will go through a high pass filter so it is less important
 * if that one drifts compared to the Base Measurement.
 *
 * <p>Example Usage: BaseMeasurement = Limelight, FastMeasurement = Encoders
 *
 * @author Myles Pasetsky
 */
public class IFuser implements IStream {

    private final Number mFilterRC;

    private final IStream mBase;
    private final IStream mFast;

    private IFilter mBaseFilter;
    private IFilter mFastFilter;

    private double mFastOffset;

    /**
     * Create an IFuser with an RC, Base/Fast Measurement stream
     *
     * @param rc RC value for the lowpass / highpass filters
     * @param baseMeasurement a stream that returns the slow, but accurate measurement values
     * @param fastMeasurement a stream that returns faster, less accurate measurement values
     */
    public IFuser(Number rc, IStream baseMeasurement, IStream fastMeasurement) {
        mBase = baseMeasurement;
        mFast = fastMeasurement;

        mFilterRC = rc;

        reset();
    }

    /** Resets the IFuser so that it can ignore any previous data / reset its initial read */
    public void reset() {
        mBaseFilter = new LowPassFilter(mFilterRC);
        mFastFilter = new HighPassFilter(mFilterRC);

        mFastOffset = mBase.get() - mFast.get();
    }

    private double getBase() {
        return mBaseFilter.get(mBase.get());
    }

    private double getFast() {
        return mFastFilter.get(mFast.get() + mFastOffset);
    }

    /** Get the result of merging the two datastreams together */
    public double get() {
        return getBase() + getFast();
    }
}
