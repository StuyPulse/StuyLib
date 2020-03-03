package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * This class lets you rate limit a stream of inputs
 *
 * Instead of being based on the rate that update is called, the value you give it is based on how
 * much it should be able to change in one second.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class TimedRateLimit implements IStreamFilter {

    // Used to get the time since the last get call
    private StopWatch mTimer;

    // Used to take the difference and
    private double mLastValue;
    private double mRateLimit;

    /**
     * @param rateLimit The amount that the value should be able to change in one second.
     */
    public TimedRateLimit(double rateLimit) {
        if(rateLimit <= 0) {
            throw new IllegalArgumentException(
                    "rateLimit must be a positive number");
        }

        mTimer = new StopWatch();
        mRateLimit = rateLimit;
        mLastValue = 0;
    }

    public double get(double next) {
        return mLastValue += SLMath.limit(next - mLastValue,
                mRateLimit * mTimer.reset());
    }
}
