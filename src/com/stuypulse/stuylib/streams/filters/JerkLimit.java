/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

import java.nio.file.attribute.AclEntry;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.util.StopWatch;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

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

        TrapezoidProfile profile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(mAccelLimit.doubleValue(), mJerkLimit.doubleValue()), 
            new TrapezoidProfile.State(target, 0.0),
            new TrapezoidProfile.State(mOutput, mAccel));

        TrapezoidProfile.State next = profile.calculate(dt);

        mAccel = next.velocity;
        return mOutput = next.position;
    }
}
