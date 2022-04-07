package com.stuypulse.stuylib.math.interpolation;
import java.util.Arrays;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.math.Vector2D;

public class NearestInterpolator implements Interpolator {

    private final Vector2D[] points; // Java equivalent of a list

    public NearestInterpolator(Vector2D... points) {
        Arrays.sort(points, (lhs, rhs) -> (int)(Math.signum(lhs.x - rhs.x))); // sorts the points 
        this.points = points;
    }
    
    @Override
    public double interpolate(double x) {
        // this section will find the nearest refernce points to the distance
        Vector2D left = Vector2D.kOrigin; // points are 0, 0
        Vector2D right = Vector2D.kOrigin;

        for (int i = 1; i < points.length; i ++){ 
            Vector2D left_temp = points[i - 1];
            Vector2D right_temp = points[i - 0];

            if (left_temp.x < x && x < right_temp.x){
                left = left_temp;
                right = right_temp;
                
                break;
            }
        }
        
        return SLMath.lerp(left.x, right.x, (x - left.x) / (right.x - left.x));

    }


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
