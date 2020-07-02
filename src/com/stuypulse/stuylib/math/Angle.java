package com.stuypulse.stuylib.math;

/**
 * This angle class is made to remove the ambiguity of units when passing or returning angles. It
 * stores it in radians, but returns it in any unit depending on what the user requests. All of the
 * functions and math normalize the angle to help issues.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public final class Angle {

    private static final double kPi = Math.PI;
    private static final double kTwoPi = 2.0 * kPi;

    /**
     * Normalize an angle in radians around a specified center
     *
     * @param radians the angle to be normalized
     * @param center  the center of the normalized range +/- pi
     * @return the normalized angle
     */
    private static double normalizeRadians(double radians, double center) {
        return radians - kTwoPi * Math.floor((radians + kPi - center) / kTwoPi);
    }

    /**
     * Normalize an angle in degrees around a specified center
     *
     * @param degrees the angle to be normalized
     * @param center  the center of the normalized range +/- 180
     * @return the normalized angle
     */
    private static double normalizeDegrees(double degrees, double center) {
        return degrees - 360.0 * Math.floor((degrees + 180.0 - center) / 360.0);
    }

    /**
     * Construct a new Angle class with a double in radians
     *
     * @param radians the angle for the new angle class in radians
     * @return an angle class with the specified angle
     */
    public static Angle fromRadians(double radians) {
        return new Angle(radians);
    }

    /**
     * Construct a new Angle class with a double in degrees
     *
     * @param degrees the angle for the new angle class in degrees
     * @return an angle class with the specified angle
     */
    public static Angle fromDegrees(double degrees) {
        return new Angle(Math.toRadians(degrees));
    }

    /**
     * The value of the angle stored in radians
     */
    private final double mRadians;

    /**
     * Create a new angle with radians as the unit
     *
     * @param radians the value of the new angle
     */
    private Angle(double radians) {
        mRadians = normalizeRadians(radians, 0.0);
    }

    /**
     * Get the value of the angle in radians centered around 0.0
     *
     * @return the value of the angle in radians centered around 0.0
     */
    public double toRadians() {
        return mRadians;
    }

    /**
     * Get the angle normalized around a custom angle in radians
     *
     * @param center the center for the angle to be normalized around
     * @return the angle normalized around the center in radians
     */
    public double toRadians(double center) {
        return normalizeRadians(this.toRadians(), center);
    }

    /**
     * Get the value of the angle in degrees centered around 0.0
     *
     * @return the value of the angle in degrees centered around 0.0
     */
    public double toDegrees() {
        return Math.toDegrees(this.toRadians());
    }

    /**
     * Get the angle normalized around a custom angle in degrees
     *
     * @param center the center for the angle to be normalized around
     * @return the angle normalized around the center in degrees
     */
    public double toDegrees(double center) {
        return normalizeDegrees(this.toDegrees(), center);
    }

    /**
     * Add two angles together
     *
     * @param other the other angle in the sum
     * @return the sum of the two angles
     */
    public Angle add(Angle other) {
        return fromRadians(this.toRadians() + other.toRadians());
    }

    /**
     * Subtract two angles from each other
     *
     * @param other the other angle to subtract
     * @return the first angle subtracted from the other
     */
    public Angle sub(Angle other) {
        return fromRadians(this.toRadians() - other.toRadians());
    }

    /**
     * Get the sin of the angle
     *
     * @return the sin of this angle
     */
    public double sin() {
        return Math.sin(this.toRadians());
    }

    /**
     * Get the cos of the angle
     *
     * @return the cos of this angle
     */
    public double cos() {
        return Math.cos(this.toRadians());
    }

    /**
     * Get the tan of the angle
     *
     * @return the tan of this angle
     */
    public double tan() {
        return Math.tan(this.toRadians());
    }

    /**
     * Get the point of the angle on the unit circle
     *
     * @return the point of the angle on the unit circle
     */
    public Vector2D getVector() {
        return new Vector2D(this.cos(), this.sin());
    }

    /**
     * Represents the Angle as a String
     *
     * @return the string representation of the angle
     */
    public String toString() {
        return "(" + this.toRadians() + "pi, " + this.toDegrees() + "deg)";
    }
}
