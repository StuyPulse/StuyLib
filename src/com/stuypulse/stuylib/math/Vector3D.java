/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math;

import com.stuypulse.stuylib.util.HashBuilder;

import edu.wpi.first.math.geometry.Translation2d;

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
    public static final Vector3D kk = new Vector3D(0, 0, 1);

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
     * @param axis Array of size 3 where the first element will be defined as x, the second will
     *     be defined as y, and the third will be defined as z.
     */
    public Vector3D(double[] axis) {
        if (axis.length != 3) {
            throw new IllegalArgumentException("axis must be of size 2");
        }

        this.x = axis[0];
        this.y = axis[1];
        this.z = axis[2];
    }

    /** @return double array of size 3 defined as {x, y, z} */
    public double[] getArray() {
        return new double[] {x, y, z};
    }

    /**
     * @param other other Vector3D
     * @return the distance between the two Vector3Ds
     */
    public double distance(Vector3D other) {
        return Math.sqrt(Math.pow((this.x - other.x), 2)  + Math.pow((this.y - other.y), 2) + Math.pow((this.y - other.y), 2));
    }

    /** @return distance from 0, 0, 0 */
    public double distance() {
        return distance(new Vector3D(0, 0, 0));
    }

    /** @return magnitude of the vector (same as distance from 0, 0, 0) */
    public double magnitude() {
        return this.distance();
    }

    /** @return the angle of the Vector3D around 0, 0, 0 */
    public Angle getAngle() {
        return Math.acos();
    }

    /**
     * @param angle angle to rotate by
     * @param origin point to rotate around
     * @return result of rotation
     */
    public Vector3D rotate(Angle angle, Vector3D origin) {
        return new Vector3D(
                origin.x + (this.x - origin.x) * angle.cos() - (this.y - origin.y) * angle.sin(),
                origin.y + (this.y - origin.y) * angle.cos() + (this.x - origin.x) * angle.sin());
    }

    /**
     * @param angle angle to rotate by
     * @return result of rotation
     */
    public Vector3D rotate(Angle angle) {
        return new Vector3D(
                this.x * angle.cos() - this.y * angle.sin(),
                this.y * angle.cos() + this.x * angle.sin());
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
        return new Vector3D(this.x - other.x, this.y - other.y);
    }

    /**
     * @param other Vector3D to be multiplied by
     * @return product of the two Vector3Ds
     */
    public Vector3D mul(Vector3D other) {
        return new Vector3D(this.x * other.x, this.y * other.y);
    }

    /**
     * @param other Vector3D to be divided by
     * @return division of the two Vector3Ds
     */
    public Vector3D div(Vector3D other) {
        return new Vector3D(this.x / other.x, this.y / other.y);
    }

    /**
     * @param multiplier amount to multiply the x and y components by
     * @return result of multiplying the x and y components by the multiplier
     */
    public Vector3D mul(double multiplier) {
        return new Vector3D(this.x * multiplier, this.y * multiplier);
    }

    /**
     * @param divisor amount to divide the x and y components by
     * @return result of dividing the x and y components by the divisor
     */
    public Vector3D div(double divisor) {
        return new Vector3D(this.x / divisor, this.y / divisor);
    }

    /**
     * @param other Vector3D to perform dot product with
     * @return result of performing the dot product with the other Vector3D
     */
    public double dot(Vector3D other) {
        return this.x * other.x + this.y * other.y;
    }

    /** @return result of normalizing the Vector3D so that the magnitude is 1.0 */
    public Vector3D normalize() {
        final double magnitude = this.distance();
        if (SLMath.isZero(magnitude)) {
            return Vector3D.kI;
        } else {
            return this.div(magnitude);
        }
    }

    /** @return result of negating the x, y, and z components */
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
     * @return hashCode generated by combining the hashes of the x and y components
     */
    @Override
    public int hashCode() {
        return HashBuilder.combineHash(Double.hashCode(x), Double.hashCode(y));
    }

    /** @return string representation of Vector3D */
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
