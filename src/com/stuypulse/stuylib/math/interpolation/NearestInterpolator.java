package com.stuypulse.stuylib.math.interpolation;

import java.util.ArrayList;

import com.stuypulse.stuylib.math.Vector2D;

public class NearestInterpolator implements Interpolator {

    private ArrayList<Vector2D> points = new ArrayList<Vector2D>(); // Java equivalent of a list

    public NearestInterpolator(ArrayList<Vector2D> points) {
        this.points = points;
    }

    public NearestInterpolator(Vector2D... points) { // this takes in a unknown amount of points
        for (Vector2D point : points) { // for each point in the array
            this.points.add(point); // add the point to the arraylist
            // points.collections.sort();
        }
    }

    private double addNewVector(Vector2D x){ // vector 
        // double nearest = 0;
        // for (int i = 0; i < points.size(); i ++){
        //     if (x < points.get(i).x){ // if x is less than the value in the list
        //         lowerLimit = i;
        //         upperLimit = i - 1;
        //     }
        // }
        points.add(x)


    }

    public static void main(String args[]){
        int[] Array = {1, 2, 4, 6};
        int target  = 3;
        
        for (int i = 0; i < Array.length; i++){
            if (target < Array[i]){
                System.out.println(Array[i - 1]);
                System.out.println(Array[i]);
                break;
            }
        }

    }
    
    @Override
    public double interpolate(double x) {
        return 0;
    }


}
