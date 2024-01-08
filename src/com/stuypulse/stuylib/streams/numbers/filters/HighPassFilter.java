/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.numbers.filters;

/**
 * Implementation for of a real time IIR HighPassFilter
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class HighPassFilter implements IFilter {

    private LowPassFilter mInverse;

    /** @param rc time constant for high pass filter */
    public HighPassFilter(Number rc) {
        mInverse = new LowPassFilter(rc);
    }

    public double get(double next) {
        return next - mInverse.get(next);
    }
}
