package com.stuypulse.stuylib.streams.filters;

/**
 * Implementation of Weighted Moving Average.
 *
 * Very heavily optimized, may not be readable, but it is really fast.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class WeightedMovingAverage implements IStreamFilter {

    private int mSize;
    private double mTotal;
    private double mLastAverage;
    private MovingAverage mAverage;

    /**
     * @param size size of weighted moving average
     */
    public WeightedMovingAverage(int size) {
        if(size <= 0) {
            throw new IllegalArgumentException("size must be greater than 0!");
        }

        mSize = size;
        mAverage = new MovingAverage(size);
        mLastAverage = 0.0;
        mTotal = 0.0;
    }

    public double get(double next) {
        mTotal -= mLastAverage;
        mLastAverage = mAverage.get(next);
        return (mTotal += next) / ((mSize + 1) * 0.5);
    }
}
