/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles.filters;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * Implementation of a real time IIR LowPassFilter
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class ALowPassFilter implements AFilter {

    // Used to calculate time in between calls
    private final StopWatch mTimer;

    // Used to calculate next value based on previous value and time
    private final Number mRC;
    private Angle mLastValue;

    /**
     * @param rc Time Constant. The time constant is the amount of time in seconds that it takes to
     *     get 63.2% of the way to the target value. 63.2% is (1 - (1 / e)).
     */
    public ALowPassFilter(Number rc) {
        mTimer = new StopWatch();
        mLastValue = Angle.kZero;
        mRC = rc;
    }

    public Angle get(Angle next) {
        // If RC will cause errors, disable filter
        if (mRC.doubleValue() <= 0.0) {
            return mLastValue = next;
        }

        // Get a constant, which is determined based on dt and the mRC constant
        double a = Math.exp(-mTimer.reset() / mRC.doubleValue());

        // Based on the value of a (which is determined by dt), the next value
        // could either change a lot, or not by much. (smaller dt = smaller change)
        return mLastValue = mLastValue.add(next.sub(mLastValue).mul(1.0 - a));
    }
}
