package com.stuypulse.stuylib.math.interpolation;

import com.stuypulse.stuylib.math.Vector2D;

public class QuadraticInterpolator implements Interpolator{
    Vector2D point1;
    Vector2D point2;
    Vector2D point3;
    double a;
    double b;
    double c;

    public QuadraticInterpolator(Vector2D point1, Vector2D point2, Vector2D point3){
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

   public void getEquation(Vector2D point1, Vector2D point2, Vector2D point3){
    // math 
    
    // write 3 equations out of the three points.
    // store the equations in 3 arrays, and we can manipulate those arrays to get new arrays


    // all equations are in the form {coeffient of a, coefficent of b, coeffience of c, equals to (usually y)}
    double equation1[] = {Math.pow(point1.x, 2), point1.x, 1, point1.y };
    double equation2[] = {Math.pow(point2.x, 2), point2.x, 1, point2.y };
    double equation3[] = {Math.pow(point3.x, 2), point3.x, 1, point3.y };
       




   }

   
   
   
   
   
   
    @Override
    public double interpolate(double x) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
