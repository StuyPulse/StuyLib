package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.exception.ConstructionError;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Simple implementation of an Simple Moving Average
 *
 * This is not time dependant, so the values will change if you change the rate
 * that you call this filter, the filter will not adapt for that.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class LinearFilter implements IStreamFilter {

    private double[] mWeights;
    private double mTotal;
    private LinkedList<Double> mBuffer;

    /**
     * Create a LinearFilter with a list of weights
     * 
     * @param weights a list of weights to apply to the past values
     */
    public LinearFilter(double... weights) throws ConstructionError {
        mWeights = Arrays.copyOf(weights, weights.length);
        
        mTotal = 0.0;
        for(double i : mWeights) {
            mTotal += i;
        }

        mBuffer = new LinkedList<Double>();

        while(mBuffer.size() < mWeights.length) {
            mBuffer.addFirst(0.0);
        }
    }

    public double get(double next) {
        mBuffer.addFirst(next);

        if(mWeights.length < mBuffer.size()) {
            mBuffer.removeLast();
        }


        double total = 0.0;
        Iterator<Double> value = mBuffer.iterator();

        for(int i = 0; i < mWeights.length && value.hasNext(); ++i) {
            total += value.next() * mWeights[i];
        }

        return total / mTotal;
    }

    /**
     * Get a moving average with a certain size
     * @param size size of moving average
     * @return the moving average
     */
    public static IStreamFilter getMovingAverage(int size) {
        // Custom moving average class is much faster
        return new MovingAverage(size);
    }

    /**
     * Get a weighted moving average with a certain size
     * @param size size of weighted moving average
     * @return the weighted moving average
     */
    public static IStreamFilter getWeightedMovingAverage(int size) {
        // Custom moving average class is much faster
        return new WeightedMovingAverage(size);
    }

    /**
     * 
     */
    public static IStreamFilter getTrapezoidalMovingAverage(int size, int climbSize) {
        double[] weights = new double[size];

        for(int i = 0; i < size; ++i) {
            if(i < climbSize) {
                weights[i] = i + 1;
            } else if(i > size - climbSize) {
                weights[i] = size - i;
            } else {
                weights[i] = climbSize + 1;
            }
        }

        return new LinearFilter(weights);
    }
}
