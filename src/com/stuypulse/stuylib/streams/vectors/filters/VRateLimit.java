/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * A filter that limits the amount that a Vector2D can change by per second.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VRateLimit implements VFilter {

    private final StopWatch mTimer;
    private final Number mRateLimit;

    private Vector2D mLastValue;

    public VRateLimit(Number rateLimit) {
        mTimer = new StopWatch();
        mRateLimit = rateLimit;

        mLastValue = Vector2D.kOrigin;
    }

    public Vector2D get(Vector2D next) {
        final double dt = mTimer.reset();
        return mLastValue =
                mLastValue.add(next.sub(mLastValue).clamp(dt * mRateLimit.doubleValue()));
    }
}
