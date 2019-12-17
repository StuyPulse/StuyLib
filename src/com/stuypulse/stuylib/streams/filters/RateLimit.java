package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.exception.ConstructionError;
import com.stuypulse.stuylib.math.SLMath;

/**
 * This class lets you rate limit a stream of inputs
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class RateLimit implements IStreamFilter {

    private double mLastValue;
    private double mRateLimit;

    /**
     * Makes a new rate limiter with specified rate limit
     * 
     * @param rateLimit desired rate limit
     */
    public RateLimit(double rateLimit) throws ConstructionError {
        if (rateLimit <= 0) {
            throw new ConstructionError("RateLimit(double rateLimit)", "rateLimit must be greater than 0!");
        }

        mLastValue = 0;
        mRateLimit = rateLimit;
    }

    public double get(double next) {
        return mLastValue += SLMath.limit(next - mLastValue, mRateLimit);
    }
}