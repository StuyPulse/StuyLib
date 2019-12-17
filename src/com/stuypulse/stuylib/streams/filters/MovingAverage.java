package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.exception.ConstructionError;

import java.util.Queue;
import java.util.LinkedList;

/**
 * Simple implementation of an Simple Moving Average
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
    public MovingAverage(int size) throws ConstructionError {
        if (size <= 0) {
            throw new ConstructionError("MovingAverage(int size)", "size must be greater than 0!");
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
