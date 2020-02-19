package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.exception.ConstructionError;

/**
 * Implementation of Inverted Hull Moving Average.
 *
 * Hull Moving Average can overshoot, but when the order of the filters is
 * switched around, it can create an interesting S-Curve.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class InvertedHullMovingAverage implements IStreamFilter {

    private IStreamFilter mFilterA;
    private IStreamFilter mFilterB;
    private IStreamFilter mFilterC;

    /**
     * Make Simple Moving Average with Max Array Size
     *
     * @param size size of moving average
     */
    public InvertedHullMovingAverage(int size) throws ConstructionError {
        if(size <= 0) {
            throw new ConstructionError("InvertedHullMovingAverage(int size)",
                    "size must be greater than 0!");
        }

        mFilterA = new WeightedMovingAverage(size);
        mFilterB = new WeightedMovingAverage(size / 2);
        mFilterC = new WeightedMovingAverage((int) Math.sqrt(size));
    }

    public double get(double next) {
        return mFilterC.get(mFilterA.get(next) * 2 - mFilterB.get(next));
    }
}
