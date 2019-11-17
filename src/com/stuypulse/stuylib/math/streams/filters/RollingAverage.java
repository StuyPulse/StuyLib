package com.stuypulse.stuylib.math.streams.filters;

import com.stuypulse.stuylib.math.streams.filters.IStreamFilter;

/**
 * Simple implementation of an Weighted Moving Average
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class RollingAverage implements IStreamFilter {

    private double mValue; // Current Value
    private double mWeight; // Weight

    /**
     * Make Moving Average with based on exponent
     * If exp = 1, it will instantly update
     * The weight must be greater than or equal to 1
     * The higher the weight, the longer it takes to update
     * @param weight weight (greater than or equal to 1)
     */
    public RollingAverage(double weight) {
        mValue = 0;
        mWeight = Math.max(1, weight);
    }

    /**
     * @param next next value in stream
     * @return next value
     */
    public double get(double next) {
        return mValue += (next - mValue) / mWeight;
    }
}