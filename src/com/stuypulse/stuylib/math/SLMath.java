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
    private SLMath() {
    }

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
        if(x > max)
            return max;
        if(x < min)
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
    /*** DEADBAND ALGORITHM ***/
    /**************************/

    /**
     * Dead bands x value with window being the dead band. all values for this are [-1.0...1.0]
     *
     * @param x      value
     * @param window deadband window
     * @return deadbanded value
     */
    public static double deadband(double x, double window) {
        window = Math.abs(window);

        if(Math.abs(x) < window) {
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
     * spow (signless pow), raises a number to a power without affecting the sign of the number
     *
     * @param x     input
     * @param power power to raise x to
     * @return input ^ power
     */
    public static double spow(double x, double power) {
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

    /*******************************/
    /*** INTEGRAL APPROXIMATIONS ***/
    /*******************************/

    /**
     * Given data points, calculates integral using the chosen formula.<br>
     * 
     * Arrays x and y must be the same length, because they contain data points.
     * The x-values must be in sorted order.
     * 
     * @param x       the x-values of the data points, where (x[i], y[i]) is a point
     * @param y       the y-values of the data points, where (x[i], y[i]) is a point
     * @param formula the chosen formula to approximate the integral.
     * @return approximation of the integral from given points
     */
    public static double integrate(double[] x, double[] y, Integral formula) {
        // Precondition: there always exists x[i], y[i]
        if(x.length != y.length) {
            throw new IllegalArgumentException("x and y-value arrays should be the same length");
        }

        // Precondition: x-values should be sorted
        for(int i = 1; i < x.length; ++i) {
            if(x[i] < x[i - 1]) {
                throw new IllegalArgumentException("x-values should be in sorted order");
            }
        }

        return formula.calculate(x, y);
    }

    public static final Integral DEFAULT_INTEGRAL = new Integral.Simpson();
    /**
     * Calls integrate(double[] x, double[] y, Integral formula) with default formula Simpson.
     * 
     * @param x the x-values of the data points, where (x[i], y[i]) is a point
     * @param y the y-values of the data points, where (x[i], y[i]) is a point
     * @return approximation of th eintegral from the given points using the Simpson formula.
     */
    public static double integrate(double[] x, double[] y) {
        return integrate(x, y, DEFAULT_INTEGRAL);
    }

    /**
     * Integral is an interface used by integrate() to get the method used for numerical integration.  
     * It also contains several classes containing various formulas for integration.
     * 
     * Riemann: Uses the Riemann sum to approximate the integral. (https://en.wikipedia.org/wiki/Riemann_sum)<br>
     * Trapezoidal: Uses the trapezoidal rule to approximate the integral. (https://en.wikipedia.org/wiki/Trapezoidal_rule)<br>
     * Simpson: Uses Composite Simpson's Rule for irregularly spaced data to approximate the integral. (https://en.wikipedia.org/wiki/Simpson%27s_rule)<br>
     */
    public interface Integral {
        /**
         * Given data points, gives an approximation for the integral.
         * Arrays x and y must be the same length, because they contain data points.
         * The x-values must be in sorted order.
         * 
         * @param x the x-values of the data points, where (x[i], y[i]) is a point
         * @param y the y-values of the data points, where (x[i], y[i]) is a point
         * @return approximation of the integral from given points
         */
        public double calculate(double[] x, double[] y);

        public class Riemann implements Integral {
            public double calculate(double[] x, double[] y) {
                double riemannsum = 0.0;
                for(int i = 1; i < x.length; ++i) {
                    riemannsum += y[i] * (x[i] - x[i - 1]);
                }
                return riemannsum;
            }
        }

        public class Trapezoidal implements Integral {
            public double calculate(double[] x, double[] y) {
                double trapezoidsum = 0.0;
                for(int i = 1; i < x.length; ++i) {
                    trapezoidsum += (y[i] + y[i - 1]) * (x[i] - x[i - 1]) / 2;
                }
                return trapezoidsum;
            }
        }

        public class Simpson implements Integral {
            public double calculate(double[] x, double[] y) {
                double simpsonsum = 0.0;

                // Create an array of interval widths
                double[] widths = new double[x.length - 1];
                for(int i = 0; i < x.length - 1; ++i) {
                    widths[i] = x[i + 1] - x[i];
                }

                // Even case for intervals
                int n = widths.length;
                for(int i = 0; i < n / 2; i += 1) {
                    double ai = (2 * Math.pow(widths[2 * i + 1], 3) - Math.pow(widths[2 * i], 3) + 3 * widths[2 * i] * Math.pow(widths[2 * i + 1], 2)) / 
                                (6 * widths[2 * i + 1] * (widths[2 * i + 1] + widths[2 * i]));
                    double bi = (Math.pow(widths[2 * i + 1], 3) + Math.pow(widths[2 * i], 3) + 3 * widths[2 * i + 1] * widths[2 * i] * (widths[2 * i + 1] + widths[2 * i])) /
                                (6 * widths[2 * i + 1] * widths[2 * i]);
                    double ci = (2 * Math.pow(widths[2 * i], 3) - Math.pow(widths[2 * i + 1], 3) + 3 * widths[2 * i + 1] * Math.pow(widths[2 * i], 2)) /
                                (6 * widths[2 * i] * (widths[2 * i + 1] + widths[2 * i]));
                    simpsonsum += ai * y[2 * i + 2] + bi * y[2 * i + 1] + ci * y[2 * i]; 
                }

                // Odd case for intervals added on end
                if(n % 2 == 1) {
                    double a = (2 * Math.pow(widths[n - 1], 2) + 3 * widths[n - 1] * widths[n - 2]) /
                               (6 * (widths[n - 2] + widths[n - 1]));
                    double b = (Math.pow(widths[n - 1], 2) + 3 * widths[n - 1] * widths[n - 2]) / 
                               (6 * widths[n - 2]);
                    double c = Math.pow(widths[n - 1], 3) /
                               (6 * widths[n - 2] * (widths[n - 2] + widths[n - 1]));
                    simpsonsum += a * y[n] + b * y[n - 1] - c * y[n - 2];
                }
                return simpsonsum;
            }
        }


    }

    /*****************/
    /*** MISC MATH ***/
    /*****************/

    /**
     * fpow (fast pow), is a pow function that takes in an integer for the exponent. This allows it to
     * be much faster on repeated calls due to the fact that it does not need to deal with fractional
     * exponents.
     *
     * @param base base of the power
     * @param exp  integer exponent of power
     * @return result of calculation
     */
    public static double fpow(double base, int exp) {
        // Output of the fpow function
        double out = 1.0;

        // If the exponent is negative, divide instead of multiply
        if(exp < 0) {
            // Flip exponent to make calculations easier
            exp = -exp;

            // Fast integer power algorithm
            while(exp > 0) {
                if((exp & 1) == 1) {
                    out /= base;
                }
                base *= base;
                exp >>= 1;
            }
        } else {
            // Fast integer power algorithm
            while(exp > 0) {
                if((exp & 1) == 1) {
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
     * @param n       number to round
     * @param sigfigs amount of sigfigs to round it to
     * @return rounded number
     */
    public static double round(double n, int sigfigs) {
        // The value 0 returns nan if not accounted for
        if(n == 0.0) {
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
