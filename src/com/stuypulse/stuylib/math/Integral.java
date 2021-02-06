package com.stuypulse.stuylib.math;

import java.util.ArrayList;

/**
 * Integral is a class that aids in calculating integrals, 
 */
public class Integral {
    
    private ArrayList<Vector2D> mDataPoints;
    private Formula mFormula;

    public Integral(ArrayList<Vector2D> dataPoints, Formula formula) {
        mDataPoints = new ArrayList<Vector2D>(dataPoints);
        mFormula = formula;
    }

    public Integral(ArrayList<Vector2D> dataPoints) {
        this(dataPoints, new Formula.Simpson());
    }

    public Integral(Formula formula) {
        this(new ArrayList<Vector2D>(), formula);
    }

    public Integral() {
        this(new ArrayList<Vector2D>(), new Formula.Simpson());
    }

    /**
     * Formula is an interface used by integrate() to get the method used for numerical integration.  
     * It also contains several classes containing various formulas for integration.
     * 
     * Riemann: Uses the Riemann sum to approximate the integral. (https://en.wikipedia.org/wiki/Riemann_sum)<br>
     * Trapezoidal: Uses the trapezoidal rule to approximate the integral. (https://en.wikipedia.org/wiki/Trapezoidal_rule)<br>
     * Simpson: Uses composite Simpson's Rule for irregularly spaced data to approximate the integral. (https://en.wikipedia.org/wiki/Simpson%27s_rule)<br>
     */
    public interface Formula {
        /**
         * Given data points, gives an approximation for the integral.
         * The x-values must be in sorted order.
         * 
         * @param points the points of the graph to approximate the interval over
         * @return approximation of the integral from given points
         */
        public double calculate(ArrayList<Vector2D> points);

        public class Riemann implements Formula {
            public double calculate(ArrayList<Vector2D> points) {
                double riemannsum = 0.0;
                for(int i = 1; i < points.size(); ++i) {
                    riemannsum += points.get(i).y * (points.get(i).x - points.get(i - 1).x);
                }
                return riemannsum;
            }
        }

        public class Trapezoidal implements Formula {
            public double calculate(ArrayList<Vector2D> points) {
                double trapezoidsum = 0.0;
                for(int i = 1; i < points.size(); ++i) {
                    trapezoidsum += (points.get(i).y + points.get(i - 1).y) * (points.get(i).x - points.get(i - 1).x) / 2;
                }
                return trapezoidsum;
            }
        }

        public class Simpson implements Formula {
            public double calculate(ArrayList<Vector2D> points) {
                double simpsonsum = 0.0;

                // Create an array of interval widths
                double[] widths = new double[points.size() - 1];
                for(int i = 0; i < points.size() - 1; ++i) {
                    widths[i] = points.get(i + 1).x - points.get(i).x;
                }

                // Even case for intervals
                int n = widths.length;
                for(int i = 0; i < n / 2; i += 1) {
                    double ai = (2 * SLMath.fpow(widths[2 * i + 1], 3) - SLMath.fpow(widths[2 * i], 3) + 3 * widths[2 * i] * SLMath.fpow(widths[2 * i + 1], 2)) / 
                                (6 * widths[2 * i + 1] * (widths[2 * i + 1] + widths[2 * i]));
                    double bi = (SLMath.fpow(widths[2 * i + 1], 3) + SLMath.fpow(widths[2 * i], 3) + 3 * widths[2 * i + 1] * widths[2 * i] * (widths[2 * i + 1] + widths[2 * i])) /
                                (6 * widths[2 * i + 1] * widths[2 * i]);
                    double ci = (2 * SLMath.fpow(widths[2 * i], 3) - SLMath.fpow(widths[2 * i + 1], 3) + 3 * widths[2 * i + 1] * SLMath.fpow(widths[2 * i], 2)) /
                                (6 * widths[2 * i] * (widths[2 * i + 1] + widths[2 * i]));
                    simpsonsum += ai * points.get(2 * i + 2).y + bi * points.get(2 * i + 1).y + ci * points.get(2 * i).y; 
                }

                // Odd case for intervals added on end
                if(n % 2 == 1) {
                    double a = (2 * SLMath.fpow(widths[n - 1], 2) + 3 * widths[n - 1] * widths[n - 2]) /
                               (6 * (widths[n - 2] + widths[n - 1]));
                    double b = (SLMath.fpow(widths[n - 1], 2) + 3 * widths[n - 1] * widths[n - 2]) / 
                               (6 * widths[n - 2]);
                    double c = SLMath.fpow(widths[n - 1], 3) /
                               (6 * widths[n - 2] * (widths[n - 2] + widths[n - 1]));
                    simpsonsum += a * points.get(n).y + b * points.get(n - 1).y - c * points.get(n - 2).y;
                }
                return simpsonsum;
            }
        }

    }

    public void addPoint(Vector2D point) {
        
    }

    public void recalculate() {

    }
}
