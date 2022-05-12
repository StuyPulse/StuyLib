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

    /** @return double array of size 3 defined as {x, y, z} */
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

    /** @return distance from 0, 0, 0 */
    public double distance() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /** @return magnitude of the vector (same as distance from 0, 0, 0) */
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

    /** @return result of normalizing the Vector3D so that the magnitude is 1.0 */
    public Vector3D normalize() {
        double magnitude = this.distance();
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
     * @return hashCode generated by combining the hashes of the x, y, and z components
     */
    @Override
    public int hashCode() {
        return new HashBuilder().append(this.x).append(this.y).append(this.z).toHashCode();
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

    /**************************/
    /*** Swizzled Vector2Ds ***/
    /**************************/

    /** @return swizzled Vector2D with components x, x */
    public Vector2D xx() {
        return new Vector2D(x, x);
    }

    /** @return swizzled Vector2D with components x, y */
    public Vector2D xy() {
        return new Vector2D(x, y);
    }

    /** @return swizzled Vector2D with components x, z */
    public Vector2D xz() {
        return new Vector2D(x, z);
    }

    /** @return swizzled Vector2D with components x, 0 */
    public Vector2D x_() {
        return new Vector2D(x, 0);
    }

    /** @return swizzled Vector2D with components y, x */
    public Vector2D yx() {
        return new Vector2D(y, x);
    }

    /** @return swizzled Vector2D with components y, y */
    public Vector2D yy() {
        return new Vector2D(y, y);
    }

    /** @return swizzled Vector2D with components y, z */
    public Vector2D yz() {
        return new Vector2D(y, z);
    }

    /** @return swizzled Vector2D with components y, 0 */
    public Vector2D y_() {
        return new Vector2D(y, 0);
    }

    /** @return swizzled Vector2D with components z, x */
    public Vector2D zx() {
        return new Vector2D(z, x);
    }

    /** @return swizzled Vector2D with components z, y */
    public Vector2D zy() {
        return new Vector2D(z, y);
    }

    /** @return swizzled Vector2D with components z, z */
    public Vector2D zz() {
        return new Vector2D(z, z);
    }

    /** @return swizzled Vector2D with components z, 0 */
    public Vector2D z_() {
        return new Vector2D(z, 0);
    }

    /** @return swizzled Vector2D with components 0, x */
    public Vector2D _x() {
        return new Vector2D(0, x);
    }

    /** @return swizzled Vector2D with components 0, y */
    public Vector2D _y() {
        return new Vector2D(0, y);
    }

    /** @return swizzled Vector2D with components 0, z */
    public Vector2D _z() {
        return new Vector2D(0, z);
    }

    /**************************/
    /*** Swizzled Vector3Ds ***/
    /**************************/

    /** @return swizzled Vector3D with components x, x, x */
    public Vector3D xxx() {
        return new Vector3D(x, x, x);
    }

    /** @return swizzled Vector3D with components x, x, y */
    public Vector3D xxy() {
        return new Vector3D(x, x, y);
    }

    /** @return swizzled Vector3D with components x, x, z */
    public Vector3D xxz() {
        return new Vector3D(x, x, z);
    }

    /** @return swizzled Vector3D with components x, x, 0 */
    public Vector3D xx_() {
        return new Vector3D(x, x, 0);
    }

    /** @return swizzled Vector3D with components x, y, x */
    public Vector3D xyx() {
        return new Vector3D(x, y, x);
    }

    /** @return swizzled Vector3D with components x, y, y */
    public Vector3D xyy() {
        return new Vector3D(x, y, y);
    }

    /** @return swizzled Vector3D with components x, y, z */
    public Vector3D xyz() {
        return new Vector3D(x, y, z);
    }

    /** @return swizzled Vector3D with components x, y, 0 */
    public Vector3D xy_() {
        return new Vector3D(x, y, 0);
    }

    /** @return swizzled Vector3D with components x, z, x */
    public Vector3D xzx() {
        return new Vector3D(x, z, x);
    }

    /** @return swizzled Vector3D with components x, z, y */
    public Vector3D xzy() {
        return new Vector3D(x, z, y);
    }

    /** @return swizzled Vector3D with components x, z, z */
    public Vector3D xzz() {
        return new Vector3D(x, z, z);
    }

    /** @return swizzled Vector3D with components x, z, 0 */
    public Vector3D xz_() {
        return new Vector3D(x, z, 0);
    }

    /** @return swizzled Vector3D with components x, 0, x */
    public Vector3D x_x() {
        return new Vector3D(x, 0, x);
    }

    /** @return swizzled Vector3D with components x, 0, y */
    public Vector3D x_y() {
        return new Vector3D(x, 0, y);
    }

    /** @return swizzled Vector3D with components x, 0, z */
    public Vector3D x_z() {
        return new Vector3D(x, 0, z);
    }

    /** @return swizzled Vector3D with components x, 0, 0 */
    public Vector3D x__() {
        return new Vector3D(x, 0, 0);
    }

    /** @return swizzled Vector3D with components y, x, x */
    public Vector3D yxx() {
        return new Vector3D(y, x, x);
    }

    /** @return swizzled Vector3D with components y, x, y */
    public Vector3D yxy() {
        return new Vector3D(y, x, y);
    }

    /** @return swizzled Vector3D with components y, x, z */
    public Vector3D yxz() {
        return new Vector3D(y, x, z);
    }

    /** @return swizzled Vector3D with components y, x, 0 */
    public Vector3D yx_() {
        return new Vector3D(y, x, 0);
    }

    /** @return swizzled Vector3D with components y, y, x */
    public Vector3D yyx() {
        return new Vector3D(y, y, x);
    }

    /** @return swizzled Vector3D with components y, y, y */
    public Vector3D yyy() {
        return new Vector3D(y, y, y);
    }

    /** @return swizzled Vector3D with components y, y, z */
    public Vector3D yyz() {
        return new Vector3D(y, y, z);
    }

    /** @return swizzled Vector3D with components y, y, 0 */
    public Vector3D yy_() {
        return new Vector3D(y, y, 0);
    }

    /** @return swizzled Vector3D with components y, z, x */
    public Vector3D yzx() {
        return new Vector3D(y, z, x);
    }

    /** @return swizzled Vector3D with components y, z, y */
    public Vector3D yzy() {
        return new Vector3D(y, z, y);
    }

    /** @return swizzled Vector3D with components y, z, z */
    public Vector3D yzz() {
        return new Vector3D(y, z, z);
    }

    /** @return swizzled Vector3D with components y, z, 0 */
    public Vector3D yz_() {
        return new Vector3D(y, z, 0);
    }

    /** @return swizzled Vector3D with components y, 0, x */
    public Vector3D y_x() {
        return new Vector3D(y, 0, x);
    }

    /** @return swizzled Vector3D with components y, 0, y */
    public Vector3D y_y() {
        return new Vector3D(y, 0, y);
    }

    /** @return swizzled Vector3D with components y, 0, z */
    public Vector3D y_z() {
        return new Vector3D(y, 0, z);
    }

    /** @return swizzled Vector3D with components y, 0, 0 */
    public Vector3D y__() {
        return new Vector3D(y, 0, 0);
    }

    /** @return swizzled Vector3D with components z, x, x */
    public Vector3D zxx() {
        return new Vector3D(z, x, x);
    }

    /** @return swizzled Vector3D with components z, x, y */
    public Vector3D zxy() {
        return new Vector3D(z, x, y);
    }

    /** @return swizzled Vector3D with components z, x, z */
    public Vector3D zxz() {
        return new Vector3D(z, x, z);
    }

    /** @return swizzled Vector3D with components z, x, 0 */
    public Vector3D zx_() {
        return new Vector3D(z, x, 0);
    }

    /** @return swizzled Vector3D with components z, y, x */
    public Vector3D zyx() {
        return new Vector3D(z, y, x);
    }

    /** @return swizzled Vector3D with components z, y, y */
    public Vector3D zyy() {
        return new Vector3D(z, y, y);
    }

    /** @return swizzled Vector3D with components z, y, z */
    public Vector3D zyz() {
        return new Vector3D(z, y, z);
    }

    /** @return swizzled Vector3D with components z, y, 0 */
    public Vector3D zy_() {
        return new Vector3D(z, y, 0);
    }

    /** @return swizzled Vector3D with components z, z, x */
    public Vector3D zzx() {
        return new Vector3D(z, z, x);
    }

    /** @return swizzled Vector3D with components z, z, y */
    public Vector3D zzy() {
        return new Vector3D(z, z, y);
    }

    /** @return swizzled Vector3D with components z, z, z */
    public Vector3D zzz() {
        return new Vector3D(z, z, z);
    }

    /** @return swizzled Vector3D with components z, z, 0 */
    public Vector3D zz_() {
        return new Vector3D(z, z, 0);
    }

    /** @return swizzled Vector3D with components z, 0, x */
    public Vector3D z_x() {
        return new Vector3D(z, 0, x);
    }

    /** @return swizzled Vector3D with components z, 0, y */
    public Vector3D z_y() {
        return new Vector3D(z, 0, y);
    }

    /** @return swizzled Vector3D with components z, 0, z */
    public Vector3D z_z() {
        return new Vector3D(z, 0, z);
    }

    /** @return swizzled Vector3D with components z, 0, 0 */
    public Vector3D z__() {
        return new Vector3D(z, 0, 0);
    }

    /** @return swizzled Vector3D with components 0, x, x */
    public Vector3D _xx() {
        return new Vector3D(0, x, x);
    }

    /** @return swizzled Vector3D with components 0, x, y */
    public Vector3D _xy() {
        return new Vector3D(0, x, y);
    }

    /** @return swizzled Vector3D with components 0, x, z */
    public Vector3D _xz() {
        return new Vector3D(0, x, z);
    }

    /** @return swizzled Vector3D with components 0, x, 0 */
    public Vector3D _x_() {
        return new Vector3D(0, x, 0);
    }

    /** @return swizzled Vector3D with components 0, y, x */
    public Vector3D _yx() {
        return new Vector3D(0, y, x);
    }

    /** @return swizzled Vector3D with components 0, y, y */
    public Vector3D _yy() {
        return new Vector3D(0, y, y);
    }

    /** @return swizzled Vector3D with components 0, y, z */
    public Vector3D _yz() {
        return new Vector3D(0, y, z);
    }

    /** @return swizzled Vector3D with components 0, y, 0 */
    public Vector3D _y_() {
        return new Vector3D(0, y, 0);
    }

    /** @return swizzled Vector3D with components 0, z, x */
    public Vector3D _zx() {
        return new Vector3D(0, z, x);
    }

    /** @return swizzled Vector3D with components 0, z, y */
    public Vector3D _zy() {
        return new Vector3D(0, z, y);
    }

    /** @return swizzled Vector3D with components 0, z, z */
    public Vector3D _zz() {
        return new Vector3D(0, z, z);
    }

    /** @return swizzled Vector3D with components 0, z, 0 */
    public Vector3D _z_() {
        return new Vector3D(0, z, 0);
    }

    /** @return swizzled Vector3D with components 0, 0, x */
    public Vector3D __x() {
        return new Vector3D(0, 0, x);
    }

    /** @return swizzled Vector3D with components 0, 0, y */
    public Vector3D __y() {
        return new Vector3D(0, 0, y);
    }

    /** @return swizzled Vector3D with components 0, 0, z */
    public Vector3D __z() {
        return new Vector3D(0, 0, z);
    }
}
