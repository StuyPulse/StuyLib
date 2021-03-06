/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.math.SLMath;

/**
 * A filter that takes inputs and limits the amount of jerk and acceleration that can happen in one
 * step. Because some of the calculations need to make estimates about the future, this is not based
 * on time. The rate at which you call this filter will affect the changes you observe.
 *
 * <p>TODO: make this time independent. (it's not an important feature so its not really a
 * priority.)
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class SpeedProfile implements IFilter {

    // Limits for each of the derivatives
    private Number mAccelLimit;
    private Number mJerkLimit;

    // The last speed and acceleration
    private double mSpeed;
    private double mAccel;

    /**
     * Create speed profile with custom accel and jerk limits
     *
     * @param accelLimit maximum amount of acceleration in one second
     * @param jerkLimit maximum amount of jerk in one second
     */
    public SpeedProfile(Number accelLimit, Number jerkLimit) {
        // Jerk cannot be <= 0
        if (jerkLimit.doubleValue() <= 0) {
            throw new IllegalArgumentException("jerkLimit must be above 0");
        }

        // Negative accelLimit means no accel limit
        if (accelLimit.doubleValue() <= 0) {
            accelLimit = Double.MAX_VALUE;
        }

        // Initialize variables
        mAccelLimit = accelLimit;
        mJerkLimit = jerkLimit;

        mSpeed = 0;
        mAccel = 0;
    }

    /**
     * Create SpeedProfile with a custom jerk limit
     *
     * @param jerkLimit most amount of jerk allowed in one step
     */
    public SpeedProfile(double jerkLimit) {
        this(-1, jerkLimit);
    }

    public double get(double next) {
        // Current position accounting for reduced jerk
        double projected = mSpeed + mAccel * Math.abs(mAccel / mJerkLimit.doubleValue()) / 2.0;

        // How much the acceleration needs to change
        double targetAccel = next - projected;

        // Limit the acceleration change by jerk limit
        mAccel += SLMath.clamp(targetAccel - mAccel, mJerkLimit.doubleValue());

        // Limit acceleration
        mAccel = SLMath.clamp(mAccel, mAccelLimit.doubleValue());

        // Add acceleration to speed value
        return mSpeed += mAccel;
    }
}
