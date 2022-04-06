package com.stuypulse.stuylib.math.interpolation;

import java.util.ArrayList;

import com.stuypulse.stuylib.math.Vector2D;

public class NearestInterpolator implements Interpolator {

    private ArrayList<Vector2D> points = new ArrayList<Vector2D>(); // Java equivalent of a list

    public NearestInterpolator(ArrayList<Vector2D> points) {
        this.points = points;
    }

    // this method is used to input the reference point (to interpolate)
    public void ReferencePoints(Vector2D... points) { // this takes in a unknown amount of points
        for (Vector2D point : points) { // for each point in the array
            this.points.add(point); // add the point to the arraylist
            
        }
    }
    /**   this method will ask a user for a distance and then return a RPM
     **   put the points in order of increasing distance or else it's not gonna work 
     **/
    public double getInterpolatedValueFromDistance(double input){
        // this section will find the nearest refernce points to the distance
        int refPoint1 = 0;
        int refPoint2 = 0;

        for (int i = 0; i < points.size(); i ++){ 
            if (input < points.get(i).x){
                refPoint1 = i; 
                refPoint2 = i - 1;

            }

        }
        
        //After we have the two reference points, we need to interpolate
        Interpolator inputInterpolator = new IntervalInterpolator(points.get(refPoint1), points.get(refPoint2));

        double RPM = inputInterpolator.interpolate(input);

        return RPM;

    }

    
    @Override
    public double interpolate(double x) {
        return getInterpolatedValueFromDistance(x);
    }



}
