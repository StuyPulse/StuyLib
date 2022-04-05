/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

/**
 * A simple boolean filter that returns true once when the stream goes from true -> false
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class BReleased implements BFilter {

    private boolean mLastValue;

    public BReleased() {
        mLastValue = false;
    }

    public boolean get(boolean next) {
        if (mLastValue == false) {
            return mLastValue = false;
        }

        return (mLastValue = next) == false;
    }
}
