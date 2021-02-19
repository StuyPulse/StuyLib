// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

package com.stuypulse.stuylib.math;

import edu.wpi.first.wpilibj.geometry.Translation2d;

/**
 * A Vector2D class that stores x and y position data. It is made to work with the StuyLib Angle
 * class and be easy to use. It is a standard Vector2D class with all of the functions that you
 * would expect.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public final class Vector2D {

    // Configuration for toString() function
    private static final int STRING_SIGFIGS = 5;

    /** The x position of the Vector2D */
    public final double x;

    /** The y position of the Vector2D */
    public final double y;

    /**
     * Make a Vector2D with two numbers
     *
     * @param x the x axis of the vector
     * @param y the y axis of the vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Make a Vector2D with an array of 2 numbers
     *
     * @param axis an array or varargs for x and y
     */
    public Vector2D(double... axis) {
        if (axis.length != 2) {
            throw new IllegalArgumentException("axis must be of size 2");
        }

        this.x = axis[0];
        this.y = axis[1];
    }

    /**
     * Get x, y coordinates as an array
     *
     * @return array of x and y
     */
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
     * Get the distance between two Vector2Ds
     *
     * @param other other Vector2D
     * @return the distance between the two Vector2Ds
     */
    public double distance(Vector2D other) {
        return Math.hypot(other.x - this.x, other.y - this.y);
    }

    /**
     * Get the distance from 0, 0
     *
     * @return distance from 0, 0
     */
    public double distance() {
        return Math.hypot(this.x, this.y);
    }

    /**
     * Get the magnitude of the vector (same as distance from 0, 0)
     *
     * @return magnitude of the vector
     */
    public double magnitude() {
        return this.distance();
    }

    /**
     * Get the angle of the Vector2D around 0
     *
     * @return the angle of the Vector2D around 0
     */
    public Angle getAngle() {
        return Angle.fromVector(this);
    }

    /**
     * Get polar coordinates created from this vector
     *
     * @return polar coordinates created from this vector
     */
    public Polar2D getPolar() {
        return new Polar2D(this);
    }

    /**
     * Rotate Vector2D around point
     *
     * @param angle amount to rotate
     * @param origin point to rotate around
     * @return result of rotation
     */
    public Vector2D rotate(Angle angle, Vector2D origin) {
        return this.sub(origin).rotate(angle).add(origin);
    }

    /**
     * Rotate Vector2D around origin
     *
     * @param angle amount to rotate
     * @return result of rotation
     */
    public Vector2D rotate(Angle angle) {
        return new Vector2D(
                this.x * angle.cos() - this.y * angle.sin(),
                this.y * angle.cos() + this.x * angle.sin());
    }

    /**
     * Add two Vector2Ds
     *
     * @param other the other Vector2D
     * @return sum of the two Vector2Ds
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtract two Vector2Ds
     *
     * @param other the other Vector2D
     * @return difference between the two Vector2Ds
     */
    public Vector2D sub(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Multiply two Vector2Ds
     *
     * @param other the other Vector2D
     * @return product of the two Vector2Ds
     */
    public Vector2D mul(Vector2D other) {
        return new Vector2D(this.x * other.x, this.y * other.y);
    }

    /**
     * Divide two Vector2Ds
     *
     * @param other the other Vector2D
     * @return division of the two Vector2Ds
     */
    public Vector2D div(Vector2D other) {
        return new Vector2D(this.x / other.x, this.y / other.y);
    }

    /**
     * Multiply the x and y of the Vector2D by a certain amount
     *
     * @param multiplier amount to multiply the Vector2D by
     * @return the multiplied Vector2D
     */
    public Vector2D mul(double multiplier) {
        return new Vector2D(this.x * multiplier, this.y * multiplier);
    }

    /**
     * Divide the x and y of the Vector2D by a certain amount
     *
     * @param divisor amount to divide the Vector2D by
     * @return the divided Vector2D
     */
    public Vector2D div(double divisor) {
        return new Vector2D(this.x / divisor, this.y / divisor);
    }

    /**
     * Calculate the vector dot product of the two vectors
     *
     * @param other the other Vector2D
     * @return the result of the dot product
     */
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Normalize the Vector2D so that the point lands on the unit circle
     *
     * @return the normalized Vector2D
     */
    public Vector2D normalize() {
        return this.div(this.distance());
    }

    /**
     * A vector with the negatives of the x and y components.
     *
     * @return the negative vector.
     */
    public Vector2D negative() {
        return new Vector2D(-this.x, -this.y);
    }

    /**
     * Compare Vector2D to another object
     *
     * @param other object to compare to
     * @return both objects are Vector2Ds and they equal eachother
     */
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

    /**
     * Returns Vector2D class in the form of a string
     *
     * @return string value of Vector2D
     */
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Vector2D(");
        out.append(SLMath.round(x, STRING_SIGFIGS));
        out.append(", ");
        out.append(SLMath.round(y, STRING_SIGFIGS));
        out.append(")");
        return out.toString();
    }
}
