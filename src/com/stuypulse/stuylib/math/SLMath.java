// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

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
     * clamp input from max to min
     *
     * @param x input
     * @param min min value for x
     * @param max max value for x
     * @return clamp input
     */
    public static double clamp(double x, double min, double max) {
        if (min < max) {
            if (x > max) return max;
            if (x < min) return min;
        } else {
            if (x > min) return min;
            if (x < max) return max;
        }
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
     * [WARNING! THIS WILL KEEP THE SIGN OF THE INPUT NUMBER] Square number and keep sign
     *
     * @param x input
     * @return squared input with the same sign
     */
    public static double square(double x) {
        return clamp(x * x * Math.signum(x));
    }

    /**
     * Cube a number
     *
     * @param x input
     * @return cubed input that
     */
    public static double cube(double x) {
        return x * x * x;
    }

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

    /*****************/
    /*** MISC MATH ***/
    /*****************/

    /**
     * fpow (fast pow), is a pow function that takes in an integer for the exponent. This allows it
     * to be much faster on repeated calls due to the fact that it does not need to deal with
     * fractional exponents.
     *
     * @param base base of the power
     * @param exp integer exponent of power
     * @return result of calculation
     */
    public static double fpow(double base, int exp) {
        // Output of the fpow function
        double out = 1.0;

        // If the exponent is negative, divide instead of multiply
        if (exp < 0) {
            // Flip exponent to make calculations easier
            exp = -exp;

            // Fast integer power algorithm
            while (exp > 0) {
                if ((exp & 1) == 1) {
                    out /= base;
                }
                base *= base;
                exp >>= 1;
            }
        } else {
            // Fast integer power algorithm
            while (exp > 0) {
                if ((exp & 1) == 1) {
                    out *= base;
                }
                base *= base;
                exp >>= 1;
            }
        }

        // Return
        return out;
    }

    /**
     * Round a double by a certain amount of sigfigs in base 10
     *
     * @param n number to round
     * @param sigfigs amount of sigfigs to round it to
     * @return rounded number
     */
    public static double round(double n, int sigfigs) {
        // The value 0 returns nan if not accounted for
        if (n == 0.0) {
            return 0.0;
        }

        // Digit place that number starts at
        int digits = (int) Math.floor(Math.log10(Math.abs(n)));

        // Amount to multiply before multiplying based on
        // the sigfigs and digits in the number
        double mul = fpow(10.0, sigfigs - digits);

        // Round number by the multiplier calculated
        return Math.round(n * mul) / mul;
    }
}
