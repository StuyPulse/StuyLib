/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.streams.numbers.filters.LowPassFilter;

/**
 * A filter that applies a LowPassFilter to a VStream
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VLowPassFilter extends XYFilter {

    public VLowPassFilter(Number rc) {
        super(new LowPassFilter(rc), new LowPassFilter(rc));
    }
}
