package com.stuypulse.stuylib.math;

/** 
 * The input scalar class is a class
 * that packages many different 
 * functions that can help with 
 * squaring inputs etc.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class InputScaler {

    /**
     * Limit input from -1 to 1
     * @param x input
     * @return limited input
     */
    public static double limit(double x) {
        if(x > 1.0)  return 1.0;
        if(x < -1.0) return -1.0;
        return x;
    }

    /**
     * Square number
     * @param x input
     * @return square input
     */
    public static double square(double x) {
        return limit(x * x * Math.signum(x));
    }

    /**
     * Cube number
     * @param x input
     * @return cubed input
     */
    public static double cube(double x) {
        return limit(x * x * x);
    }

    /**
     * Raise input to power and keep sign
     * @param x input
     * @param power power to raise x to
     * @return input ^ power
     */
    public static double pow(double x, double power) {
        return limit(Math.pow(Math.abs(x), power) * Math.signum(x));
    }

    /**
     * Use the shape of a circle with
     * power p to scale the input
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
     * @param x input
     * @return circular return
     */
    public static double circular(double x) {
        return circular(x, 2);
    }
}