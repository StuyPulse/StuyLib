// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.


package com.stuypulse.stuylib.streams.filters;

import java.util.LinkedList;
import java.util.Queue;

/**
 * An implementation of an Simple Moving Average
 *
 * <p>This is not time dependant, so the values will change if you change the rate that you call
 * this filter, the filter will not adapt for that.
 *
 * <p>This implementation is O(1) unlike many other implementations of this algorithm.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class MovingAverage implements IFilter {

    private int mSize;
    private double mTotal;
    private Queue<Double> mValues;

    /**
     * Make Simple Moving Average with Max Array Size
     *
     * @param size size of moving average
     */
    public MovingAverage(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }

        mSize = size;
        mValues = new LinkedList<>();
        mTotal = 0.0;

        while (mValues.size() < mSize) {
            mValues.add(0.0);
        }
    }

    public double get(double next) {
        // Remove old value
        mTotal -= mValues.remove();

        // Add new value
        mValues.add(next);
        mTotal += next;

        // Return average
        return mTotal / mSize;
    }
}
