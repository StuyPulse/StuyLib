/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.util.StopWatch;

/**
 * This class takes an IStream and gives you the derivative with respect to time.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class Derivative implements IFilter {

    StopWatch mTimer;
    double mLastValue;

    public Derivative() {
        mTimer = new StopWatch();
        mLastValue = 0.0;
    }

    public double get(double next) {
        double diff = next - mLastValue;
        mLastValue = next;
        return diff / mTimer.reset();
    }
}
