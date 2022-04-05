/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

import com.stuypulse.stuylib.util.StopWatch;

/**
 * A Debounce Class. This class requires the boolean stream to have changed for (debounceTime)
 * seconds before returning the new value. However if it returns to its original value once, the
 * timer is reset. It is similar to combining a Rising / Falling filter together.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class BDebounceBoth implements BFilter {

    private final StopWatch mTimer;
    private final double mDebounceTime;
    private boolean mLastValue;

    public BDebounceBoth(double debounceTime) {
        mTimer = new StopWatch();
        mDebounceTime = debounceTime;
        mLastValue = false;
    }

    public boolean get(boolean next) {
        if (next == mLastValue) {
            mTimer.reset();
            return mLastValue;
        }

        return mLastValue ^= mDebounceTime < mTimer.getTime();
    }
}
