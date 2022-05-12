/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math;

public final class Swizzler {

    private Swizzler() {
        /** This is a utility class */
    }

    /************************/
    /*** Swizzled doubles ***/
    /************************/

    /**
     * @param v double to swizzle with
     * @return swizzled double with component v
     */
    public static double x(double v) {
        return v;
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector2D with components v, v
     */
    public static Vector2D xx(double v) {
        return new Vector2D(v, v);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector3D with components v, v, v
     */
    public static Vector3D xxx(double v) {
        return new Vector3D(v, v, v);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector3D with components v, v, 0
     */
    public static Vector3D xx_(double v) {
        return new Vector3D(v, v, 0.0);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector2D with components v, 0
     */
    public static Vector2D x_(double v) {
        return new Vector2D(v, 0.0);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector3D with components v, 0, v
     */
    public static Vector3D x_x(double v) {
        return new Vector3D(v, 0.0, v);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector3D with components v, 0, 0
     */
    public static Vector3D x__(double v) {
        return new Vector3D(v, 0.0, 0.0);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector2D with components 0, v
     */
    public static Vector2D _x(double v) {
        return new Vector2D(0.0, v);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector3D with components 0, v, v
     */
    public static Vector3D _xx(double v) {
        return new Vector3D(0.0, v, v);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector3D with components 0, v, 0
     */
    public static Vector3D _x_(double v) {
        return new Vector3D(0.0, v, 0.0);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector2D with components 0, 0
     */
    public static Vector2D __(double v) {
        return new Vector2D(0.0, 0.0);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector3D with components 0, 0, v
     */
    public static Vector3D __x(double v) {
        return new Vector3D(0.0, 0.0, v);
    }

    /**
     * @param v double to swizzle with
     * @return swizzled Vector3D with components 0, 0, 0
     */
    public static Vector3D ___(double v) {
        return new Vector3D(0.0, 0.0, 0.0);
    }

    /**************************/
    /*** Swizzled Vector2Ds ***/
    /**************************/

    /**
     * @param v vector to swizzle with
     * @return swizzled double with component x
     */
    public static double x(Vector2D v) {
        return v.x;
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components x, x
     */
    public static Vector2D xx(Vector2D v) {
        return new Vector2D(v.x, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, x, x
     */
    public static Vector3D xxx(Vector2D v) {
        return new Vector3D(v.x, v.x, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, x, y
     */
    public static Vector3D xxy(Vector2D v) {
        return new Vector3D(v.x, v.x, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, x, 0
     */
    public static Vector3D xx_(Vector2D v) {
        return new Vector3D(v.x, v.x, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components x, y
     */
    public static Vector2D xy(Vector2D v) {
        return new Vector2D(v.x, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, y, x
     */
    public static Vector3D xyx(Vector2D v) {
        return new Vector3D(v.x, v.y, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, y, y
     */
    public static Vector3D xyy(Vector2D v) {
        return new Vector3D(v.x, v.y, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, y, 0
     */
    public static Vector3D xy_(Vector2D v) {
        return new Vector3D(v.x, v.y, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components x, 0
     */
    public static Vector2D x_(Vector2D v) {
        return new Vector2D(v.x, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, 0, x
     */
    public static Vector3D x_x(Vector2D v) {
        return new Vector3D(v.x, 0.0, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, 0, y
     */
    public static Vector3D x_y(Vector2D v) {
        return new Vector3D(v.x, 0.0, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, 0, 0
     */
    public static Vector3D x__(Vector2D v) {
        return new Vector3D(v.x, 0.0, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled double with component y
     */
    public static double y(Vector2D v) {
        return v.y;
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components y, x
     */
    public static Vector2D yx(Vector2D v) {
        return new Vector2D(v.y, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, x, x
     */
    public static Vector3D yxx(Vector2D v) {
        return new Vector3D(v.y, v.x, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, x, y
     */
    public static Vector3D yxy(Vector2D v) {
        return new Vector3D(v.y, v.x, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, x, 0
     */
    public static Vector3D yx_(Vector2D v) {
        return new Vector3D(v.y, v.x, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components y, y
     */
    public static Vector2D yy(Vector2D v) {
        return new Vector2D(v.y, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, y, x
     */
    public static Vector3D yyx(Vector2D v) {
        return new Vector3D(v.y, v.y, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, y, y
     */
    public static Vector3D yyy(Vector2D v) {
        return new Vector3D(v.y, v.y, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, y, 0
     */
    public static Vector3D yy_(Vector2D v) {
        return new Vector3D(v.y, v.y, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components y, 0
     */
    public static Vector2D y_(Vector2D v) {
        return new Vector2D(v.y, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, 0, x
     */
    public static Vector3D y_x(Vector2D v) {
        return new Vector3D(v.y, 0.0, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, 0, y
     */
    public static Vector3D y_y(Vector2D v) {
        return new Vector3D(v.y, 0.0, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, 0, 0
     */
    public static Vector3D y__(Vector2D v) {
        return new Vector3D(v.y, 0.0, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components 0, x
     */
    public static Vector2D _x(Vector2D v) {
        return new Vector2D(0.0, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, x, x
     */
    public static Vector3D _xx(Vector2D v) {
        return new Vector3D(0.0, v.x, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, x, y
     */
    public static Vector3D _xy(Vector2D v) {
        return new Vector3D(0.0, v.x, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, x, 0
     */
    public static Vector3D _x_(Vector2D v) {
        return new Vector3D(0.0, v.x, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components 0, y
     */
    public static Vector2D _y(Vector2D v) {
        return new Vector2D(0.0, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, y, x
     */
    public static Vector3D _yx(Vector2D v) {
        return new Vector3D(0.0, v.y, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, y, y
     */
    public static Vector3D _yy(Vector2D v) {
        return new Vector3D(0.0, v.y, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, y, 0
     */
    public static Vector3D _y_(Vector2D v) {
        return new Vector3D(0.0, v.y, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components 0, 0
     */
    public static Vector2D __(Vector2D v) {
        return new Vector2D(0.0, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, 0, x
     */
    public static Vector3D __x(Vector2D v) {
        return new Vector3D(0.0, 0.0, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, 0, y
     */
    public static Vector3D __y(Vector2D v) {
        return new Vector3D(0.0, 0.0, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, 0, 0
     */
    public static Vector3D ___(Vector2D v) {
        return new Vector3D(0.0, 0.0, 0.0);
    }

    /**************************/
    /*** Swizzled Vector3Ds ***/
    /**************************/

    /**
     * @param v vector to swizzle with
     * @return swizzled double with component x
     */
    public static double x(Vector3D v) {
        return v.x;
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components x, x
     */
    public static Vector2D xx(Vector3D v) {
        return new Vector2D(v.x, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, x, x
     */
    public static Vector3D xxx(Vector3D v) {
        return new Vector3D(v.x, v.x, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, x, y
     */
    public static Vector3D xxy(Vector3D v) {
        return new Vector3D(v.x, v.x, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, x, z
     */
    public static Vector3D xxz(Vector3D v) {
        return new Vector3D(v.x, v.x, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, x, 0
     */
    public static Vector3D xx_(Vector3D v) {
        return new Vector3D(v.x, v.x, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components x, y
     */
    public static Vector2D xy(Vector3D v) {
        return new Vector2D(v.x, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, y, x
     */
    public static Vector3D xyx(Vector3D v) {
        return new Vector3D(v.x, v.y, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, y, y
     */
    public static Vector3D xyy(Vector3D v) {
        return new Vector3D(v.x, v.y, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, y, z
     */
    public static Vector3D xyz(Vector3D v) {
        return new Vector3D(v.x, v.y, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, y, 0
     */
    public static Vector3D xy_(Vector3D v) {
        return new Vector3D(v.x, v.y, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components x, z
     */
    public static Vector2D xz(Vector3D v) {
        return new Vector2D(v.x, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, z, x
     */
    public static Vector3D xzx(Vector3D v) {
        return new Vector3D(v.x, v.z, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, z, y
     */
    public static Vector3D xzy(Vector3D v) {
        return new Vector3D(v.x, v.z, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, z, z
     */
    public static Vector3D xzz(Vector3D v) {
        return new Vector3D(v.x, v.z, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, z, 0
     */
    public static Vector3D xz_(Vector3D v) {
        return new Vector3D(v.x, v.z, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components x, 0
     */
    public static Vector2D x_(Vector3D v) {
        return new Vector2D(v.x, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, 0, x
     */
    public static Vector3D x_x(Vector3D v) {
        return new Vector3D(v.x, 0.0, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, 0, y
     */
    public static Vector3D x_y(Vector3D v) {
        return new Vector3D(v.x, 0.0, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, 0, z
     */
    public static Vector3D x_z(Vector3D v) {
        return new Vector3D(v.x, 0.0, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components x, 0, 0
     */
    public static Vector3D x__(Vector3D v) {
        return new Vector3D(v.x, 0.0, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled double with component y
     */
    public static double y(Vector3D v) {
        return v.y;
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components y, x
     */
    public static Vector2D yx(Vector3D v) {
        return new Vector2D(v.y, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, x, x
     */
    public static Vector3D yxx(Vector3D v) {
        return new Vector3D(v.y, v.x, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, x, y
     */
    public static Vector3D yxy(Vector3D v) {
        return new Vector3D(v.y, v.x, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, x, z
     */
    public static Vector3D yxz(Vector3D v) {
        return new Vector3D(v.y, v.x, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, x, 0
     */
    public static Vector3D yx_(Vector3D v) {
        return new Vector3D(v.y, v.x, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components y, y
     */
    public static Vector2D yy(Vector3D v) {
        return new Vector2D(v.y, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, y, x
     */
    public static Vector3D yyx(Vector3D v) {
        return new Vector3D(v.y, v.y, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, y, y
     */
    public static Vector3D yyy(Vector3D v) {
        return new Vector3D(v.y, v.y, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, y, z
     */
    public static Vector3D yyz(Vector3D v) {
        return new Vector3D(v.y, v.y, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, y, 0
     */
    public static Vector3D yy_(Vector3D v) {
        return new Vector3D(v.y, v.y, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components y, z
     */
    public static Vector2D yz(Vector3D v) {
        return new Vector2D(v.y, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, z, x
     */
    public static Vector3D yzx(Vector3D v) {
        return new Vector3D(v.y, v.z, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, z, y
     */
    public static Vector3D yzy(Vector3D v) {
        return new Vector3D(v.y, v.z, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, z, z
     */
    public static Vector3D yzz(Vector3D v) {
        return new Vector3D(v.y, v.z, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, z, 0
     */
    public static Vector3D yz_(Vector3D v) {
        return new Vector3D(v.y, v.z, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components y, 0
     */
    public static Vector2D y_(Vector3D v) {
        return new Vector2D(v.y, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, 0, x
     */
    public static Vector3D y_x(Vector3D v) {
        return new Vector3D(v.y, 0.0, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, 0, y
     */
    public static Vector3D y_y(Vector3D v) {
        return new Vector3D(v.y, 0.0, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, 0, z
     */
    public static Vector3D y_z(Vector3D v) {
        return new Vector3D(v.y, 0.0, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components y, 0, 0
     */
    public static Vector3D y__(Vector3D v) {
        return new Vector3D(v.y, 0.0, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled double with component z
     */
    public static double z(Vector3D v) {
        return v.z;
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components z, x
     */
    public static Vector2D zx(Vector3D v) {
        return new Vector2D(v.z, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, x, x
     */
    public static Vector3D zxx(Vector3D v) {
        return new Vector3D(v.z, v.x, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, x, y
     */
    public static Vector3D zxy(Vector3D v) {
        return new Vector3D(v.z, v.x, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, x, z
     */
    public static Vector3D zxz(Vector3D v) {
        return new Vector3D(v.z, v.x, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, x, 0
     */
    public static Vector3D zx_(Vector3D v) {
        return new Vector3D(v.z, v.x, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components z, y
     */
    public static Vector2D zy(Vector3D v) {
        return new Vector2D(v.z, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, y, x
     */
    public static Vector3D zyx(Vector3D v) {
        return new Vector3D(v.z, v.y, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, y, y
     */
    public static Vector3D zyy(Vector3D v) {
        return new Vector3D(v.z, v.y, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, y, z
     */
    public static Vector3D zyz(Vector3D v) {
        return new Vector3D(v.z, v.y, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, y, 0
     */
    public static Vector3D zy_(Vector3D v) {
        return new Vector3D(v.z, v.y, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components z, z
     */
    public static Vector2D zz(Vector3D v) {
        return new Vector2D(v.z, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, z, x
     */
    public static Vector3D zzx(Vector3D v) {
        return new Vector3D(v.z, v.z, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, z, y
     */
    public static Vector3D zzy(Vector3D v) {
        return new Vector3D(v.z, v.z, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, z, z
     */
    public static Vector3D zzz(Vector3D v) {
        return new Vector3D(v.z, v.z, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, z, 0
     */
    public static Vector3D zz_(Vector3D v) {
        return new Vector3D(v.z, v.z, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components z, 0
     */
    public static Vector2D z_(Vector3D v) {
        return new Vector2D(v.z, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, 0, x
     */
    public static Vector3D z_x(Vector3D v) {
        return new Vector3D(v.z, 0.0, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, 0, y
     */
    public static Vector3D z_y(Vector3D v) {
        return new Vector3D(v.z, 0.0, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, 0, z
     */
    public static Vector3D z_z(Vector3D v) {
        return new Vector3D(v.z, 0.0, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components z, 0, 0
     */
    public static Vector3D z__(Vector3D v) {
        return new Vector3D(v.z, 0.0, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components 0, x
     */
    public static Vector2D _x(Vector3D v) {
        return new Vector2D(0.0, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, x, x
     */
    public static Vector3D _xx(Vector3D v) {
        return new Vector3D(0.0, v.x, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, x, y
     */
    public static Vector3D _xy(Vector3D v) {
        return new Vector3D(0.0, v.x, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, x, z
     */
    public static Vector3D _xz(Vector3D v) {
        return new Vector3D(0.0, v.x, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, x, 0
     */
    public static Vector3D _x_(Vector3D v) {
        return new Vector3D(0.0, v.x, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components 0, y
     */
    public static Vector2D _y(Vector3D v) {
        return new Vector2D(0.0, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, y, x
     */
    public static Vector3D _yx(Vector3D v) {
        return new Vector3D(0.0, v.y, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, y, y
     */
    public static Vector3D _yy(Vector3D v) {
        return new Vector3D(0.0, v.y, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, y, z
     */
    public static Vector3D _yz(Vector3D v) {
        return new Vector3D(0.0, v.y, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, y, 0
     */
    public static Vector3D _y_(Vector3D v) {
        return new Vector3D(0.0, v.y, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components 0, z
     */
    public static Vector2D _z(Vector3D v) {
        return new Vector2D(0.0, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, z, x
     */
    public static Vector3D _zx(Vector3D v) {
        return new Vector3D(0.0, v.z, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, z, y
     */
    public static Vector3D _zy(Vector3D v) {
        return new Vector3D(0.0, v.z, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, z, z
     */
    public static Vector3D _zz(Vector3D v) {
        return new Vector3D(0.0, v.z, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, z, 0
     */
    public static Vector3D _z_(Vector3D v) {
        return new Vector3D(0.0, v.z, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector2D with components 0, 0
     */
    public static Vector2D __(Vector3D v) {
        return new Vector2D(0.0, 0.0);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, 0, x
     */
    public static Vector3D __x(Vector3D v) {
        return new Vector3D(0.0, 0.0, v.x);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, 0, y
     */
    public static Vector3D __y(Vector3D v) {
        return new Vector3D(0.0, 0.0, v.y);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, 0, z
     */
    public static Vector3D __z(Vector3D v) {
        return new Vector3D(0.0, 0.0, v.z);
    }

    /**
     * @param v vector to swizzle with
     * @return swizzled Vector3D with components 0, 0, 0
     */
    public static Vector3D ___(Vector3D v) {
        return new Vector3D(0.0, 0.0, 0.0);
    }
}
