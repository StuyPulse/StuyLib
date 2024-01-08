/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

import com.stuypulse.stuylib.streams.numbers.filters.LowPassFilter;

/**
 * An RC Debounce class takes the average of the past few boolean values to remove noise.
 * Recommended to be called very often using a PollingBStream.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface BDebounceRC extends BFilter {

    public static final double kTrue = 1.0;
    public static final double kFalse = 0.0;

    public static final double kLowerLimit = 1.0 / Math.E;
    public static final double kUpperLimit = 1.0 - kLowerLimit;

    /** An RC Debouncer that has a true bias */
    public static class Rising implements BDebounceRC {
        private final LowPassFilter mFilter;

        /** @param debounceTime amount of time on average to go from false to true */
        public Rising(Number debounceTime) {
            mFilter = new LowPassFilter(debounceTime);
        }

        public boolean get(boolean next) {
            return mFilter.get(next ? kTrue : kFalse) > kUpperLimit;
        }
    }

    /** An RC Debouncer that has a false bias */
    public static class Falling implements BDebounceRC {
        private final LowPassFilter mFilter;

        /** @param debounceTime amount of time on average to go from true to false */
        public Falling(Number debounceTime) {
            mFilter = new LowPassFilter(debounceTime);
        }

        public boolean get(boolean next) {
            return mFilter.get(next ? kTrue : kFalse) > kLowerLimit;
        }
    }

    /** An RC Debouncer that has a bias towards the previous value */
    public static class Both implements BDebounceRC {

        private final LowPassFilter mFilter;
        private boolean mPrev;

        /** @param debounceTime amount of time on average to change value */
        public Both(Number debounceTime) {
            mFilter = new LowPassFilter(debounceTime);
            mPrev = false;
        }

        public boolean get(boolean next) {
            return mPrev = mFilter.get(next ? kTrue : kFalse) > (mPrev ? kLowerLimit : kUpperLimit);
        }
    }
}
