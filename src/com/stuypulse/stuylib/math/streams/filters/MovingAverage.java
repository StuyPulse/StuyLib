package com.stuypulse.stuylib.math.streams.filters;

import com.stuypulse.stuylib.math.streams.filters.IStreamFilter;
import java.util.Queue;
import java.util.LinkedList;


/**
 * Simple implementation of an Simple Moving Average
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class MovingAverage implements IStreamFilter {

    private int mSize; // Size of Queue
    private double mTotal; // Sum of all the elements
    private Queue<Double> mValues; // Queue of Values 

    /**
     * Make Simple Moving Average with Max Array Size
     * @param size size of moving average
     */
    public MovingAverage(int size) {
        mSize = Math.max(size, 1);
        mValues = new LinkedList<>();
        mTotal = 0.0;

        while(mValues.size() < mSize) {
            mValues.add(0.0);
        }
    }

    /**
     * @param next next value in stream
     * @return next value
     */
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