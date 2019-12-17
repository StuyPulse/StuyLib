package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.exception.ConstructionError;

/**
 * Simple implementation of an Exponential Moving Average
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class RollingAverage implements IStreamFilter {

    private double mValue; 
    private double mWeight;

    /**
     * Make an Exponential Moving Average If exp = 1, it will instantly update The
     * weight must be greater than or equal to 1. The higher the weight, the longer
     * it takes to change value
     * 
     * @param weight weight (greater than or equal to 1)
     */
    public RollingAverage(double weight) throws ConstructionError {
        if (weight <= 0.5) {
            throw new ConstructionError("RollingAverage(double weight)", "weigth must be greater than 0.5!");
        }

        mValue = 0;
        mWeight = 1.0 / weight;
    }

    public double get(double next) {
        return mValue += (next - mValue) * mWeight;
    }
}