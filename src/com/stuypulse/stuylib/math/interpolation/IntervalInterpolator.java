package com.stuypulse.stuylib.math.interpolation;

import com.stuypulse.stuylib.math.Vector2D;

/* This class uses 3 sets of points, two of these are references to the shooter, and the third is
the RPM of the shooter. For the third point, all we are giving is this distance, and all we are returning 
is the RPM.

*/

public class IntervalInterpolator implements Interpolator {
    Vector2D point1;
    Vector2D point2;

    public IntervalInterpolator(Vector2D point1, Vector2D point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public double interpolate(double x) {
        double range = point2.x - point1.x;
        double slope = (point2.y - point1.y) / range;
        double yIntercept = point1.y - (slope * point1.x); // y = mx + b

        double interpolatedValue = slope * x + yIntercept;

        return interpolatedValue;
    }
}
