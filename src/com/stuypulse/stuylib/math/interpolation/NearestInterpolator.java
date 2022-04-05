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
    // this method will ask a user for a distance and then return a RPM
    // we need a sorting algorithm to order the points in the list from least xAxis to greatest xAxis
    // or we can just tell whoever uses this class to put it in order of increasing distance 
    public double getRPMFromDistance(double distance){
        // this section will find the nearest refernce points to the distance
        int refPoint1 = 0;// the two reference points that the distance is between. return the Vec
        int refPoint2 = 0;

        for (int i = 0; i < points.size(); i ++){ // for each points in the ArrayList
            if (distance < points.get(i).x){// if the distance is less than the x value of the vector2D
                refPoint1 = i; // store the indexes of the points in between the two
                refPoint2 = i - 1;

            }

        }
        
        //After we have the two reference points, we need to intrapolate
        double refPoint1Dist = points.get(refPoint1).x;
        double refPoint2Dist = points.get(refPoint1).x;
   
        double refPoint2RPM = points.get(refPoint2).y;
        double refPoint1RPM = points.get(refPoint1).y;

        new IntervalInterpolator(refPoint1Dist, refPoint2Dist, refPoint1RPM, refPoint2RPM);

        double RPM = interpolate(distance);

        return RPM;

    }

    //TODO: change all of "RPM" and "Distance" into different variables

    // public static void main(String args[]){
    //     ReferencePoints(new Vector2D(0,0), new Vector2D(100, 100));
    // }




    // public static void main(String args[]){
    //     int[] Array = {1, 2, 4, 6};
    //     int target  = 3;
        
    //     for (int i = 0; i < Array.length; i++){
    //         if (target < Array[i]){
    //             System.out.println(Array[i - 1]);
    //             System.out.println(Array[i]);
    //             break;
    //         }
    //     }

    // }

    
    @Override
    public double interpolate(double x) {
        return 0;
    }


}
