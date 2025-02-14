/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.streams.numbers.filters.HighPassFilter;

/**
 * A filter that applies a HighPassFilter to a VStream
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VHighPassFilter extends XYFilter {

    public VHighPassFilter(Number rc) {
        super(new HighPassFilter(rc), new HighPassFilter(rc));
    }
}
