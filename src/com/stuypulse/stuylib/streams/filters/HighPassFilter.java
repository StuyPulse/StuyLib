package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.util.StopWatch;

/**
 * Implementation for HighPassFilter for IStreams
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class HighPassFilter implements IFilter {

    private StopWatch mTimer;

    private double mLastValue;
    private double mLastInput;
    private double mRC;

    /**
     * @param rc time constant for high pass filter
     */
    public HighPassFilter(double rc) {
        if(rc < 0) {
            throw new IllegalArgumentException("rc must be a positive number");
        }

        mTimer = new StopWatch();
        mLastValue = 0;
        mLastInput = 0;
        mRC = rc;
    }

    public double get(double next) {
        double dt = mTimer.reset();
        double a = mRC / (mRC + dt);
        mLastValue = a * (mLastValue + next - mLastInput);
        mLastInput = next;
        return mLastValue;
    }
}
