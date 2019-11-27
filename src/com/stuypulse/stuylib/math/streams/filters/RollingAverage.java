package com.stuypulse.stuylib.math.streams.filters;

import com.stuypulse.stuylib.math.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.math.SLMath;

/**
 * Simple implementation of an Exponential Moving Average
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class RollingAverage implements IStreamFilter {

    private double mValue; // Current Value
    private double mWeight; // Weight

    /**
     * Make an Exponential Moving Average If exp = 1, it will instantly update The
     * weight must be greater than or equal to 1. The higher the weight, the longer
     * it takes to change value
     * 
     * @param weight weight (greater than or equal to 1)
     */
    public RollingAverage(double weight) throws RuntimeException {
        mValue = 0;
        mWeight = 1.0 / weight;

        if(mWeight >= 2) {
            throw new RuntimeException("RollingAverage(double weight) -> weigth value must be > 0.5!");
        }
    }

    /**
     * @param next next value in stream
     * @return next value
     */
    public double get(double next) {
        return mValue += (next - mValue) * mWeight;
    }
}