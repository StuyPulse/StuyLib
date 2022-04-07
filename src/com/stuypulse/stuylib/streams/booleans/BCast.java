/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans;

/**
 * Class used for converting Booleans to Doubles and Doubles to Booleans for use in
 * IStreams/BStreams
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public final class BCast {

    private BCast() {
        /* This is a utility class */
    }

    public static final double kTrue = 1.0;
    public static final double kFalse = 0.0;

    public static final double kThreshold = 0.5;

    public static boolean toBoolean(double x) {
        return Math.abs(x) >= kThreshold;
    }

    public static double toDouble(boolean x) {
        return x ? kTrue : kFalse;
    }
}
