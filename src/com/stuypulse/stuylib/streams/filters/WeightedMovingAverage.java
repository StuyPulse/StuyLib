package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.exception.ConstructionError;

/**
 * Implementation of Weighted Moving Average.
 * 
 * Very heavily optimized, may not be readable, but it is really fast.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class WeightedMovingAverage implements IStreamFilter {

    private double mTotal;
    private MovingAverage mValues;

    /**
     * @param size size of weighted moving average
     */
    public WeightedMovingAverage(int size) throws ConstructionError {
        if (size <= 0) {
            throw new ConstructionError("MovingAverage(int size)", "size must be greater than 0!");
        }

        mValues = new MovingAverage(size);
        mTotal = 0.0;
    }

    public double get(double next) {
        return mTotal += next - mValues.get(next);
    }
}
