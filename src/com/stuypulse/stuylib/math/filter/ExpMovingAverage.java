package com.stuypulse.stuylib.math.filter;

import com.stuypulse.stuylib.math.filter.StreamFilter;

/**
 * Simple implementation of an Exponential Moving Average
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class ExpMovingAverage implements StreamFilter {

    private double mVal; // Current Value
    private double mExp; // Exponent

    /**
     * Make Moving Average with based on exponent
     * If exp = 1, it will instantly update
     * The exponent must be greater than or equal to 1
     * @param exp exponent (greater than or equal to 1)
     */
    public ExpMovingAverage(double exp) {
        mVal = 0;
        mExp = Math.max(1, exp);
    }

    /**
     * @return current value
     */
    public double get() {
        return mVal;
    }

    /**
     * @param next next value in stream
     * @return next value
     */
    public double get(double next) {
        return mVal += (next - mVal) / mExp;
    }
}