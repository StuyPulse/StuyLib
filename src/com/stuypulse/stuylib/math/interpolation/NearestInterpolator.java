/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math.interpolation;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.math.Vector2D;

/**
 * This class is a modified version of Linear Interpolation (Lerp). Using this class will allow you to interpolate
 * between the NEAREST two reference points, as opposed to just interpolating between only two points in Linear Interpolator.
 * @author Eric Lin (ericlin071906@gmail.com, Github: cire694)
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NearestInterpolator implements Interpolator {

    private final Vector2D[] points; 

    // Sort the points in ascending order
    public NearestInterpolator(Vector2D... points) {
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

        if (x <  points[0].x){
            left = points[0];
            right = points[1];
        } else if(x > points[points.length -1].x){
            left = points[points.length -2];
            right = points[points.length -1];
        } else{
            for (int i = 1; i < points.length; i ++){ 
                Vector2D left_temp = points[i - 1];
                Vector2D right_temp = points[i - 0];

                if (left_temp.x <= x && x <= right_temp.x){
                    left = left_temp;
                    right = right_temp;
                    
                    break;
                }
            }
        }

        IntervalInterpolator NearestInterpolator = new IntervalInterpolator(left, right);
        return NearestInterpolator.interpolate(x);
        /**   returns start + (end - start) * clamp(t, 0.0, 1.0)
         * t is x
         * E - S is the slope
         * S is the start, and also b
         * https://www.desmos.com/calculator/zue4m6xgbu */
    }

    
    // Tests
    public static void main(String... args) {
        Interpolator test = new NearestInterpolator(
            new Vector2D(1, 6),
            new Vector2D(6.5, 3),
            new Vector2D(12, 6),
            new Vector2D(9, 1)  
        );

        for(double i = 1; i < 12; i += 0.5) {
            System.out.println(new Vector2D(i, test.interpolate(i)));
        }
    } 
    

}
