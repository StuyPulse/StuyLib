/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network.limelight;

import com.stuypulse.stuylib.math.Angle;

/**
 * Class that is used to return solve 3D results from the limelight
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class Solve3DResult {

    // Position Data
    private double mX;
    private double mY;
    private double mZ;

    // Rotation Data
    private Angle mPitch;
    private Angle mYaw;
    private Angle mRoll;

    /**
     * Initialize each part of the Solve3DResult
     *
     * @param x x position
     * @param y y position
     * @param z z position
     * @param pitch pitch
     * @param yaw yaw
     * @param roll roll
     */
    public Solve3DResult(double x, double y, double z, double pitch, double yaw, double roll) {
        mX = x;
        mY = y;
        mZ = z;

        mPitch = Angle.fromDegrees(pitch);
        mYaw = Angle.fromDegrees(yaw);
        mRoll = Angle.fromDegrees(roll);
    }

    /**
     * Parse data from array into class
     *
     * @param data array that the limelight returns
     */
    public Solve3DResult(double[] data) {
        this(data[0], data[1], data[2], data[3], data[4], data[5]);
    }

    /**
     * Get x from Solve3D Result
     *
     * @return x position
     */
    public double getX() {
        return mX;
    }

    /**
     * Get y from Solve3D Result
     *
     * @return y position
     */
    public double getY() {
        return mY;
    }

    /**
     * Get z from Solve3D Result
     *
     * @return z position
     */
    public double getZ() {
        return mZ;
    }

    /**
     * Get pitch from Solve3D Result
     *
     * @return pitch
     */
    public Angle getPitch() {
        return mPitch;
    }

    /**
     * Get yaw from Solve3D Result
     *
     * @return yaw
     */
    public Angle getYaw() {
        return mYaw;
    }

    /**
     * Get roll from Solve3D Result
     *
     * @return roll
     */
    public Angle getRoll() {
        return mRoll;
    }
}
