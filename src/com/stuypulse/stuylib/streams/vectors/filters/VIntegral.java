/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.streams.numbers.filters.Integral;

/**
 * A filter that integrates a VStream with respect to time.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VIntegral extends XYFilter {

    public VIntegral() {
        super(new Integral(), new Integral());
    }
}
