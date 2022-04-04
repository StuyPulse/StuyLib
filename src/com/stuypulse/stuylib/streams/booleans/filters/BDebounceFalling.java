/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

import com.stuypulse.stuylib.util.StopWatch;

/** @author Sam (sam.belliveau@gmail.com) */
public class BDebounceFalling implements BFilter {

    private final StopWatch mTimer;
    private final double mDebounceTime;

    public BDebounceFalling(double debounceTime) {
        mTimer = new StopWatch();
        mDebounceTime = debounceTime;
    }

    public boolean get(boolean next) {
        if (next) {
            mTimer.reset();
            return true;
        }

        return !(mDebounceTime < mTimer.getTime());
    }
}
