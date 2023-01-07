/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

import com.stuypulse.stuylib.util.StopWatch;

/**
 * A collection of Debounce Classes in 3 flavors.
 *
 * <p>1. Rising - BStream must be true for x seconds
 *
 * <p>2. Falling - BStream must be false for x seconds
 *
 * <p>3. Both - BStream must remain constant for x seconds to change
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface BDebounce extends BFilter {

    /**
     * A Rising Debounce Filter.
     *
     * <p>The input BStream must remain true for debounceTime before this returns true
     */
    public static class Rising implements BDebounce {

        private final StopWatch mTimer;
        private final Number mDebounceTime;

        /** @param debounceTime amount of time the BStream must remain true before returning true */
        public Rising(Number debounceTime) {
            mTimer = new StopWatch();
            mDebounceTime = debounceTime;
        }

        public boolean get(boolean next) {
            if (next == false) {
                mTimer.reset();
                return false;
            }

            return (mDebounceTime.doubleValue() < mTimer.getTime()) == true;
        }
    }

    /**
     * A Falling Debounce Filter.
     *
     * <p>The input BStream must remain false for debounceTime before this returns false
     */
    public static class Falling implements BDebounce {

        private final StopWatch mTimer;
        private final Number mDebounceTime;

        /**
         * @param debounceTime amount of time the BStream must remain false before returning false
         */
        public Falling(Number debounceTime) {
            mTimer = new StopWatch();
            mDebounceTime = debounceTime;
        }

        public boolean get(boolean next) {
            if (next == true) {
                mTimer.reset();
                return true;
            }

            return (mDebounceTime.doubleValue() < mTimer.getTime()) == false;
        }
    }

    /**
     * A General Debounce Filter.
     *
     * <p>The input BStream must remain the constant for debounceTime before it changes the value it
     * returns
     */
    public static class Both implements BDebounce {

        private final StopWatch mTimer;
        private final Number mDebounceTime;
        private boolean mLastValue;

        /** @param debounceTime amount of time the BStream must remain constant before changing */
        public Both(Number debounceTime) {
            mTimer = new StopWatch();
            mDebounceTime = debounceTime;
            mLastValue = false;
        }

        public boolean get(boolean next) {
            if (next == mLastValue) {
                mTimer.reset();
                return mLastValue;
            } else if (mDebounceTime.doubleValue() < mTimer.getTime()) {
                mTimer.reset();
                return mLastValue = next;
            } else {
                return mLastValue;
            }
        }
    }
}
