/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.util.StopWatch;

/**
 * This class takes an IStream and gives you the integral with respect to time.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class Integral implements IFilter {

    private final StopWatch mTimer;
    private double mTotal;

    public Integral() {
        mTimer = new StopWatch();
        mTotal = 0.0;
    }

    public double get(double next) {
        return mTotal += next * mTimer.reset();
    }
}
