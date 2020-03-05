package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.math.SLMath;

/**
 * A filter that takes in real time inputs and profiles it into an S Curve. This is done with time
 * dependant filters to make sure that no matter how many times you call it a second, the resulting
 * profile is the same.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class SpeedProfile implements IStreamFilter {

    private double mAccelLimit;
    private double mJerkLimit;

    private double mSpeed;
    private double mAccel;

    public SpeedProfile(double accelLimit, double jerkLimit) {
        mAccelLimit = accelLimit;
        mJerkLimit = jerkLimit;

        mSpeed = 0;
        mAccel = 0;
    }

    public double get(double next) {
        double targetAccel = next - mSpeed + (mAccelLimit / mJerkLimit);
        mAccel += SLMath.limit(targetAccel - mAccel, mJerkLimit);
        mAccel = SLMath.limit(mAccel, mAccelLimit);
        return mSpeed += mAccel;
    }

}
