package com.stuypulse.stuylib.math.stream.filter;

import com.stuypulse.stuylib.math.stream.filter.StreamFilter;
import java.util.ArrayDeque;

/**
 * Simple implementation of an Simple Moving Average
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class MovingAverage implements StreamFilter {

    private int mMaxSize; // Max Size of ArrayDeque
    private ArrayDeque<Double> mValues; // Array of Values 

    /**
     * Make Simple Moving Average with Max Array Size
     * @param maxSize max size of moving average
     */
    public MovingAverage(int maxSize) {
        mMaxSize = Math.max(maxSize, 1);
        mValues = new ArrayDeque<Double>();
    }

    /**
     * @return current value
     */
    public double get() {
        // Check if array has any values
        if(mValues.size() > 0) {
            return mValues.getLast();
        } else {
            return 0.0;
        }
    }

    /**
     * @param next next value in stream
     * @return next value
     */
    public double get(double next) {
        // Add new value
        mValues.addLast(next);

        // Remove old values if size is too big
        if(mValues.size() > mMaxSize) {
            mValues.removeFirst();
        }

        // Get average of values in array
        double average = 0.0;
        for(double item : mValues) {
            average += item;
        }
        average /= mValues.size();

        // Return average
        return average;
    }
}