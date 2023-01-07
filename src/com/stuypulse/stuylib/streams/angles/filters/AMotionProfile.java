/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles.filters;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * A filter, that when applied to the input of a motor, will profile it. Similar to the way in which
 * motion profiling can limit the amount of acceleration and jerk in an S-Curve, this can do that to
 * real time input. Because this will add a delay, it is recommended that the accelLimit is as high
 * as possible. Aside from the accelLimit, this is identical to a SlewRateLimiter or TimedRateLimit.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class AMotionProfile implements AFilter {

    // Default number of times to apply filter (helps accuracy)
    private static final int kDefaultSteps = 64;

    // Stopwatch to Track dt
    private StopWatch mTimer;

    // Limits for each of the derivatives
    private Number mVelLimit;
    private Number mAccelLimit;

    // The last output / acceleration
    private Angle mOutput;
    private double mAccel;

    // Number of times to apply filter (helps accuracy)
    private final int mSteps;

    /**
     * @param velLimit maximum change in velocity per second (u/s)
     * @param accelLimit maximum change in acceleration per second (u/s/s)
     * @param steps number of times to apply filter (improves accuracy)
     */
    public AMotionProfile(Number velLimit, Number accelLimit, int steps) {
        mTimer = new StopWatch();

        mVelLimit = velLimit;
        mAccelLimit = accelLimit;

        mOutput = Angle.kZero;
        mAccel = 0;

        mSteps = steps;
    }

    /**
     * @param velLimit maximum change in velocity per second (u/s)
     * @param accelLimit maximum change in acceleration per second (u/s/s)
     */
    public AMotionProfile(Number velLimit, Number accelLimit) {
        this(velLimit, accelLimit, kDefaultSteps);
    }

    public Angle get(Angle target) {
        double dt = mTimer.reset() / mSteps;

        for (int i = 0; i < mSteps; ++i) {
            // if there is a accel limit, limit the amount the acceleration can change
            if (0 < mAccelLimit.doubleValue()) {
                // amount of windup in system (how long it would take to slow down)
                double windup = Math.abs(mAccel) / mAccelLimit.doubleValue();

                // If the windup is too small, just use normal algorithm to limit acceleration
                if (windup < dt) {
                    // Calculate acceleration needed to reach target
                    double accel = target.velocityRadians(mOutput, dt) - mAccel;

                    // Try to reach it while abiding by accellimit
                    mAccel += SLMath.clamp(accel, dt * mAccelLimit.doubleValue());
                } else {
                    // the position it would end up if it attempted to come to a full stop
                    Angle future =
                            mOutput.addRadians(
                                    0.5 * mAccel * (dt + windup)); // where the robot will end up

                    // Calculate acceleration needed to come to stop at target throughout windup
                    double accel = target.velocityRadians(future, windup);

                    // Try to reach it while abiding by accelLimit
                    mAccel += SLMath.clamp(accel, dt * mAccelLimit.doubleValue());
                }

            } else {
                // make the acceleration the difference between target and current
                mAccel = target.velocityRadians(mOutput, dt);
            }

            // if there is an acceleration limit, limit the acceleration
            if (0 < mVelLimit.doubleValue()) {
                mAccel = SLMath.clamp(mAccel, mVelLimit.doubleValue());
            }

            // adjust output by calculated acceleration
            mOutput = mOutput.addRadians(dt * mAccel);
        }

        return mOutput;
    }
}
