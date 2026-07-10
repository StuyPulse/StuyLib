/* Copyright (c) 2026 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math;

/**
 * SLMath (StuyLib Math) is a class containing many algorithms that are useful for developing robot
 * code. Algorithms include limit, deadband, raising to powers while keeping the sign, and some
 * other new algorithms we came up with.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public final class SLMath {

    // Prevent the class from being extended at all
    private SLMath() {}

    /**************/
    /*** LIMITS ***/
    /**************/

    /**
     * Clamps a value to the inclusive range defined by {@code min} and {@code max}.
     * The order of {@code min} and {@code max} does not matter.
     *
     * @param x input
     * @param min one bound of the range
     * @param max the other bound of the range
     * @return clamped input
     */
    public static double clamp(double x, double min, double max) {
        double lower = Math.min(min, max);
        double upper = Math.max(min, max);
    
        if (x < lower) return lower;
        if (x > upper) return upper;
        return x;
    }

    /**
     * clamp input from max to -max
     *
     * @param x input
     * @param max max and min value
     * @return clamped input
     */
    public static double clamp(double x, double max) {
        return clamp(x, -max, max);
    }

    /**
     * clamp input from -1 to 1
     *
     * @param x input
     * @return clamped input
     */
    public static double clamp(double x) {
        return clamp(x, 1.0);
    }

    /**************************/
    /*** DEADBAND ALGORITHM ***/
    /**************************/

    /**
     * Dead bands x value with window being the dead band. all values for this are [-1.0...1.0]
     *
     * @param x value
     * @param window deadband window
     * @return deadbanded value
     */
    public static double deadband(double x, double window) {
        window = Math.abs(window);

        if (Math.abs(x) < window) {
            return 0.0;
        } else {
            return (x - Math.copySign(window, x)) / (1.0 - window);
        }
    }

    /*****************************************/
    /*** RAISE TO POWER WHILE KEEPING SIGN ***/
    /*****************************************/

    /**
     * spow (signless pow), raises a number to a power without affecting the sign of the number
     *
     * @param x input
     * @param power power to raise x to
     * @return input ^ power
     */
    public static double spow(double x, double power) {
        return Math.pow(Math.abs(x), power) * Math.signum(x);
    }
}
