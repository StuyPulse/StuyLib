/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

/**
 * A simple boolean filter that returns true when a boolean stream changes depending on the type.
 *
 * <p>1. Both - will return true if the BStream changes at all
 *
 * <p>2. Pressed - will return true if the BStream changes from false to true
 *
 * <p>3. Released - will return true if the BStream changes from true to false
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface BButton extends BFilter {

    public static class Both implements BButton {
        private boolean mLastValue;

        public Both() {
            mLastValue = false;
        }

        public boolean get(boolean next) {
            if (next == mLastValue) {
                return false;
            } else {
                mLastValue = next;
                return true;
            }
        }
    }

    public static class Pressed implements BButton {
        private boolean mLastValue;

        public Pressed() {
            mLastValue = false;
        }

        public boolean get(boolean next) {
            if (next == mLastValue) {
                return false;
            } else {
                return (mLastValue = next) == true;
            }
        }
    }

    public static class Released implements BButton {
        private boolean mLastValue;

        public Released() {
            mLastValue = false;
        }

        public boolean get(boolean next) {
            if (next == mLastValue) {
                return false;
            } else {
                return (mLastValue = next) == false;
            }
        }
    }
}
