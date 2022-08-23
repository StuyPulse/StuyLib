/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.math.Vector2D;

/**
 * A filter that will zero out a vector if its magnitude is too small
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VDeadZone implements VFilter {

    private Number mDeadZone;

    public VDeadZone(Number deadzone) {
        mDeadZone = deadzone;
    }

    public Vector2D get(Vector2D target) {
        if (target.magnitude() < mDeadZone.doubleValue()) {
            return Vector2D.kOrigin;
        }

        return target;
    }
}
