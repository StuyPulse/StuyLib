// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.


package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.math.SLMath;

/**
 * This class lets you rate limit a stream of inputs
 *
 * <p>That means that the value can not change more than a specified amount in one update
 *
 * <p>This is not time dependant, so the values will change if you change the rate that you call
 * this filter, the filter will not adapt for that.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class RateLimit implements IFilter {

    // Used to limit the change from the last value
    private double mLastValue;
    private Number mRateLimit;

    /**
     * Makes a new rate limiter with specified rate limit
     *
     * @param rateLimit desired rate limit
     */
    public RateLimit(Number rateLimit) {
        if (rateLimit.doubleValue() < 0.0) {
            throw new IllegalArgumentException("rateLimit must be a positive number");
        }

        mLastValue = 0;
        mRateLimit = 0;
    }

    public double get(double next) {
        return mLastValue += SLMath.clamp(next - mLastValue, mRateLimit.doubleValue());
    }
}
