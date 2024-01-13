package com.stuypulse.stuylib.math.Regression;

import com.stuypulse.stuylib.math.Vector2D;

// sum of x 
public class LinearRegression {

    public Vector2D[] points;

    public LinearRegression(Vector2D... points){
        this.points = points;
    }

    private Vector2D equation(){
        double sumX = 0;
        double sumY = 0;
        double sumXX = 0;
        double sumXY = 0;

        for(Vector2D point : points) {
            sumX += point.x;
            sumY += point.y;
            sumXX += Math.pow(point.x, 2);
            sumXY += point.x * point.y;
            
        }

        double intercept = (sumY * sumXX - sumX * sumXY) / (sumXX - Math.pow(sumX, 2));
        double slope = (points.length * sumXY - sumX * sumY) / (points.length * sumXX - Math.pow(sumX,2));

        return new Vector2D(slope, intercept);
    }

    public void addRefPoint(double x, double y){
        
    }

    public double predictedValue(double x){
        return equation().x * x + equation().y;
    }

}
