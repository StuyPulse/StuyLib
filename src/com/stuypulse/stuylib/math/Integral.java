package com.stuypulse.stuylib.math;

import java.util.ArrayList;

/**
 * Integral is a class that aids in approximating integrals. The Integral object stores points on
 * the graph of which to approximate the integral of. Depending on how it is configured, it can
 * automatically update the answer when fed new points.
 *
 * @author Ivan Wei (ivanw8288@gmail.com)
 */
public class Integral {

    private ArrayList<Vector2D> mDataPoints;
    private Config mConfig;
    private double answer;

    /**
     * Constructs an Integral calculator object with no data points that uses the trapezoidal rule,
     * updates the answer on adding points, but not through recalculating the entire integral
     * approximation.
     */
    public Integral() {
        this(new ArrayList<Vector2D>(), new Config());
    }

    /**
     * Constructs an Integral calculator object with the specified configuration and no data points.
     *
     * @param config the specified configuration for integral approximation
     */
    public Integral(Config config) {
        this(new ArrayList<Vector2D>(), config);
    }

    /**
     * Constructs an Integral calculator object with the specified data points and configuration.
     *
     * @param dataPoints the data points to be initially calculated
     * @param config     the specified configuration for integral approximation
     */
    public Integral(ArrayList<Vector2D> dataPoints, Config config) {
        mDataPoints = new ArrayList<Vector2D>(dataPoints);
        mConfig = config;
        recalculate();
    }

    /**
     * Config is the configuration class for Integral.
     */
    public static class Config {

        private Formula mFormula;
        private boolean mAlwaysUpdate;
        private boolean mAlwaysRecalculate;

        /**
         * Constructs a new Integral approxmation calculator configuration object.
         */
        public Config() {
            mFormula = new Formula.Trapezoidal();
            mAlwaysUpdate = true;
            mAlwaysRecalculate = false;
        }

        /**
         * Sets the formula to be used in calculating the approximation.
         *
         * @param formula the formula to be used in calculating the approximation
         * @return this configuration
         */
        public Config setFormula(Formula formula) {
            mFormula = formula;
            return this;
        }

        /**
         * Sets if the answer to the approximation should be updated every time a point is added. By
         * default, this is true. You might want to turn this off if you wish to add multiple points then
         * recalculate.
         *
         * @param alwaysUpdate if the answer to the approximation should be updated every time a point is
         *                     added
         * @return this configuration
         */
        public Config setAlwaysUpdate(boolean alwaysUpdate) {
            mAlwaysUpdate = alwaysUpdate;
            return this;
        }

        /**
         * Sets if the answer to the approximation should be fully recalculated. By default, this is false.
         * You might want to turn this on if you are using Simpson and do not care too much about the
         * performance loss of complete calculation.
         *
         * @param alwaysRecalculate if the answer to the approximation should be fully recalculated
         * @return this configuration
         */
        public Config setAlwaysRecalculate(boolean alwaysRecalculate) {
            mAlwaysRecalculate = alwaysRecalculate;
            return this;
        }

        /**
         * Returns the formula used to approximate the integral.
         *
         * @return the formula used to approximate the integral.
         */
        public Formula getFormula() {
            return mFormula;
        }

        /**
         * Returns whether the answer to the approximation should be updated every time a point is added.
         *
         * @return whether the answer to the approximation should be updated every time a point is added.
         */
        public boolean getAlwaysUpdate() {
            return mAlwaysUpdate;
        }

        /**
         * Returns if the answer to the approximation should be fully recalculated.
         *
         * @return if the answer to the approximation should be fully recalculated.
         */
        public boolean getAlwaysRecalculate() {
            return mAlwaysRecalculate;
        }
    }

    /**
     * Formula is an interface used by integrate() to get the method used for numerical integration. It
     * also contains several classes containing various formulas for integration.
     *
     * Riemann: Uses the Riemann sum to approximate the integral.
     * (https://en.wikipedia.org/wiki/Riemann_sum)<br>
     * Trapezoidal: Uses the trapezoidal rule to approximate the integral.
     * (https://en.wikipedia.org/wiki/Trapezoidal_rule)<br>
     * Simpson: Uses composite Simpson's Rule for irregularly spaced data to approximate the integral.
     * (https://en.wikipedia.org/wiki/Simpson%27s_rule)<br>
     */
    public interface Formula {

        /**
         * Given data points, gives an approximation for the integral. The x-values must be in sorted order.
         *
         * @param points the points of the graph to approximate the interval over
         * @return approximation of the integral from given points
         */
        public double calculate(ArrayList<Vector2D> points);

        /**
         * The Riemann sum approximates the integral using rectangles. Over each interval [a, b], the
         * rectangle is height f(a) and width b - a. This produces area f(a) * (b - a). It is the least
         * accurate of the provided Formulas. (https://en.wikipedia.org/wiki/Riemann_sum)
         */
        public class Riemann implements Formula {

            public double calculate(ArrayList<Vector2D> points) {
                if(points.size() <= 1) {
                    return 0.0;
                }

                double riemannsum = 0.0;
                for(int i = 1; i < points.size(); ++i) {
                    riemannsum += points.get(i).y
                            * (points.get(i).x - points.get(i - 1).x);
                }
                return riemannsum;
            }
        }

        /**
         * The trapezoidal method approximates the integral using trapezoids. Over each interval [a, b], the
         * trapezoid is height b - a and has bases f(a) and f(b). Therefore, the area of each trapezoid is
         * (f(a) + f(b)) * (b - a) / 2. It is more accurate than Simpson for linear data. Thus, this is the
         * recommended method if you do not plan on recalculating the integral over all the points every
         * time you update, because the best approximation between two points is a line.
         * (https://en.wikipedia.org/wiki/Trapezoidal_rule)
         */
        public class Trapezoidal implements Formula {

            public double calculate(ArrayList<Vector2D> points) {
                if(points.size() <= 1) {
                    return 0.0;
                }

                double trapezoidsum = 0.0;
                for(int i = 1; i < points.size(); ++i) {
                    trapezoidsum += (points.get(i).y + points.get(i - 1).y)
                            * (points.get(i).x - points.get(i - 1).x) / 2;
                }
                return trapezoidsum;
            }
        }

        /**
         * The Simpson's rule for approximating integrals approximates the shape of the graph using
         * quadratics. Refer to the wikipedia article for more information. It is more accurate than
         * trapezoidal for curved data. (https://en.wikipedia.org/wiki/Simpson%27s_rule)
         */
        public class Simpson implements Formula {

            public double calculate(ArrayList<Vector2D> points) {
                if(points.size() <= 1) {
                    return 0.0;
                }

                double simpsonsum = 0.0;

                // Create an array of interval widths
                double[] widths = new double[points.size() - 1];
                for(int i = 0; i < points.size() - 1; ++i) {
                    widths[i] = points.get(i + 1).x - points.get(i).x;
                }

                // Even case for intervals
                int n = widths.length;
                for(int i = 0; i < n / 2; i += 1) {
                    double ai = (2 * SLMath.fpow(widths[2 * i + 1], 3)
                            - SLMath.fpow(widths[2 * i], 3)
                            + 3 * widths[2 * i]
                                    * SLMath.fpow(widths[2 * i + 1], 2))
                            / (6 * widths[2 * i + 1]
                                    * (widths[2 * i + 1] + widths[2 * i]));
                    double bi = (SLMath.fpow(widths[2 * i + 1], 3)
                            + SLMath.fpow(widths[2 * i], 3)
                            + 3 * widths[2 * i + 1] * widths[2 * i]
                                    * (widths[2 * i + 1] + widths[2 * i]))
                            / (6 * widths[2 * i + 1] * widths[2 * i]);
                    double ci = (2 * SLMath.fpow(widths[2 * i], 3)
                            - SLMath.fpow(widths[2 * i + 1], 3)
                            + 3 * widths[2 * i + 1]
                                    * SLMath.fpow(widths[2 * i], 2))
                            / (6 * widths[2 * i]
                                    * (widths[2 * i + 1] + widths[2 * i]));
                    simpsonsum += ai * points.get(2 * i + 2).y
                            + bi * points.get(2 * i + 1).y
                            + ci * points.get(2 * i).y;
                }

                // Odd case for intervals added on end
                if(n % 2 == 1) {
                    double a = (2 * SLMath.fpow(widths[n - 1], 2)
                            + 3 * widths[n - 1] * widths[n - 2])
                            / (6 * (widths[n - 2] + widths[n - 1]));
                    double b = (SLMath.fpow(widths[n - 1], 2)
                            + 3 * widths[n - 1] * widths[n - 2])
                            / (6 * widths[n - 2]);
                    double c = SLMath.fpow(widths[n - 1], 3) / (6
                            * widths[n - 2] * (widths[n - 2] + widths[n - 1]));
                    simpsonsum += a * points.get(n).y + b * points.get(n - 1).y
                            - c * points.get(n - 2).y;
                }
                return simpsonsum;
            }
        }

    }

    /**
     * Uses binary search to find the index at which the point should be inserted for the x-coordinates
     * to be in order. Not in stable order.
     *
     * @param point
     */
    private void binarySearchInsert(Vector2D point) {
        int low = 0, high = mDataPoints.size() - 1, ans = mDataPoints.size();
        while(low <= high) {
            int mid = low + (high - low) / 2;

            if(point.x <= mDataPoints.get(mid).x
                    && (mid < 1 || point.x >= mDataPoints.get(mid - 1).x)) {
                ans = mid;
                break;
            }

            if(point.x > mDataPoints.get(mid).x) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        mDataPoints.add(ans, point);
    }

    /**
     * Adds the point to the array, such that the x-coordinate is in order.
     *
     * @param point the point to be added to the datapoints
     * @return if the point was not inserted in the middle of the
     */
    private boolean addPointOnly(Vector2D point) {
        if(point.x >= mDataPoints.get(mDataPoints.size() - 1).x) {
            mDataPoints.add(point);
            return false;
        } else {
            binarySearchInsert(point);
            return true;
        }
    }

    /**
     * Adds the point to the calculator and updates the answer if alwaysUpdate is on.
     *
     * @param point the point to be added
     */
    public void addPoint(Vector2D point) {
        boolean inMid = addPointOnly(point);
        if(mConfig.getAlwaysUpdate()) {
            if(mConfig.getAlwaysRecalculate() || inMid) {
                recalculate();
            } else {
                calculateLast();
            }
        }
    }

    /**
     * Adds the integral over the last two points to the answer - used for updating without
     * recalculating the entire approximation.
     */
    private void calculateLast() {
        ArrayList<Vector2D> lastTwo = new ArrayList<Vector2D>();
        for(int i = Math.max(0, mDataPoints.size() - 2); i < mDataPoints
                .size(); ++i) {
            lastTwo.add(mDataPoints.get(i));
        }
        answer += mConfig.getFormula().calculate(lastTwo);
    }

    /**
     * Recalculates the entire integral approximation.
     */
    public void recalculate() {
        answer = mConfig.getFormula().calculate(mDataPoints);
    }

    /**
     * Get the answer to the approximation of the integral. If alwaysUpdate is off, it could be very
     * inaccurate.
     *
     * @return the answer to the approximation of the integral.
     */
    public double getAnswer() {
        return answer;
    }

    /**
     * Modifies the configuration for this Integral calculator.
     *
     * @param config the new config
     */
    public void setConfig(Config config) {
        mConfig = config;
    }

    /**
     * Returns the configuration for this Integral calculator.
     *
     * @return the configuration for this Integral calculator
     */
    public Config getConfig() {
        return mConfig;
    }

    /**
     * Clears the graph data points and resets the answer to 0.
     */
    public void reset() {
        mDataPoints = new ArrayList<Vector2D>();
        answer = 0.0;
    }
}
