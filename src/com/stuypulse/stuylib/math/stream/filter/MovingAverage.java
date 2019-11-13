package com.stuypulse.stuylib.math.stream.filter;

import com.stuypulse.stuylib.math.stream.filter.StreamFilter;
import java.util.Queue;
import java.util.ArrayDeque;


/**
 * Simple implementation of an Simple Moving Average
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class MovingAverage implements StreamFilter {

    private int mSize; // Size of Queue
    private double mTotal; // Sum of all the elements
    private Queue<Double> mValues; // Queue of Values 

    /**
     * Make Simple Moving Average with Max Array Size
     * @param size size of moving average
     */
    public MovingAverage(int size) {
        mSize = Math.max(size, 1);
        mValues = new ArrayDeque<Double>(mSize);
        mTotal = 0.0;
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