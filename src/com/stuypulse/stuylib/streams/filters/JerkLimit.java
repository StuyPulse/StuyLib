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
        double dt = mTimer.reset();

        boolean reverse = target < mOutput;

        if(reverse) {
            target *= -1;
            mOutput *= -1;
            mAccel *= -1;
        }

        mAccel = SLMath.clamp(mAccel, mAccelLimit.doubleValue());

        double cutoffBegin = mAccel / mJerkLimit.doubleValue();
        double cutoffDistBegin = cutoffBegin * cutoffBegin * mJerkLimit.doubleValue() * 0.5;

        double fullTrapezoidDist = cutoffDistBegin + (target - mOutput);
        double accelerationTime = mAccelLimit.doubleValue() / mJerkLimit.doubleValue();
        double fullSpeedDist = fullTrapezoidDist - accelerationTime * accelerationTime * mJerkLimit.doubleValue();

        if (fullSpeedDist < 0) {
            accelerationTime = Math.sqrt(fullTrapezoidDist / mJerkLimit.doubleValue());
            fullSpeedDist = 0;
        }

        double endAccel = accelerationTime - cutoffBegin;
        double endFullSpeed = endAccel + fullSpeedDist / mAccelLimit.doubleValue();
        double endDeccel = endFullSpeed + accelerationTime;

        if (dt < endAccel) {
            mOutput += (mAccel + dt * mJerkLimit.doubleValue() * 0.5) * dt;
            mAccel += dt * mJerkLimit.doubleValue();
        } else if (dt < endFullSpeed) {
            mOutput += (mAccel + endAccel * mJerkLimit.doubleValue() * 0.5) * endAccel + mAccelLimit.doubleValue() * (dt - endAccel);
            mAccel = mAccelLimit.doubleValue();
        } else if (dt <= endDeccel) {
            double timeLeft = endDeccel - dt;
            mOutput = target - (timeLeft * mJerkLimit.doubleValue() * 0.5) * timeLeft;
            mAccel = timeLeft * mJerkLimit.doubleValue();
        } else {
            mOutput = target;
        }

        if (reverse) {
            mOutput *= -1;
            mAccel *= -1;
        }

        return mOutput;
    }
}
