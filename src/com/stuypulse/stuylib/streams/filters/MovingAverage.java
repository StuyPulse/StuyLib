package com.stuypulse.stuylib.streams.filters;

import java.util.Queue;
import java.util.LinkedList;

/**
 * Simple implementation of an Simple Moving Average
 *
 * This is not time dependant, so the values will change if you change the rate that you call this
 * filter, the filter will not adapt for that.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class MovingAverage implements IStreamFilter {

    private int mSize;
    private double mTotal;
    private Queue<Double> mValues;

    /**
     * Make Simple Moving Average with Max Array Size
     *
     * @param size size of moving average
     */
    public MovingAverage(int size) {
        if(size <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }

        mSize = size;
        mValues = new LinkedList<>();
        mTotal = 0.0;

        while(mValues.size() < mSize) {
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
