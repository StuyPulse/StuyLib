/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

import com.stuypulse.stuylib.streams.filters.HighPassFilter;

/**
 * A simple boolean filter that returns true when a boolean stream changes depending on the type.
 * This type of button filter will hold true for a certain decay length before returning to false.
 *
 * <p>1. Both - will return true if the BStream changes at all
 *
 * <p>2. Pressed - will return true if the BStream changes from false to true
 *
 * <p>3. Released - will return true if the BStream changes from true to false
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class BButtonRC {

    private BButtonRC() {
        /* This is an organizational class */
    }

    private static final double kTrue = 1.0;
    private static final double kFalse = 0.0;

    private static final double kPressedLimit = 1.0 / Math.E;
    private static final double kReleasedLimit = -kPressedLimit;

    public static class Both implements BFilter {
        private HighPassFilter mFilter;

        /** @param decay time in seconds to remain true after the BStream is pressed or released */
        public Both(double decay) {
            mFilter = new HighPassFilter(decay);
        }

        public boolean get(boolean next) {
            double value = mFilter.get(next ? kTrue : kFalse);
            return value > kPressedLimit || value < kReleasedLimit;
        }
    }

    public static class Pressed implements BFilter {
        private HighPassFilter mFilter;

        /** @param decay time in seconds to remain true after the BStream has been pressed */
        public Pressed(double decay) {
            mFilter = new HighPassFilter(decay);
        }

        public boolean get(boolean next) {
            return mFilter.get(next ? kTrue : kFalse) > kPressedLimit;
        }
    }

    public static class Released implements BFilter {
        private HighPassFilter mFilter;

        /** @param decay time in seconds to remain true after the BStream has been released */
        public Released(double decay) {
            mFilter = new HighPassFilter(decay);
        }

        public boolean get(boolean next) {
            return mFilter.get(next ? kTrue : kFalse) < kReleasedLimit;
        }
    }
}
