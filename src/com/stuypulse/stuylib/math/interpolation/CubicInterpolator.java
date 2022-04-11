package com.stuypulse.stuylib.math.interpolation;


import java.util.Arrays;

import com.stuypulse.stuylib.math.Vector2D;

public class CubicInterpolator implements Interpolator{
    
    private static double getTangent(Vector2D a, Vector2D b) {
        return (b.y - a.y) / (b.x - a.x);
    }

    private final int size;
    private final Vector2D[] points;
    private final double[] tangents;

    public CubicInterpolator(Vector2D... points) {
        if (points.length < 4) {
            throw new IllegalArgumentException("CubicInterpolator requires at least 4 points");
        }

        this.size = points.length;
        this.points = Interpolator.getSortedPoints(points);
        this.tangents = new double[size];

        this.tangents[0] = getTangent(this.points[0], this.points[1]);
        this.tangents[size - 1] = getTangent(this.points[size - 2], this.points[size - 1]);

        for (int i = 1; i < size - 1; ++i) {
            this.tangents[i] = getTangent(this.points[i - 1], this.points[i + 1]);
        }
    }

    public double interpolate(double x) {
        // this section will find the nearest refernce points to the distance
        Vector2D left = Vector2D.kOrigin; // points are 0, 0
        Vector2D right = Vector2D.kOrigin;

        double left_tangent = 0;
        double right_tangent = 0;

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

        double t = (x - left.x) / gap;
        double tt = t * t;
        double ttt = tt * t;

        double h00 = 2 * ttt - 3 * tt + 1;
        double h10 = ttt - 2 * tt + t;
        double h01 = -2 * ttt + 3 * tt;
        double h11 = ttt - tt;

        return h00 * left.y + h10 * gap * left_tangent + h01 * right.y + h11 * gap * right_tangent;
    }

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
