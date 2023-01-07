/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math;

import edu.wpi.first.math.geometry.Rotation2d;

/**
 * This angle class is made to remove the ambiguity of units when passing or returning angles. It
 * stores it in radians, but returns it in any unit depending on what the user requests. All of the
 * functions and math normalize the angle to help issues.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public final class Angle {

    // Configuration for toString() function
    private static final boolean STRING_RADIANS = false;
    private static final int STRING_SIGFIGS = 5;

    /******************************************************/
    /*** CONSTANT ANGLE VALUES, SET AT BENCHMARK VALUES ***/
    /******************************************************/

    public static final Angle kRadiant = Angle.fromRadians(1.0);

    public static final Angle kDegree = Angle.fromDegrees(1.0);

    public static final Angle kArcMinute = Angle.fromArcMinutes(1.0);
    public static final Angle kArcSecond = Angle.fromArcSeconds(1.0);

    public static final Angle kZero = Angle.fromDegrees(0);
    public static final Angle k30deg = Angle.fromDegrees(30);
    public static final Angle k45deg = Angle.fromDegrees(45);
    public static final Angle k60deg = Angle.fromDegrees(60);
    public static final Angle k90deg = Angle.fromDegrees(90);
    public static final Angle k120deg = Angle.fromDegrees(120);
    public static final Angle k135deg = Angle.fromDegrees(135);
    public static final Angle k150deg = Angle.fromDegrees(150);
    public static final Angle k180deg = Angle.fromDegrees(180);

    /** An angle that will return cos() = 0, sin() = 0, toRadians() = 0 */
    public static final Angle kNull = new Angle();

    /********************************/
    /*** PRIVATE HELPER FUNCTIONS ***/
    /********************************/

    private static final double PI = Math.PI;

    private static final double TAU = PI * 2.0;

    /**
     * @param radians the value to be normalized
     * @param center the center of the normalized range +/- (range/2)
     * @param range range of normalized values
     * @return the normalized value
     */
    private static double normalizeValue(double value, double center, double range) {
        return value - range * Math.round((value - center) / range);
    }

    /**
     * @param radians the angle to be normalized
     * @param center the center of the normalized range +/- pi
     * @return the normalized angle
     */
    private static double normalizeRadians(double radians, double center) {
        return normalizeValue(radians, center, TAU);
    }

    /**
     * @param rotations the angle to be normalized
     * @param center the center of the normalized range +/- 0.5
     * @return the normalized angle
     */
    private static double normalizeRotations(double rotations, double center) {
        return normalizeValue(rotations, center, 1.0);
    }

    /**
     * @param degrees the angle to be normalized
     * @param center the center of the normalized range +/- 180
     * @return the normalized angle
     */
    private static double normalizeDegrees(double degrees, double center) {
        return normalizeValue(degrees, center, 360.0);
    }

    /********************************/
    /*** ANGLE cache OPTIMIZATION ***/
    /********************************/

    // Class responsible for storing cached angles
    private static class AngleCache {
        static final Angle cache[];

        static {
            cache = new Angle[360];
            for (int degrees = 0; degrees < cache.length; ++degrees)
                cache[degrees] = new Angle(Math.toRadians(degrees));
        }

        private AngleCache() {}
    }

    /**
     * Get angle from cached angle lookup table
     *
     * @param degrees degrees of the resulting angle
     * @return the cached angle
     */
    private static final Angle cachedAngle(int degrees) {
        return AngleCache.cache[degrees - 360 * (int) Math.floor(degrees / 360.0)];
    }

    /*************************/
    /*** FACTORY FUNCTIONS ***/
    /*************************/

    /**
     * Construct a new Angle class with a double in radians
     *
     * @param radians the angle for the new angle class measured in radians
     * @return an angle class with the specified angle
     */
    public static Angle fromRadians(double radians) {
        return new Angle(radians);
    }

    /**
     * Construct a new Angle class with a double in rotations
     *
     * @param rotations the angle for the new angle class measured in the number of rotations
     * @return an angle class with the specified angle
     */
    public static Angle fromRotations(double rotations) {
        return fromRadians(TAU * rotations);
    }

    /**
     * Construct a new Angle class with a double in degrees
     *
     * @param degrees the angle for the new angle class measured in degrees
     * @return an angle class with the specified angle
     */
    public static Angle fromDegrees(double degrees) {
        return fromRadians(Math.toRadians(degrees));
    }

    /**
     * Return a new Angle class with an integer in degrees.
     *
     * <p>Because the value that the angle is created from is an integer, every possible angle has
     * been precalculated. You can use this method if you are worried about memory usage or
     * computation time.
     *
     * @param degrees the angle for the new angle class in degrees
     * @return an angle class with the specified angle
     */
    public static Angle fromDegrees(int degrees) {
        return cachedAngle(degrees);
    }

    /**
     * Construct a new Angle class with a double in arcminute
     *
     * @param arcminutes the angle for the new angle class in arcminutes
     * @return an angle class with the specified angle
     */
    public static Angle fromArcMinutes(double arcminutes) {
        return fromDegrees(arcminutes / 60.0);
    }

    /**
     * Construct a new Angle class with a double in arcseconds
     *
     * @param arcseconds the angle for the new angle class in arcseconds
     * @return an angle class with the specified angle
     */
    public static Angle fromArcSeconds(double arcseconds) {
        return fromArcMinutes(arcseconds / 60.0);
    }

    /**
     * Contstruct a new Angle class from x and y positions.
     *
     * <p>You can think of this as getting the angle from rise [y] over run [x].
     *
     * @param x the run of the vector
     * @param y the rise of the vector
     * @return the new angle class
     */
    public static Angle fromVector(double x, double y) {
        return fromRadians(Math.atan2(y, x));
    }

    /**
     * Construct a new Angle class from a vector.
     *
     * <p>This is identicle to calling fromVector(x, y) but by passing in the vectors x and y
     * values.
     *
     * @param vector the vector from which to get the angle from
     * @return the angle of the vector
     */
    public static Angle fromVector(Vector2D vector) {
        return fromVector(vector.x, vector.y);
    }

    /**
     * Construct a new Angle from a slope.
     *
     * <p>This will get you the angle of any slope in the form of rise / run.
     *
     * @param slope the slope to get the angle from
     * @return the angle of the slope
     */
    public static Angle fromSlope(double slope) {
        return fromRadians(Math.atan(slope));
    }

    /**
     * Constructs a new Angle from a WPILib Rotation2d
     *
     * @param rotation rotation2d object
     * @return the same angle
     */
    public static Angle fromRotation2d(Rotation2d rotation) {
        return Angle.fromDegrees(rotation.getDegrees());
    }

    /****************************/
    /*** CLASS IMPLEMENTATION ***/
    /****************************/

    /** The value of the angle stored in radians */
    private final double mRadians;

    // Precalculated Trig Values
    private final double mSin;
    private final double mCos;

    /** Create an Angle with 0 radians, 0 sin, and 0 cos */
    private Angle() {
        mRadians = 0.0;
        mSin = 0.0;
        mCos = 0.0;
    }

    /** @param radians the value of the new angle */
    private Angle(double radians) {
        mRadians = normalizeRadians(radians, 0.0);
        mSin = Math.sin(mRadians);
        mCos = Math.cos(mRadians);
    }

    /** @return the value of the angle in radians centered around 0.0 (+/- pi) */
    public double toRadians() {
        return mRadians;
    }

    /**
     * @param center the angle in radians to be centered around (+/- pi)
     * @return the angle normalized around the center in radians
     */
    public double toRadians(double center) {
        return normalizeRadians(this.toRadians(), center);
    }

    /** @return the value of the angle in rotations centered around 0.0 (+/- 0.5) */
    public double toRotations() {
        return this.toRadians() / TAU;
    }

    /**
     * @param center the angle in rotations to be centered around (+/- 0.5)
     * @return the angle normalized around the center in rotations
     */
    public double toRotations(double center) {
        return normalizeRotations(this.toRotations(), center);
    }

    /** @return the value of the angle in degrees centered around 0.0 (+/- 180) */
    public double toDegrees() {
        return Math.toDegrees(this.toRadians());
    }

    /**
     * @param center the angle in degrees to be centered around (+/- 180)
     * @return the angle normalized around the center in degrees
     */
    public double toDegrees(double center) {
        return normalizeDegrees(this.toDegrees(), center);
    }

    /**
     * Return the StuyLib Angle class in the form of the WPILib Rotation2d.
     *
     * <p>This function is here in order to make interoperability with WPILib easier so that manual
     * conversion isn't needed as much.
     *
     * @return Rotation2d with the same value as this angle
     */
    public Rotation2d getRotation2d() {
        return new Rotation2d(toRadians());
    }

    /**
     * Add two angles together
     *
     * @param other the other angle in the sum
     * @return the sum of the two angles [normalized from (-pi, pi)]
     */
    public Angle add(Angle other) {
        return fromRadians(this.toRadians() + other.toRadians());
    }

    /**
     * Add two angles together
     *
     * @param radians the other angle in the sum
     * @return the sum of the two angles [normalized from (-pi, pi)]
     */
    public Angle addRadians(double radians) {
        return fromRadians(this.toRadians() + radians);
    }

    /**
     * Add two angles together
     *
     * @param rotations the other angle in the sum
     * @return the sum of the two angles [normalized from (-pi, pi)]
     */
    public Angle addRotations(double rotations) {
        return fromRotations(this.toDegrees() + rotations);
    }

    /**
     * Add two angles together
     *
     * @param degrees the other angle in the sum
     * @return the sum of the two angles [normalized from (-pi, pi)]
     */
    public Angle addDegrees(double degrees) {
        return fromDegrees(this.toDegrees() + degrees);
    }

    /**
     * Subtract two angles from each other
     *
     * @param other the other angle to subtract
     * @return the second angle subtracted from the first [normalized from (-pi, pi)]
     */
    public Angle sub(Angle other) {
        return fromRadians(this.toRadians() - other.toRadians());
    }

    /**
     * Subtract two angles from each other
     *
     * @param radians the other angle to subtract
     * @return the second angle subtracted from the first [normalized from (-pi, pi)]
     */
    public Angle subRadians(double radians) {
        return fromRadians(this.toRadians() - radians);
    }

    /**
     * Subtract two angles from each other
     *
     * @param rotations the other angle to subtract
     * @return the second angle subtracted from the first [normalized from (-pi, pi)]
     */
    public Angle subRotations(double rotations) {
        return fromRotations(this.toDegrees() - rotations);
    }

    /**
     * Subtract two angles from each other
     *
     * @param degrees the other angle to subtract
     * @return the second angle subtracted from the first [normalized from (-pi, pi)]
     */
    public Angle subDegrees(double degrees) {
        return fromDegrees(this.toDegrees() - degrees);
    }

    /**
     * Get the angular velocity in radians given 2 angles and a dt
     *
     * @param prev the previous angle to measure velocity from
     * @param dt the time between measurements
     * @return the calculated angular velocity in radians/s
     */
    public double velocityRadians(Angle prev, double dt) {
        return normalizeRadians(this.toRadians() - prev.toRadians(), 0.0) / dt;
    }

    /**
     * Get the angular velocity in rotations/s given 2 angles and a dt
     *
     * @param prev the previous angle to measure velocity from
     * @param dt the time between measurements
     * @return the calculated angular velocity in rotations/s
     */
    public double velocityRotations(Angle prev, double dt) {
        return normalizeRotations(this.toRotations() - prev.toRotations(), 0.0) / dt;
    }

    /**
     * Get the angular velocity in degrees/s given 2 angles and a dt
     *
     * @param prev the previous angle to measure velocity from
     * @param dt the time between measurements
     * @return the calculated angular velocity in degrees/s
     */
    public double velocityDegrees(Angle prev, double dt) {
        return normalizeDegrees(this.toDegrees() - prev.toDegrees(), 0.0) / dt;
    }

    /**
     * Multiply the angle by a scalar value
     *
     * @param scale the scaler value to multiply the angle by
     * @return the multiplied angle [normalized from (-pi, pi)]
     */
    public Angle mul(double scale) {
        return fromRadians(this.toRadians() * scale);
    }

    /**
     * Divide the angle by a scalar value
     *
     * @param scale the scaler value to divide the angle by
     * @return the divided angle [normalized from (-pi, pi)]
     */
    public Angle div(double scale) {
        return fromRadians(this.toRadians() / scale);
    }

    /** @return an angle with a negative value */
    public Angle negative() {
        return fromRadians(0.0 - this.toRadians());
    }

    /** @return an angle rotated by 180 degrees or Pi radians */
    public Angle opposite() {
        return fromRadians(PI + this.toRadians());
    }

    /** @return the sine value of this angle */
    public double sin() {
        return mSin;
    }

    /** @return the cosine value of this angle */
    public double cos() {
        return mCos;
    }

    /** @return the tangent value of this angle */
    public double tan() {
        return mSin / mCos;
    }

    /** @return the angle as a point on the unit circle */
    public Vector2D getVector() {
        return new Vector2D(this.cos(), this.sin());
    }

    /**
     * Compare Angle to another object
     *
     * @param other object to compare to
     * @return both objects are Angle and they equal eachother
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Angle) {
            return this.toRadians() == ((Angle) other).toRadians();
        }

        return false;
    }

    /** @return the hashCode of the double for the value in radians */
    @Override
    public int hashCode() {
        return Double.hashCode(this.toRadians());
    }

    /** @return the string representation of the angle */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Angle(");

        if (STRING_RADIANS) {
            out.append(SLMath.round(this.toRadians(), STRING_SIGFIGS)).append("pi, ");
        }

        out.append(SLMath.round(this.toDegrees(), STRING_SIGFIGS)).append("deg)");

        return out.toString();
    }
}
