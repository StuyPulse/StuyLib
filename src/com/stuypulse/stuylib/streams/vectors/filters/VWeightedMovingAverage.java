/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.streams.filters.WeightedMovingAverage;

/**
 * A filter that takes a weighted moving average of a VStream
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VWeightedMovingAverage extends XYFilter {

    public VWeightedMovingAverage(int size) {
        super(new WeightedMovingAverage(size), new WeightedMovingAverage(size));
    }
}
