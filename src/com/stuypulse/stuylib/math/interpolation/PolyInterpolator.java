package com.stuypulse.stuylib.math.interpolation;

import java.util.ArrayList;

import com.stuypulse.stuylib.math.Vector2D;

// this class uses Lagrange interpolation polymial method. It interpolators any n number of points
// @author Eric Lin (ericlin071906@gmail.com)
// @author Myles Pasetsky (selym3 on github)


public class PolyInterpolator implements Interpolator {


    private static class Polynomial {
        private double a;
        private ArrayList<Double> factors;

        public Polynomial() {
            a = 0.0; 
            factors = new ArrayList<>();
        }

        public void setCoefficient(double a) { // sets Coefficent of a
            this.a = a;
        }

        public void addZero(double zero) {
            // add factor to the list of factors
            factors.add(zero);
        }

        public double get(double x) { // x is target point
            //evaluates polynomial in form a * (x - factors[0]) * (x - factors[1]) * ... 
            double output = a;
            for (double factor : factors) {
                output *= (x - factor);
            }
            return output; 
        }
    }
    

    // Lagrange polynomials is in the form F(x) = p(x) + q(x) + r(x) ... 
    // each of the terms of the sum is called a PartialPolynomial
    // this method finds the value of each partial polynomial 
    private static Polynomial getPartialPolynomial(Vector2D target, Vector2D[] points) { 
        

        Polynomial polynomial =  new Polynomial();
        double a = target.y;

        for (Vector2D point : points){
            if(point != target){
                a /= target.x - point.x; 
                polynomial.addZero(point.x);
            }
            
        }
        polynomial.setCoefficient(a);
        return polynomial;
       
    }

  
    private final Polynomial[] partialPolynomials; // storing the partial polynomials 

    // gets the value of the partial polynomials
    public PolyInterpolator(Vector2D... points) {
        if (points.length <= 1){
            throw new IllegalArgumentException("PolyInterpolator needs at least 2 or more points");
        }

        partialPolynomials = new Polynomial[points.length];
        
        // returns a and factors for partial polynomials
        for (int i = 0; i < points.length; i ++){
            partialPolynomials[i] = getPartialPolynomial(points[i], points);
        }

    
    }

    

    @Override
    public double interpolate(double x) {
        //sum of all partial function at x

        double fullPolynomial = 0;
        for (int i = 0; i < partialPolynomials.length; i ++){
            fullPolynomial += partialPolynomials[i].get(x);
        }
        return fullPolynomial;
    }

}