package com.stuypulse.stuylib.math;

public class ImmutableAngle implements Angle {
    private final double mRadians;
    private final double mSin;
    private final double mCos;

    protected ImmutableAngle(double radians) {
        mRadians = radians;
        mSin = Math.sin(radians);
        mCos = Math.cos(radians);
    }

    protected ImmutableAngle() {
        mRadians = 0.0;
        mSin = 0.0;
        mCos = 0.0;
    }

    public double toRadians() {
        return mRadians;
    }

    public double sin() {
        return mSin;
    }

    public double cos() {
        return mCos;
    }

    public double tan() {
        return sin() / cos();
    }
}
