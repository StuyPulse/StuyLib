/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * A filter that applies the same math found in JerkLimit to a VStream, limiting the jerk of a
 * series of vectors in 2 dimentions.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VJerkLimit implements VFilter {

    // Stopwatch to Track dt
    private StopWatch mTimer;

    // Limits for each of the derivatives
    private Number mAccelLimit;
    private Number mJerkLimit;

    // The last output / acceleration
    private Vector2D mOutput;
    private Vector2D mAccel;

    /**
     * @param accelLimit maximum change in velocity per second (u/s)
     * @param jerkLimit maximum change in acceleration per second (u/s/s)
     */
    public VJerkLimit(Number accelLimit, Number jerkLimit) {
        mTimer = new StopWatch();

        mAccelLimit = accelLimit;
        mJerkLimit = jerkLimit;

        mOutput = Vector2D.kOrigin;
        mAccel = Vector2D.kOrigin;
    }

    public Vector2D get(Vector2D target) {
        double dt = mTimer.reset();

        // if there is a jerk limit, limit the amount the acceleration can change
        if (0 < mJerkLimit.doubleValue()) {
            // amount of windup in system (how long it would take to slow down)
            double windup = mAccel.magnitude() / mJerkLimit.doubleValue();

            // If the windup is too small, just use normal algorithm to limit acceleration
            if (windup < dt) {
                // Calculate acceleration needed to reach target
                Vector2D accel = target.sub(mOutput).div(dt).sub(mAccel);

                // Try to reach it while abiding by jerklimit
                mAccel = mAccel.add(accel.clamp(dt * mJerkLimit.doubleValue()));
            } else {
                // the position it would end up if it attempted to come to a full stop
                Vector2D windA = mAccel.mul(0.5 * (dt + windup)); // windup caused by acceleration
                Vector2D future = mOutput.add(windA); // where the robot will end up

                // Calculate acceleration needed to come to stop at target throughout windup
                Vector2D accel = (target.sub(future)).div(windup);

                // Try to reach it while abiding by jerklimit
                mAccel = mAccel.add(accel.clamp(dt * mJerkLimit.doubleValue()));
            }

        } else {
            // make the acceleration the difference between target and current
            mAccel = target.sub(mOutput).div(dt);
        }

        // if there is an acceleration limit, limit the acceleration
        if (0 < mAccelLimit.doubleValue()) {
            mAccel = mAccel.clamp(mAccelLimit.doubleValue());
        }

        // adjust output by calculated acceleration
        return mOutput = mOutput.add(mAccel.mul(dt));
    }
}
