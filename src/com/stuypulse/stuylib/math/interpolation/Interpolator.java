package com.stuypulse.stuylib.math.interpolation;

import java.util.Arrays;

import com.stuypulse.stuylib.math.Vector2D;

public interface Interpolator {
    double interpolate(double x); // a behavior that takes in a double and returns a double

    public static Vector2D[] getSortedPoints(Vector2D... points) {
        if (points.length <= 1) {
            throw new IllegalArgumentException("Interpolation requires at 2 <= points");
        }

        Vector2D[] output = points.clone();
        Arrays.sort(points, (lhs, rhs) -> (int)(Math.signum(lhs.x - rhs.x)));

        for(int i = 1; i < output.length; ++i) {
            if(output[i - 1].x == output[i - 0].x) {
                throw new IllegalArgumentException("Interpolation requires all points to have unique X coordinates!");
            }
        }

        return output;
    }
}
