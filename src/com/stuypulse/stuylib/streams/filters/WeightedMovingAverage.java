/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

/**
 * Implementation of Weighted Moving Average. In a Weighted moving average, each value in the
 * average buffer is weighted linearly based on their position in the buffer. The most recent item
 * is weighted the most, and the last item is weighted the least.
 *
 * <p>This implementation is very fast, however, it is also very hard to explain. It does however
 * correctly calculate a weighted moving average.
 *
 * <p>This implementation is O(1) unlike many other implementations of this algorithm.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class WeightedMovingAverage implements IFilter {

    private int mSize;
    private double mTotal;
    private double mLastAverage;
    private MovingAverage mAverage;

    /** @param size size of weighted moving average */
    public WeightedMovingAverage(int size) {
        if (size <= 0) {
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
