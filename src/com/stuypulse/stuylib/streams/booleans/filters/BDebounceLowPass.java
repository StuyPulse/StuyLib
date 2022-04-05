/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

import com.stuypulse.stuylib.streams.filters.LowPassFilter;

/**
 * A LowPass Debounce class takes the average of the past few boolean values to remove noise.
 * Recommended to be called very often using a PollingBStream.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class BDebounceLowPass {

    private static final double kTrue = 1.0;
    private static final double kFalse = 0.0;

    private static final double kLowerLimit = 1.0 / Math.E;
    private static final double kUpperLimit = 1.0 - kLowerLimit;

    private final LowPassFilter mFilter;
    private boolean mLastValue;

    public BDebounceLowPass(double debounceTime) {
        mFilter = new LowPassFilter(debounceTime);
        mLastValue = false;
    }

    public boolean get(boolean next) {
        final double value = mFilter.get(next ? kTrue : kFalse);

        if (mLastValue) {
            return mLastValue = value > kLowerLimit;
        } else {
            return mLastValue = value < kUpperLimit;
        }
    }
}
