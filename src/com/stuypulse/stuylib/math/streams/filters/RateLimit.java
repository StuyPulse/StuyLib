package com.stuypulse.stuylib.math.streams.filters;

import com.stuypulse.stuylib.math.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.math.InputScaler;

/**
 * This class lets you rate limit a stream of inputs
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class RateLimit implements IStreamFilter {

    private double mLastValue; // Stores current value
    private double mRateLimit; // Stores max rate limit

    /**
     * Makes a new rate limiter with 
     * specified rate limit
     * @param rateLimit desired rate limit
     */
    public RateLimit(double rateLimit) {
        mLastValue = 0;
        mRateLimit = rateLimit;
    }

    /**
     * @param next next value in stream
     * @return next value
     */
    public double get(double next) {
        return mLastValue += InputScaler.limit(next - mLastValue, mRateLimit);
    }
}