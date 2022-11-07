/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math;

import com.stuypulse.stuylib.util.HashBuilder;

/**
 * A Vector3D class that stores x, y, and z position data. It is made to work with the StuyLib Angle
 * class and be easy to use. It is a standard Vector3D class with all of the functions that you
 * would expect.
 *
 * @author Vincent (vinowang921@gmail.com)
 */
public final class Vector3D {

    // Configuration for toString() function
    private static final int STRING_SIGFIGS = 5;

    // Vector Constnants
    public static final Vector3D kOrigin = new Vector3D(0, 0, 0);
    public static final Vector3D kI = new Vector3D(1, 0, 0);
    public static final Vector3D kJ = new Vector3D(0, 1, 0);
    public static final Vector3D kK = new Vector3D(0, 0, 1);

    /****************************/
    /*** Class Implementation ***/
    /****************************/

    /** The x position of the Vector3D */
    public final double x;

    /** The y position of the Vector3D */
    public final double y;

    /** The z position of the Vector3D */
    public final double z;

    /**
     * @param x the x axis of the vector
     * @param y the y axis of the vector
     * @param z the z axis of the vector
     */
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @param axis Array of size 3 where the first element will be defined as x, the second will be
     *     defined as y, and the third will be defined as z.
     */
    public Vector3D(double[] axis) {
        if (axis.length != 3) {
            throw new IllegalArgumentException("axis must be of size 3");
        }

        this.x = axis[0];
        this.y = axis[1];
        this.z = axis[2];
    }

    /**
     * @return double array of size 3 defined as {x, y, z}
     */
    public double[] getArray() {
        return new double[] {x, y, z};
    }

    /**
     * @param other other Vector3D
     * @return the distance between the two Vector3Ds
     */
    public double distance(Vector3D other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * @return distance from 0, 0, 0
     */
    public double distance() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * @return magnitude of the vector (same as distance from 0, 0, 0)
     */
    public double magnitude() {
        return this.distance();
    }

    /**
     * @param angle angle to rotate by
     * @return result of rotation
     */
    public Vector3D rotateX(Angle angle) {
        return new Vector3D(
                +this.x,
                +this.y * angle.cos() + +this.x * angle.sin(),
                -this.x * angle.sin() + +this.z * angle.cos());
    }

    /**
     * @param angle angle to rotate by
     * @return result of rotation
     */
    public Vector3D rotateY(Angle angle) {
        return new Vector3D(
                +this.x * angle.cos() + +this.z * angle.sin(),
                +this.y,
                -this.x * angle.sin() + +this.z * angle.cos());
    }

    /**
     * @param angle angle to rotate by
     * @return result of rotation
     */
    public Vector3D rotateZ(Angle angle) {
        return new Vector3D(
                +this.x * angle.cos() + -this.y * angle.sin(),
                +this.x * angle.sin() + +this.y * angle.cos(),
                +this.z);
    }

    /**
     * @param other Vector3D to be added by
     * @return sum of the two Vector3Ds
     */
    public Vector3D add(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    /**
     * @param other Vector3D to be subtracted from
     * @return difference between the two Vector3Ds
     */
    public Vector3D sub(Vector3D other) {
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    /**
     * @param other Vector3D to be multiplied by
     * @return product of the two Vector3Ds
     */
    public Vector3D mul(Vector3D other) {
        return new Vector3D(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    /**
     * @param other Vector3D to be divided by
     * @return division of the two Vector3Ds
     */
    public Vector3D div(Vector3D other) {
        return new Vector3D(this.x / other.x, this.y / other.y, this.z / other.z);
    }

    /**
     * @param multiplier amount to multiply the x and y components by
     * @return result of multiplying the x and y components by the multiplier
     */
    public Vector3D mul(double multiplier) {
        return new Vector3D(this.x * multiplier, this.y * multiplier, this.z * multiplier);
    }

    /**
     * @param divisor amount to divide the x and y components by
     * @return result of dividing the x and y components by the divisor
     */
    public Vector3D div(double divisor) {
        return new Vector3D(this.x / divisor, this.y / divisor, this.z / divisor);
    }

    /**
     * @param other Vector3D to perform dot product with
     * @return result of performing the dot product with the other Vector3D
     */
    public double dot(Vector3D other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * @param other Vector3D to perform cross product with
     * @return result of performing the cross product with the other Vector3D
     */
    public Vector3D cross(Vector3D other) {
        return new Vector3D(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x);
    }

    /**
     * @return result of normalizing the Vector3D so that the magnitude is 1.0
     */
    public Vector3D normalize() {
        double magnitude = this.distance();
        if (SLMath.isZero(magnitude)) {
            return Vector3D.kI;
        } else {
            return this.div(magnitude);
        }
    }

    /**
     * @return result of negating the x, y, and z components
     */
    public Vector3D negative() {
        return new Vector3D(-this.x, -this.y, -this.z);
    }

    /**
     * @param other object to compare to
     * @return both objects are Vector3Ds and they equal eachother
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Vector3D) {
            Vector3D o = (Vector3D) other;
            return this.x == o.x && this.y == o.y && this.z == o.z;
        }

        return false;
    }

    /**
     * @see com.stuypulse.stuylib.util.HashBuilder#combineHash(int, int)
     * @return hashCode generated by combining the hashes of the x, y, and z components
     */
    @Override
    public int hashCode() {
        return new HashBuilder().append(this.x).append(this.y).append(this.z).toHashCode();
    }

    /**
     * @return string representation of Vector3D
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Vector3D(");
        out.append(SLMath.round(x, STRING_SIGFIGS));
        out.append(", ");
        out.append(SLMath.round(y, STRING_SIGFIGS));
        out.append(", ");
        out.append(SLMath.round(z, STRING_SIGFIGS));
        out.append(")");
        return out.toString();
    }
}
