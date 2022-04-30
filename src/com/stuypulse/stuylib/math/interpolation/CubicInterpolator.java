/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */
package com.stuypulse.stuylib.math.interpolation;

import com.stuypulse.stuylib.math.Vector2D;

/**
 * This class uses Cubic Hermite interpolation (to find the RPM of the shooter). 
 * This class gets a polynomial that represents the model. We then use the polynomial to interpolate. 
 * 
 * The polynomial can be written as
 *      P(t) = h00(t)p0 + h10(t)m0 + h01(t)p1 + h11(t)m1
 * 
 * in which 
 *       P(t) is the polynomial
 *       p0 and p1 are the y coordinates of the reference points 
 *       h00(t) = 2(t * t * t) - 3(t * t) + 1 
 *       h10(t) = (t* t * t) - 2(t * t) + t
 *       h01(t) = -2(t * t * t) + 3(t * t)
 *       h11(t) = (t * t * t) - (t * t)
 *       m0, m1 is the slope (derivative) of the points
 *       t = time (arbitrary point in interval)
 * 
 * It can be thought of as interpolating between the derivatives.
 *          https://www.desmos.com/calculator/wcjns2ayab
 * Note: the two points surrounding a point is parrellel to the tangent. To get the slope of a point, we look at the points around it. 
 * 
 * more information can be found here:
 *          https://en.wikipedia.org/wiki/Cubic_Hermite_spline
 * 
 * @author Sam  (sam.belliveau@gmail.com)
 * @author Eric (ericlin071906@gmail.com)
 */
public class CubicInterpolator implements Interpolator{
    
    /**
     * Get the tangents of the two reference points surrounding the point to be interpolated
     * @param left the left point
     * @param right the right point
     * @return the slope/tangent (note that the slope and tangents are parallel
     */
    private static double getTangent(Vector2D left, Vector2D right) {
        return (right.y - left.y) / (right.x - left.x);
    }

    private final int size;
    private final Vector2D[] points;
    private final double[] tangents;

    /**
     * This constructor creates a new cubic hermite spline interpolator. It takes in at least 4 reference points
     * it gets the tangent of every point by the two points around it and stores it in a array
     * @param points the reference points we interpolate from. 
     */
    public CubicInterpolator(Vector2D... points) {
        if (points.length < 4) {
            throw new IllegalArgumentException("CubicInterpolator requires at least 4 points");
        }

        this.size = points.length;
        this.points = Interpolator.getSortedPoints(points);
        this.tangents = new double[size];
        
        // gets the tangent (m0 and m1) 
        // note that the tangent of the points are parrallel to the slope of the points surrounding it 
        this.tangents[0] = getTangent(this.points[0], this.points[1]);
        this.tangents[size - 1] = getTangent(this.points[size - 2], this.points[size - 1]);

        for (int i = 1; i < size - 1; ++i) {
            this.tangents[i] = getTangent(this.points[i - 1], this.points[i + 1]);
        }
    }

    public double interpolate(double x) {
        // Find the nearest reference points to the distance
        Vector2D left = Vector2D.kOrigin; // kOrigin is (0,0)
        Vector2D right = Vector2D.kOrigin;

        double left_tangent = 0;
        double right_tangent = 0;

        // Solve for the tangents of the left and right points that surround the target point
        for (int i = 1; i < points.length; i ++){ 
            Vector2D left_temp = points[i - 1];
            Vector2D right_temp = points[i - 0];

            if (left_temp.x <= x && x <= right_temp.x){
                left = left_temp;
                right = right_temp;

                left_tangent = tangents[i - 1];
                right_tangent = tangents[i - 0];
                
                break;
            }
        }

        double gap = (right.x - left.x);

        // Apply the formula
        double t = (x - left.x) / gap;
        double tt = t * t;
        double ttt = tt * t;

        double h00 = 2 * ttt - 3 * tt + 1;
        double h10 = ttt - 2 * tt + t;
        double h01 = -2 * ttt + 3 * tt;
        double h11 = ttt - tt;

        return h00 * left.y + h10 * gap * left_tangent + h01 * right.y + h11 * gap * right_tangent;
    }

    // Tests
    public static void main(String... args) {
        Interpolator test = new CubicInterpolator(
            new Vector2D(1, 1),
            new Vector2D(3, 2),
            new Vector2D(8, 5),
            new Vector2D(10, 2)  
        );

        for(double i = 3; i < 8; i += 0.5) {
            System.out.println(new Vector2D(i, test.interpolate(i)));
        }
    }



}
