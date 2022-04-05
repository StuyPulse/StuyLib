/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

/**
 * A simple boolean filter that returns true when a boolean stream changes depending on the type.
 *
 * <p>1. Both - will return true if the BStream changes at all
 *
 * <p>2. Pressed - will return true if the BStream changes from false -> true
 *
 * <p>2. Released - will return true if the BStream changes from true -> false
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class BButton {

    private BButton() {
        /* This is an organizational class */
    }

    public static class Both implements BFilter {
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

    public static class Pressed implements BFilter {
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

    public static class Released implements BFilter {
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
