/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.streams.numbers.filters.TimedMovingAverage;

/**
 * A filter that takes a timed moving average of a VStream
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VTimedMovingAverage extends XYFilter {

    public VTimedMovingAverage(Number time) {
        super(new TimedMovingAverage(time), new TimedMovingAverage(time));
    }
}
