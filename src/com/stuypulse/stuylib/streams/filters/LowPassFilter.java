/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.util.StopWatch;

/**
 * Implementation of a real time IIR LowPassFilter
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class LowPassFilter implements IFilter {

    // Used to calculate time in between calls
    private final StopWatch mTimer;

    // Used to calculate next value based on previous value and time
    private final Number mRC;
    private double mLastValue;

    /**
     * @param rc Time Constant. The time constant is the amount of time in seconds that it takes to
     *     get 63.2% of the way to the target value. 63.2% is (1 - (1 / e)).
     */
    public LowPassFilter(Number rc) {
        if (rc.doubleValue() < 0) {
            throw new IllegalArgumentException("rc must be a positive number");
        }

        mTimer = new StopWatch();
        mLastValue = 0;
        mRC = rc;
    }

    public double get(double next) {
        // Get time since last .get() call
        double dt = mTimer.reset();

        // Get a constant, which is determined based on dt and the mRC constant
        double a = dt / (mRC.doubleValue() + dt);

        // Based on the value of a (which is determined by dt), the next value
        // could either change a lot, or not by much. (smaller dt = smaller change)
        return mLastValue += a * (next - mLastValue);
    }
}
