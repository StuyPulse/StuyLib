package com.stuypulse.stuylib.math;

/**
 * SLMath (StuyLib Math) is a class containing many algorithms that are useful
 * for developing robot code. Algorithms include limit, deadband, raising to
 * powers while keeping the sign, and some other new algorithms we came up with.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class SLMath {

    /**************/
    /*** LIMITS ***/
    /**************/

    /**
     * Limit input from max to min
     * 
     * @param x   input
     * @param min min value for x
     * @param max max value for x
     * @return limited input
     */
    public static double limit(double x, double min, double max) {
        if (x > max)
            return max;
        if (x < min)
            return min;
        return x;
    }

    /**
     * Limit input from max to -max
     * 
     * @param x   input
     * @param max max and min value
     * @return limited input
     */
    public static double limit(double x, double max) {
        return limit(x, -max, max);
    }

    /**
     * Limit input from -1 to 1
     * 
     * @param x input
     * @return limited input
     */
    public static double limit(double x) {
        return limit(x, 1.0);
    }

    /**************************/
    /*** DEADBAND ALGORIGHM ***/
    /**************************/

    /**
     * Apply Deadband to value
     * 
     * @param x      value
     * @param window deadband window
     * @return deadbanded value
     */
    public static double deadband(double x, double window) {
        if (Math.abs(x) < window) {
            return 0.0;
        } else {
            return x;
        }
    }

    /*****************************************/
    /*** RAISE TO POWER WHILE KEEPING SIGN ***/
    /*****************************************/

    /**
     * [WARNING! THIS WILL KEEP THE SIGN OF THE INPUT NUMBER] Square number and keep
     * sign
     * 
     * @param x input
     * @return square input
     */
    public static double square(double x) {
        return limit(x * x * Math.signum(x));
    }

    /**
     * Cube number
     * 
     * @param x input
     * @return cubed input
     */
    public static double cube(double x) {
        return limit(x * x * x);
    }

    /**
     * [WARNING! THIS WILL KEEP THE SIGN OF THE INPUT NUMBER] Raise input to power
     * and keep sign
     * 
     * @param x     input
     * @param power power to raise x to
     * @return input ^ power
     */
    public static double pow(double x, double power) {
        return limit(Math.pow(Math.abs(x), power) * Math.signum(x));
    }

    /**************************/
    /*** CIRCULAR ALGORITHM ***/
    /**************************/

    /**
     * Use the shape of a circle with power p to scale the input
     * 
     * @param x input
     * @param p power (p greater than 1)
     * @return circular return
     */
    public static double circular(double x, double p) {
        double sign = Math.signum(x);
        x = limit(Math.abs(x));

        p = Math.max(1.0, p);

        x = 1.0 - Math.pow(x, p);
        x = 1.0 - Math.pow(x, 1.0 / p);
        x *= sign;

        return limit(x);
    }

    /**
     * Use the shape of a circle to scale the input
     * 
     * @param x input
     * @return circular return
     */
    public static double circular(double x) {
        return circular(x, 2);
    }
}