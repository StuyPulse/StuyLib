/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

import com.stuypulse.stuylib.util.StopWatch;

/**
 * A Falling Debounce Class. This class requires the boolean stream to be false for (debounceTime)
 * seconds before returning false. However if it returns true once, the timer is reset.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class BDebounceFalling implements BFilter {

    private final StopWatch mTimer;
    private final double mDebounceTime;

    /** @param debounceTime amount of time requred before the filter returns false */
    public BDebounceFalling(double debounceTime) {
        mTimer = new StopWatch();
        mDebounceTime = debounceTime;
    }

    public boolean get(boolean next) {
        if (next == true) {
            mTimer.reset();
            return true;
        }

        return true ^ (mDebounceTime < mTimer.getTime());
    }
}
