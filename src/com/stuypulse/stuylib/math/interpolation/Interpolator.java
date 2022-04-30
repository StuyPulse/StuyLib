/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */
package com.stuypulse.stuylib.math.interpolation;

import java.util.Arrays;

import com.stuypulse.stuylib.math.Vector2D;

/**
 * @author Eric (ericlin071906@gmail.com)
 */
public interface Interpolator {
    /**
     * A behavior that takes in a double and returns a double 
     * @param x point to be intepolated
     * @return interpolated value
     */
    double interpolate(double x); 


    /**
     * Sorts the reference point by the value of x from smallest to greatest
     * @param points reference points
     * @return an array of sorted reference points
     */
    public static Vector2D[] getSortedPoints(Vector2D... points) {
        if (points.length < 2) {
            throw new IllegalArgumentException("Interpolation requires at least 2 points");
        }

        Vector2D[] output = points.clone();
        Arrays.sort(points, (lhs, rhs) -> (int)(Math.signum(lhs.x - rhs.x)));

        for(int i = 1; i < output.length; ++i) {
            if(output[i - 1].x == output[i - 0].x) {
                throw new IllegalArgumentException("Interpolation requires all points to have unique x coordinates");
            }
        }

        return output;
    }
}
