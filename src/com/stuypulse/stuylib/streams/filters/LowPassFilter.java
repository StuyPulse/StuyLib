package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.util.StopWatch;

/**
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class LowPassFilter implements IStreamFilter {

    // Used to get the time since the last get call
    private StopWatch mTimer;

    // Used to take the difference and
    private double mLastValue;
    private double mRC;

    /**
     * @param rc Time Constant. The time constant is the amount of time in seconds that it takes to get
     *           63.2% of the way to the target value. 63.2% is (1 - (1 / e)).
     */
    public LowPassFilter(double rc) {
        if(rc < 0) {
            throw new IllegalArgumentException("rc must be a positive number");
        }

        mTimer = new StopWatch();
        mLastValue = 0;
        mRC = rc;
    }

    public double get(double next) {
        double dt = mTimer.reset();
        double a = dt / (mRC + dt);
        return mLastValue += a * (next - mLastValue);
    }
}
