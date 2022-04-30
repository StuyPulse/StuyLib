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

    // sorts the points in ascending order
    public NearestInterpolator(Vector2D... points) {
        this.points = Interpolator.getSortedPoints(points); 
    }
    
    @Override
    /**
     * @param x the point of extrapolation to get an output
     * @return interpolated value
     */
    public double interpolate(double x) {
        // this section will find the nearest refernce points to the distance
        Vector2D left = Vector2D.kOrigin; // points are 0, 0 for kOrigin
        Vector2D right = Vector2D.kOrigin; 

        // searching for the points on the left and right of the target point. 
        for (int i = 1; i < points.length; i ++){ 
            Vector2D left_temp = points[i - 1];
            Vector2D right_temp = points[i - 0];

            if (left_temp.x <= x && x <= right_temp.x){
                left = left_temp;
                right = right_temp;
                
                break;
            }

            if (x < points[0].x){ // if we're interpolating outside the data set
                left = points[0]; // set left and right to be the outer edge
                right = points[1];
                IntervalInterpolator slope = new IntervalInterpolator(left, right);
                return slope.interpolate(x);
                
            }
            
            // if(x > points[points.length].x){
            //     left = points[points.length - 1];
            //     right = points[points.length];
            //     break;
            // }
        }
        
        return SLMath.lerp(left.y, right.y, (x - left.x) / (right.x - left.x));
        /**   returns start + (end - start) * clamp(t, 0.0, 1.0)
         * t is x
         * E - S is the slope
         * S is the start, and also b
         * https://www.desmos.com/calculator/zue4m6xgbu */
    }

    
    //for testing
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
