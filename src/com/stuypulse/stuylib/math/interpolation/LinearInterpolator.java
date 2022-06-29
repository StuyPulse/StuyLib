/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math.interpolation;

import com.stuypulse.stuylib.math.Vector2D;

/**
 * Using this class will allow you to linearly interpolate between two surrounding reference 
 * points.
 *
 * @author Eric Lin (ericlin071906@gmail.com, Github: cire694)
 * @author Sam (sam.belliveau@gmail.com)
 */
public class LinearInterpolator implements Interpolator {

    private final Vector2D[] points;

    // Sort the points in ascending order
    public LinearInterpolator(Vector2D... points) {
        if (points.length < 2) {
            throw new IllegalArgumentException("Linear Interpolator needs at least 2 points");
        }

        this.points = Interpolator.getSortedPoints(points);
    }

    @Override
    /**
     * @param x the point of interpolation to get an output
     * @return interpolated value
     */
    public double interpolate(double x) {
        // Find the nearest refernce points to the distance
        Vector2D left = Vector2D.kOrigin; // kOrigin is (0,0)
        Vector2D right = Vector2D.kOrigin;

        // Searching for the points on the left and right of the target point.

        if (x < points[0].x) {
            left = points[0];
            right = points[1];
        } else if (x > points[points.length - 1].x) {
            left = points[points.length - 2];
            right = points[points.length - 1];
        } else {
            for (int i = 1; i < points.length; i++) {
                Vector2D left_temp = points[i - 1];
                Vector2D right_temp = points[i - 0];

                if (left_temp.x <= x && x <= right_temp.x) {
                    left = left_temp;
                    right = right_temp;

                    break;
                }
            }
        }

        return new IntervalInterpolator(left, right).interpolate(x);
    }

    // Tests
    public static void main(String... args) {
        Interpolator test =
                new LinearInterpolator(
                        new Vector2D(1, 6),
                        new Vector2D(6.5, 3),
                        new Vector2D(12, 6),
                        new Vector2D(9, 1));

        for (double i = 1; i < 12; i += 0.5) {
            System.out.println(new Vector2D(i, test.interpolate(i)));
        }
    }
}
