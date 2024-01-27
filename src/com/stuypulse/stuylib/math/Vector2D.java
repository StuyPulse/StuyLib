/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math;

import edu.wpi.first.math.geometry.Translation2d;

/**
 * A Vector2D class that stores x and y position data. It is made to work with the StuyLib Angle
 * class and be easy to use. It is a standard Vector2D class with all of the functions that you
 * would expect.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public final class Vector2D {

    // Vector Constnants
    public static final Vector2D kOrigin = new Vector2D(0, 0);
    public static final Vector2D kI = new Vector2D(1, 0);
    public static final Vector2D kJ = new Vector2D(0, 1);

    /****************************/
    /*** Class Implementation ***/
    /****************************/

    /** The x position of the Vector2D */
    public final double x;

    /** The y position of the Vector2D */
    public final double y;

    /**
     * @param x the x axis of the vector
     * @param y the y axis of the vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param axis Array of size 2 where the first element will be defined as x, and the second will
     *     be defined as y.
     */
    public Vector2D(double[] axis) {
        if (axis.length != 2) {
            throw new IllegalArgumentException("axis must be of size 2");
        }

        this.x = axis[0];
        this.y = axis[1];
    }

    /** @param translation Translation2d to copy this vector into */
    public Vector2D(Translation2d translation) {
        this.x = translation.getX();
        this.y = translation.getY();
    }

    /** @return double array of size 2 defined as {x, y} */
    public double[] getArray() {
        return new double[] {x, y};
    }

    /**
     * Return the StuyLib Vector2D class in the form as WPILib's Translation2d.
     *
     * <p>This function is here in order to make interoperability with WPILib easier so that manual
     * conversion isn't needed as much.
     *
     * @return Translation2d class with the same value as the Vector2d
     */
    public Translation2d getTranslation2d() {
        return new Translation2d(x, y);
    }

    /**
     * @param other other Vector2D
     * @return the distance between the two Vector2Ds
     */
    public double distance(Vector2D other) {
        return Math.hypot(other.x - this.x, other.y - this.y);
    }

    /** @return distance from 0, 0 */
    public double distance() {
        return Math.hypot(this.x, this.y);
    }

    /** @return magnitude of the vector (same as distance from 0, 0) */
    public double magnitude() {
        return this.distance();
    }

    /** @return the angle of the Vector2D around 0, 0 */
    public Angle getAngle() {
        return Angle.fromVector(this);
    }

    /**
     * @param angle angle to rotate by
     * @param origin point to rotate around
     * @return result of rotation
     */
    public Vector2D rotate(Angle angle, Vector2D origin) {
        return new Vector2D(
                origin.x + (this.x - origin.x) * angle.cos() - (this.y - origin.y) * angle.sin(),
                origin.y + (this.y - origin.y) * angle.cos() + (this.x - origin.x) * angle.sin());
    }

    /**
     * @param angle angle to rotate by
     * @return result of rotation
     */
    public Vector2D rotate(Angle angle) {
        return new Vector2D(
                this.x * angle.cos() - this.y * angle.sin(),
                this.y * angle.cos() + this.x * angle.sin());
    }

    /**
     * @param other Vector2D to be added by
     * @return sum of the two Vector2Ds
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * @param other Vector2D to be subtracted from
     * @return difference between the two Vector2Ds
     */
    public Vector2D sub(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * @param other Vector2D to be multiplied by
     * @return product of the two Vector2Ds
     */
    public Vector2D mul(Vector2D other) {
        return new Vector2D(this.x * other.x, this.y * other.y);
    }

    /**
     * @param other Vector2D to be divided by
     * @return division of the two Vector2Ds
     */
    public Vector2D div(Vector2D other) {
        return new Vector2D(this.x / other.x, this.y / other.y);
    }

    /**
     * @param multiplier amount to multiply the x and y components by
     * @return result of multiplying the x and y components by the multiplier
     */
    public Vector2D mul(double multiplier) {
        return new Vector2D(this.x * multiplier, this.y * multiplier);
    }

    /**
     * @param divisor amount to divide the x and y components by
     * @return result of dividing the x and y components by the divisor
     */
    public Vector2D div(double divisor) {
        return new Vector2D(this.x / divisor, this.y / divisor);
    }

    /**
     * @param power power to raise magnitude of vector to
     * @return result of raising the magnitude of the vector to the power
     */
    public Vector2D pow(double power) {
        return this.mul(Math.pow(magnitude(), power - 1));
    }

    /**
     * @param other Vector2D to perform dot product with
     * @return result of performing the dot product with the other Vector2D
     */
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * @param other Vector3D to perform cross product with
     * @return result of performing the cross product with the other Vector2D
     */
    public double cross(Vector2D other) {
        return this.x * other.y - this.y * other.x;
    }

    /** @return result of normalizing the Vector2D so that the magnitude is 1.0 */
    public Vector2D normalize() {
        final double magnitude = this.distance();
        if (magnitude <= 1e-9) {
            return Vector2D.kI;
        } else {
            return this.div(magnitude);
        }
    }

    /**
     * limit the magnitude of a vector to a maximum
     *
     * @param maxMagnitude max magitude of resulting vector
     * @return vector with limited magnitude
     */
    public Vector2D clamp(double maxMagnitude) {
        if (maxMagnitude <= 0.0) return Vector2D.kOrigin;

        final double magnitude = this.distance();

        if (maxMagnitude <= magnitude) {
            return mul(maxMagnitude / magnitude);
        }

        return this;
    }

    /** @return result of negating the x and y components */
    public Vector2D negative() {
        return new Vector2D(-this.x, -this.y);
    }

    /**
     * @param other object to compare to
     * @return both objects are Vector2Ds and they equal eachother
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Vector2D) {
            Vector2D o = (Vector2D) other;
            return this.x == o.x && this.y == o.y;
        }

        return false;
    }
}
