package com.stuypulse.stuylib.math;

/**
 * A class that is used to store polar coordinates. It's heavily dependant on the vector and angle
 * class.
 *
 * @author Sam Belliveau (sam.belliveau@gmail.com)
 */
public class Polar2D {

    // Internal Variables
    private final double mMag;
    private final Angle mAng;

    /**
     * Create a Polar2D with a magnitude and an angle
     *
     * @param mag magnitude
     * @param ang angle
     */
    public Polar2D(double mag, Angle ang) {
        // Make sure the magnitude is always positive
        if(mag >= 0) {
            mMag = mag;
            mAng = ang;
        } else {
            mMag = -mag;
            mAng = ang.add(Angle.k180deg);
        }
    }

    /**
     * Convert a Vector2D into Polar Coordinates
     *
     * @param vec vector
     */
    public Polar2D(Vector2D vec) {
        this(vec.magnitude(), vec.getAngle());
    }

    /**
     * Get the magnitude
     *
     * @return magnitude
     */
    public double magnitude() {
        return mMag;
    }

    /**
     * Get the angle
     *
     * @return angle
     */
    public Angle getAngle() {
        return mAng;
    }

    /**
     * Convert polar coordinates into a Vector2D
     *
     * @return Polar Coordinates as a Vector2D
     */
    public Vector2D getVector() {
        return mAng.getVector().mul(mMag);
    }

    /**
     * Multiply the polar coordinates by scalar
     *
     * @param m scalar to multiply by
     * @return result of multiplication
     */
    public Polar2D mul(double m) {
        return new Polar2D(mMag * m, mAng);
    }

    /**
     * Divide the polar coordinates by scalar
     *
     * @param d scalar to divide by
     * @return result of division
     */
    public Polar2D div(double d) {
        return new Polar2D(mMag / d, mAng);
    }

    /**
     * Rotate the polar coordinates by angle
     *
     * @param a angle to rotate by
     * @return result of rotation
     */
    public Polar2D rotate(Angle a) {
        return new Polar2D(mMag, mAng.add(a));
    }

    /**
     * Normalize the polar coordinates so the magnitude is 1
     *
     * @return normalized polar coordinates
     */
    public Polar2D normalize() {
        return new Polar2D(1, mAng);
    }
}
