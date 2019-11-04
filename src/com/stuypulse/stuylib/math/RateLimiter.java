package com.stuypulse.stuylib.math;

/**
 * This class lets you rate limit a stream of numbers
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class RateLimiter {

    private double mLastValue; // Stores current value
    private double mRateLimit; // Stores max rate limit

    /**
     * Makes a new rate limiter with 
     * specified rate limit
     * @param rateLimit desired rate limit
     */
    public RateLimiter(double rateLimit) {
        mLastValue = 0;
        mRateLimit = rateLimit;
    }

    /**
     * Limit change to whatever the rate limit is
     * @param delta amount of change
     * @return amount of change but limited
     */
    private double limit(double delta) {
        if(delta >  mRateLimit) { return  mRateLimit; }
        if(delta < -mRateLimit) { return -mRateLimit; }
        return delta;
    }

    /**
     * Limit inputted value
     * @param val desired value
     * @return limited value
     */
    public double get(double val) {
        mLastValue += limit(val - mLastValue);
        return mLastValue;
    }

}