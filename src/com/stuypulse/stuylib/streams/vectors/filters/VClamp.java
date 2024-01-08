/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.math.Vector2D;

/**
 * A filter that clamps the magnitude of a vector to a certain size
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VClamp implements VFilter {

    private Number mMaxMagnitude;

    public VClamp(Number maxMagnitude) {
        mMaxMagnitude = maxMagnitude;
    }

    public Vector2D get(Vector2D target) {
        return target.clamp(mMaxMagnitude.doubleValue());
    }
}
