package stuylib.util;

/** 
 * The input scalar class is a class
 * that packages many different 
 * functions that can help with 
 * squaring inputs etc.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class InputScale {

    public static double limit(double x) {
        return Math.max(Math.min(x, 1.0), -1.0);
    }

    public static double square(double x) {
        return limit(x * x * Math.signum(x));
    }

    public static double cube(double x) {
        return limit(x * x * x);
    }

    public static double pow(double x, double power) {
        return limit(Math.pow(Math.abs(x), power) * Math.signum(x));
    }

    /**
     * As apposed to squaring and cubing inputs
     * to get a finer control over smaller values,
     * I prepose this circular algorithm. 
     * https://www.desmos.com/calculator/msqknwjdk7
     * /\ Shows how it gives a nicer, more rounded
     * curve which still speeds up, but also gives
     * the ability for fine control
     */
    public static double circular(double x) {
        double sign = Math.signum(x);
        x = Math.sqrt(1.0 - square(x));
        x = 1.0 - x;
        x *= sign;
        return limit(x);
    }
}