package com.stuypulse.stuylib.math.interpolation;

import java.util.ArrayList;
import java.util.Arrays;

import com.stuypulse.stuylib.math.Vector2D;


// we take in an array of reference points
//we calculate each subset of a polynomial 
// the we add them up

// caclcualting numerator: (a - refPoint.x) for all x except the current reference point, a is the distance we want the RPM of
//calculate constant (p, q):  q / (p - refPoint) for all reference point p except the current one


public class PolyInterpolator implements Interpolator {

    private static class Polynomial {
        // private double a;
        private ArrayList<Double> factors;
        private double targetPoint;

        public Polynomial() {
            // a = 0.0; 
            factors = new ArrayList<>();
        }

        // public void setCoefficient(double a) {
        //     this.a = a;
            
        // }

        public void addFactor(Vector2D point) {
            // TODO: add factor to list
            factors.add(point.x);

            
        }

        public double get(double x, Vector2D referencePoint) { // x is target point
            // TODO: evaluate polynomial in form a * (x - factors[0]) * (x - factors[1]) * ... 


            // gets the value of the constant a
            double evaluatePolynomial  = referencePoint.y;
            for(int i = 0; i < factors.size(); i ++ ){
                if(factors.get(i) != targetPoint){
                    evaluatePolynomial /= (referencePoint.x - factors.get(i));
                }
            }

            // getting the numerator: 

            
            for(int i = 0; i < factors.size(); i ++ ){
                if(factors.get(i) != targetPoint){
                    evaluatePolynomial *= (x - factors.get(i));
                }
            }

            return evaluatePolynomial;
        }
    }

    private static double getPolynomial(double targetPoint, Vector2D[] points) { 
        // TODO: Implement polynomial for a point (ADD UP ALL THE GETS )
        Polynomial poly = new Polynomial();
        
        double output = 0;


        for (int i = 0; i < points.length; ++i) {
            poly.addFactor(points[i]);
        }

        for(int i = 1; i < points.length; i ++){
            output += poly.get(targetPoint, points[i]);
        }
        


        return output;
    }

    // ok, now we have a polynomial
    private final Vector2D[] points;
    private final Polynomial[] polynomials;

    public PolyInterpolator(Vector2D... points) {
        if (points.length <= 1){
            throw new IllegalArgumentException("PolyInterpolator needs at least 2 or more points");
        }
        this.points = Interpolator.getSortedPoints(points);
        polynomials = new Polynomial[points.length];
        
        // creates a new polynomial with the given set of reference points. We need to call it later with the reference point x
    }

    

    @Override
    public double interpolate(double x) {
        // TODO: do sum of all functions at x
        double findOutput = PolyInterpolator.getPolynomial(x, points);

        
        return findOutput;
    }

}