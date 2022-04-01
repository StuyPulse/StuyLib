/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network.limelight;

/**
 * This class defines all of the constants defined in the Limelight Documentation. Usage of these
 * numbers is rare, but this is good to have.
 *
 * @author Sam B (sam.belliveau@gmail.com)
 */
public final class LimelightConstants {

    /** Prevent class from being extended. */
    private LimelightConstants() {}

    /*************************/
    /*** Camera Mode Enums ***/
    /*************************/

    public static final double IMAGE_CAPTURE_LATENCY = 11;

    public static final double MIN_X_ANGLE = -27;
    public static final double MAX_X_ANGLE = 27;

    public static final double MIN_Y_ANGLE = -20.5;
    public static final double MAX_Y_ANGLE = 20.5;

    public static final double MIN_TARGET_AREA = 0;
    public static final double MAX_TARGET_AREA = 1;

    public static final double MIN_SKEW = -90;
    public static final double MAX_SKEW = 0;

    public static final double MIN_SIDE_LENGTH = 0;
    public static final double MAX_SIDE_LENGTH = 320;
}
