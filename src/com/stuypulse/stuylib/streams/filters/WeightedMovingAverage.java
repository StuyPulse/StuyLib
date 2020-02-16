package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.exception.ConstructionError;

import java.util.Queue;
import java.util.LinkedList;

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
    private Queue<Double> mValues;

    /**
     * Make Simple Moving Average with Max Array Size
     * 
     * @param size size of moving average
     */
    public WeightedMovingAverage(int size) throws ConstructionError {
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

        // Remove sum from
        for(double a : mValues) { 
            mTotal -= a;
        }

        // Remove old value
        mValues.remove();

        // Add new value
        mValues.add(next);
        mTotal += next * mSize;

        // Return average
        return mTotal / ((mSize * mSize + mSize) * 0.5);
    }
}
