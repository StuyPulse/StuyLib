package com.stuypulse.stuylib.math.interpolation;

import com.stuypulse.stuylib.math.Vector2D;

public class InterpolatorMath {
    public static double[] getOriginalEquation(Vector2D point, int power){
        double[] returnEquation = new double[power + 2];
        for (int i = 0; i < power - 1; i ++){
            returnEquation[i] = Math.pow(point.x, power + 1 - i);
        }

        return returnEquation;


    }


    public static double getScaleFactor(double[] array1, double[] array2){ //what you should multiply to array1
        double scaleFactor = array2[0] / array1[0];
        return scaleFactor;
    }

    public static double[] applyScaleFactor(double[] array, double scaleFactor) {
        for (int i = 0; i < array.length; i ++){
            array[i] = array[i] * scaleFactor;
        }
        return array;
    }
    
    public static double[] cancelAVariable(double[] array1, double[] array2){//array1 and array2 should be the same length
        double oneLessVariableEquation[] = new double[array1.length];
       
        for (int i = 0; i < array1.length; i ++){   // theoretically newEquation1, newEquation2, and equation1,2,3 should have the same length
            double newValues = array1[i] - array2[i];
            oneLessVariableEquation[i] = newValues;
        }

        return oneLessVariableEquation;

    }

    public static double[] worklessCancel(double[]array1, double[] array2){
        double scaleFactor = getScaleFactor(array1, array2);
        double[] newArray = applyScaleFactor(array1, scaleFactor);
        double[] oneLessVariableEquation = cancelAVariable(newArray, array2);

        return oneLessVariableEquation;

    }

    
}
