/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

import java.nio.file.attribute.AclEntry;

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
public class JerkLimit implements IFilter {

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
    public JerkLimit(Number accelLimit, Number jerkLimit) {
        mTimer = new StopWatch();

        mAccelLimit = accelLimit;
        mJerkLimit = jerkLimit;

        mOutput = 0;
        mAccel = 0;
    }

    public double get(double target) {
        final double dt = mTimer.reset();
        final boolean reverse = target < mOutput;

        final double accelLimit = mAccelLimit.doubleValue();
        final double jerkLimit = mJerkLimit.doubleValue();

        if(reverse) {
            target *= -1;
            mOutput *= -1;
            mAccel *= -1;
        }

        mAccel = SLMath.clamp(mAccel, accelLimit);

        double cutoffBegin = mAccel / jerkLimit;
        double cutoffDistBegin = cutoffBegin * cutoffBegin * jerkLimit * 0.5;

        double fullTrapezoidDist = cutoffDistBegin + (target - mOutput);
        double accelerationTime = accelLimit / jerkLimit;
        double fullSpeedDist = fullTrapezoidDist - accelerationTime * accelerationTime * jerkLimit;

        if (fullSpeedDist < 0) {
            accelerationTime = Math.sqrt(fullTrapezoidDist / jerkLimit);
            fullSpeedDist = 0;
        }

        double endFullSpeed = accelerationTime - cutoffBegin + fullSpeedDist / accelLimit;
        double endDeccel = accelerationTime + endFullSpeed;

        if (dt < endFullSpeed) {
            endDeccel = 0.0;
        }

        double timeLeft = Math.max(0.0, endDeccel - dt);
        double future = mOutput + 0.5 * jerkLimit * timeLeft * timeLeft;
        mAccel += SLMath.clamp((target - future) / dt - mAccel, jerkLimit * dt);
        
        mOutput += mAccel * dt;

        if (reverse) {
            mOutput *= -1;
            mAccel *= -1;
        }

        return mOutput;
    }
}
