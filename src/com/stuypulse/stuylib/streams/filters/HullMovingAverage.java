package com.stuypulse.stuylib.streams.filters;

/**
 * Implementation of Hull Moving Average.
 *
 * Hull Moving Average can smooth out inputs, but has a tendency to overshoot. It is really good if
 * lag is unacceptable, but not if overshooting based on quick changes is bad.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class HullMovingAverage implements IStreamFilter {

    private IStreamFilter mFilterA;
    private IStreamFilter mFilterB;
    private IStreamFilter mFilterC;

    /**
     * Make Simple Moving Average with Max Array Size
     *
     * @param size size of moving average
     */
    public HullMovingAverage(int size) {
        if(size <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }

        mFilterA = new WeightedMovingAverage(size);
        mFilterB = new WeightedMovingAverage(size / 2);
        mFilterC = new WeightedMovingAverage((int) Math.sqrt(size));
    }

    public double get(double next) {
        return mFilterC.get(mFilterB.get(next) * 2 - mFilterA.get(next));
    }
}
