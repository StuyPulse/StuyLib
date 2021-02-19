// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

package com.stuypulse.stuylib.streams.filters;

/**
 * Simple implementation of an Exponential Moving Average
 *
 * <p>This is not time dependant, so the values will change if you change the rate that you call
 * this filter, the filter will not adapt for that.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class ExpMovingAverage implements IFilter {

    // Used to limit the change from the last value
    private double mLastValue;
    private Number mWeight;

    /**
     * Make an Exponential Moving Average If exp = 1, it will instantly update The weight must be
     * greater than or equal to 1. The higher the weight, the longer it takes to change value
     *
     * @param weight weight (greater than or equal to 1)
     */
    public ExpMovingAverage(Number weight) {
        if (weight.doubleValue() <= 1.0) {
            throw new IllegalArgumentException("weight must be > 1");
        }

        mLastValue = 0;
        mWeight = weight;
    }

    public double get(double next) {
        return mLastValue += (next - mLastValue) / mWeight.doubleValue();
    }
}
