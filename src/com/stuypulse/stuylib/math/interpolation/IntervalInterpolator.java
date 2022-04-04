package com.stuypulse.stuylib.math.interpolation;

/* This class uses 3 sets of points, two of these are references to the shooter, and the third is
the RPM of the shooter. For the third point, all we are giving is this distance, and all we are returning 
is the RPM.

*/

public class IntervalInterpolator implements Interpolator {
    double x1; // for the limelight, this would be the first distance from the hub
    double x2; // this would be the second distance from the hub
    double y1; // for the limelight, this would be the first RPM(of the shooter)
    double y2; // this would be the second RPM(of the shooter)
    
    public IntervalInterpolator(double x1, double x2, double y1, double y2){
        //note: I would use a tuple/list for inputs but I don't know how it works for java 
        this.x1 = x1; 
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public double interpolate(double x){ // gives us the equation for the Extrapolator line
        double range = x2 - x1;
        double slope = (y2 - y1)/range;
        double yIntercept = y1 - slope * x1; // y = mx + b, we now have all compenents of the line

        // get the y value of the line at x3
        double y3 = slope * x + yIntercept;

        return y3;
    }
}
