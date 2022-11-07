/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles.filters;

import com.stuypulse.stuylib.math.Angle;

/**
 * Implementation for of a real time IIR HighPassFilter
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class AHighPassFilter implements AFilter {

    private ALowPassFilter mInverse;

    /**
     * @param rc time constant for high pass filter
     */
    public AHighPassFilter(Number rc) {
        mInverse = new ALowPassFilter(rc);
    }

    public Angle get(Angle next) {
        return next.sub(mInverse.get(next));
    }
}
