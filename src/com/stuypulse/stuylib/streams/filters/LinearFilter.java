package com.stuypulse.stuylib.streams.filters;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Simple implementation of an Simple Moving Average
 *
 * This is not time dependant, so the values will change if you change the rate that you call this
 * filter, the filter will not adapt for that.
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
    public LinearFilter(double... weights) {
        mWeights = Arrays.copyOf(weights, weights.length);

        mTotal = 0.0;
        for(double i : mWeights) {
            mTotal += i;
        }

        if(mTotal == 0.0) {
            throw new IllegalArgumentException(
                    "the sum of weights cannot be 0.0");
        } else if(mTotal < 0.0) {
            System.err.println(
                    "weights for linear filter sum to negative number!");
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
     *
     * @param size size of moving average
     * @return the moving average
     */
    public static IStreamFilter getMovingAverage(int size) {
        // Custom moving average class is much faster
        return new MovingAverage(size);
    }

    /**
     * Get a weighted moving average with a certain size
     *
     * @param size size of weighted moving average
     * @return the weighted moving average
     */
    public static IStreamFilter getWeightedMovingAverage(int size) {
        // Custom moving average class is much faster
        return new WeightedMovingAverage(size);
    }

    /**
     * Trapezoidal Moving Average is used to get a perfect S Curve.
     *
     * @param size the size of the trapezoidal moving average
     * @param cap  the maximum weight for the past values (less than half the size)
     * @return the TrapezoidalMovingAverage
     */
    public static IStreamFilter getTrapezoidalMovingAverage(int size, int cap) {
        double[] weights = new double[size];

        for(int i = 0; i < size; ++i) {
            if(i < size / 2) {
                weights[i] = i + 1;
            } else {
                weights[i] = size - i;
            }

            if(cap > 0) {
                weights[i] = Math.min(weights[i], cap);
            }
        }

        return new LinearFilter(weights);
    }

    /**
     * Trapezoidal Moving Average is used to get a perfect S Curve.
     *
     * @param size the size of the trapezoidal moving average
     * @return the TrapezoidalMovingAverage
     */
    public static IStreamFilter getTrapezoidalMovingAverage(int size) {
        return getTrapezoidalMovingAverage(size, -1);
    }
}
