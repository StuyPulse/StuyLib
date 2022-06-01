/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * A filter, that when applied to the input of a motor, will profile it. Similar to the way in which
 * motion profiling can limit the amount of acceleration and jerk in an S-Curve, this can do that to
 * real time input. Because this will add a delay, it is recommended that the jerklimit is as high
 * as possible. Aside from the jerklimit, this is identicle to a SlewRateLimiter or TimedRateLimit.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class MotorProfile implements IFilter {

    // Stopwatch to Track dt
    private StopWatch mTimer;

    // Limits for each of the derivatives
    private Number mAccelLimit;
    private Number mJerkLimit;

    // The last output / acceleration
    private double mOutput;
    private double mAccel;

    /**
     * @param accelLimit maximum change in velocity per second (u/s)
     * @param jerkLimit maximum change in acceleration per second (u/s/s)
     */
    public MotorProfile(Number accelLimit, Number jerkLimit) {
        mTimer = new StopWatch();

        mAccelLimit = accelLimit;
        mJerkLimit = jerkLimit;

        mOutput = 0;
        mAccel = 0;
    }

    public double get(double target) {
        double dt = mTimer.reset();

        // if there is a jerk limit, limit the amount the acceleration can change
        if (0 < mJerkLimit.doubleValue()) {
            // amount of windup in system (how long it would take to slow down)
            double windup = Math.abs(0.5 * mAccel / mJerkLimit.doubleValue());

            // the position it would end up if it attempted to come to a full stop
            double future = target - (mOutput + mAccel * windup);

            // attempt to make acceleration the future divided by dt
            mAccel += SLMath.clamp(future / dt - mAccel, dt * mJerkLimit.doubleValue());
        } else {
            // make the acceleration the difference between target and current
            mAccel = target - mOutput;
        }

        // if there is an acceleration limit, limit the acceleration
        if (0 < mAccelLimit.doubleValue()) {
            mAccel = SLMath.clamp(mAccel, mAccelLimit.doubleValue());
        }

        // adjust output by calculated acceleration
        return mOutput += dt * mAccel;
    }
}
