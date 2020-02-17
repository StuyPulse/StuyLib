package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.exception.ConstructionError;

/**
 * Implementation of Inverted Hull Moving Average.
 * 
 * Hull Moving Average is very jerky, and thus inverting produces an S-Curve
 * like thing.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class InvHullMovingAverage implements IStreamFilter {

    private IStreamFilter mFilterA;
    private IStreamFilter mFilterB;
    private IStreamFilter mFilterC;

    /**
     * Make Simple Moving Average with Max Array Size
     * 
     * @param size size of moving average
     */
    public InvHullMovingAverage(int size) throws ConstructionError {
        if (size <= 0) {
            throw new ConstructionError("MovingAverage(int size)", "size must be greater than 0!");
        }

        mFilterA = new WeightedMovingAverage(size);
        mFilterB = new WeightedMovingAverage(size / 2);
        mFilterC = new WeightedMovingAverage((int) Math.sqrt(size));
    }

    public double get(double next) {
        return mFilterC.get(mFilterA.get(next) * 2 - mFilterB.get(next));
    }
}
