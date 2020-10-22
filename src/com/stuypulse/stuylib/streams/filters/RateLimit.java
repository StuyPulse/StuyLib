package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.math.SLMath;

/**
 * This class lets you rate limit a stream of inputs
 *
 * That means that the value can not change more than a specified amount in one update
 *
 * This is not time dependant, so the values will change if you change the rate that you call this
 * filter, the filter will not adapt for that.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class RateLimit extends IFilterGroup {

    // Used to limit the change from the last value
    private double mLastValue;
    private double mRateLimit;

    /**
     * Makes a new rate limiter with specified rate limit
     *
     * @param rateLimit desired rate limit
     */
    public RateLimit(double rateLimit) {
        if(rateLimit < 0.0) {
            throw new IllegalArgumentException(
                    "rateLimit must be a positive number");
        }

        mLastValue = 0;
        mRateLimit = 0;
    }

    public double get(double next) {
        return mLastValue += SLMath.limit(next, mRateLimit);
    }
}
