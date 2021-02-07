package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.util.StopWatch;

/**
 * Implementation for of a real time IIR HighPassFilter
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class HighPassFilter implements IFilter {

    private StopWatch mTimer;

    private double mLastValue;
    private double mLastInput;
    private Number mRC;

    /**
     * @param rc time constant for high pass filter
     */
    public HighPassFilter(Number rc) {
        if(rc.doubleValue() < 0) {
            throw new IllegalArgumentException("rc must be a positive number");
        }

        mTimer = new StopWatch();
        mLastValue = 0;
        mLastInput = 0;
        mRC = rc;
    }

    public double get(double next) {
        double dt = mTimer.reset();
        double a = mRC.doubleValue() / (mRC.doubleValue() + dt);
        mLastValue = a * (mLastValue + next - mLastInput);
        mLastInput = next;
        return mLastValue;
    }
}
