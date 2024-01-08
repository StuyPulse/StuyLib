/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * A filter, that when applied to the input of a motor, will profile it. Similar to the way in which
 * motion profiling can limit the amount of acceleration and jerk in an S-Curve, this can do that to
 * real time input. Because this will add a delay, it is recommended that the accelLimit is as high
 * as possible. Aside from the accelLimit, this is identical to a SlewRateLimiter or TimedRateLimit.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VMotionProfile implements VFilter {

    // Default number of times to apply filter (helps accuracy)
    private static final int kDefaultSteps = 64;

    // Stopwatch to Track dt
    private StopWatch mTimer;

    // Limits for each of the derivatives
    private Number mVelLimit;
    private Number mAccelLimit;

    // The last output / acceleration
    private Vector2D mOutput;
    private Vector2D mAccel;

    // Number of times to apply filter (helps accuracy)
    private final int mSteps;

    /**
     * @param velLimit maximum change in displacement per second (u/s)
     * @param accelLimit maximum change in velocity per second (u/s/s)
     * @param steps number of times to apply filter (improves accuracy)
     */
    public VMotionProfile(Number velLimit, Number accelLimit, int steps) {
        mTimer = new StopWatch();

        mVelLimit = velLimit;
        mAccelLimit = accelLimit;

        mOutput = Vector2D.kOrigin;
        mAccel = Vector2D.kOrigin;

        mSteps = steps;
    }

    /**
     * @param velLimit maximum change in velocity per second (u/s)
     * @param accelLimit maximum change in acceleration per second (u/s/s)
     */
    public VMotionProfile(Number velLimit, Number accelLimit) {
        this(velLimit, accelLimit, kDefaultSteps);
    }

    public Vector2D get(Vector2D target) {
        double dt = mTimer.reset() / mSteps;

        for (int i = 0; i < mSteps; ++i) {
            // if there is a jerk limit, limit the amount the acceleration can change
            if (0 < mAccelLimit.doubleValue()) {
                // amount of windup in system (how long it would take to slow down)
                double windup = mAccel.magnitude() / mAccelLimit.doubleValue();

                // If the windup is too small, just use normal algorithm to limit acceleration
                if (windup < dt) {
                    // Calculate acceleration needed to reach target
                    Vector2D accel = target.sub(mOutput).div(dt).sub(mAccel);

                    // Try to reach it while abiding by jerklimit
                    mAccel = mAccel.add(accel.clamp(dt * mAccelLimit.doubleValue()));
                } else {
                    // the position it would end up if it attempted to come to a full stop
                    Vector2D windA =
                            mAccel.mul(0.5 * (dt + windup)); // windup caused by acceleration
                    Vector2D future = mOutput.add(windA); // where the robot will end up

                    // Calculate acceleration needed to come to stop at target throughout windup
                    Vector2D accel = target.sub(future).div(windup);

                    // Try to reach it while abiding by jerklimit
                    mAccel = mAccel.add(accel.clamp(dt * mAccelLimit.doubleValue()));
                }

            } else {
                // make the acceleration the difference between target and current
                mAccel = target.sub(mOutput).div(dt);
            }

            // if there is an acceleration limit, limit the acceleration
            if (0 < mVelLimit.doubleValue()) {
                mAccel = mAccel.clamp(mVelLimit.doubleValue());
            }

            // adjust output by calculated acceleration
            mOutput = mOutput.add(mAccel.mul(dt));
        }

        return mOutput;
    }
}
