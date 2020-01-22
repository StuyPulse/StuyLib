package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.OnDerivative;
import com.stuypulse.stuylib.exception.ConstructionError;

/**
 * Simple implementation of an Exponential Moving Average
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class RollingAverage extends OnDerivative {
    
    /**
     * Make an Exponential Moving Average If exp = 1, it will instantly update The
     * weight must be greater than or equal to 1. The higher the weight, the longer
     * it takes to change value
     * 
     * @param weight weight (greater than or equal to 1)
     */
    public RollingAverage(double weight) throws ConstructionError {
        super((x) -> x / weight);

        if (weight <= 0.5) {
            throw new ConstructionError("RollingAverage(double weight)", "weight must be greater than 0.5!");
        }
    }
    
    /**
     * Make an Exponential Moving Average If exp = 1, it will instantly update The
     * weight must be greater than or equal to 1. The higher the weight, the longer
     * it takes to change value
     * 
     * @param accel_weight weight when accelerating
     * @param accel_weight weight when decelerating
     */
    public RollingAverage(double accel_weight, double decel_weight) throws ConstructionError {
        super((x) -> (x < 0) ? (x / decel_weight) : (x / accel_weight));

        if (accel_weight <= 0.5) {
            throw new ConstructionError("RollingAverage(double accel_weight, double decel_weight)", "accel_weight must be greater than 0.5!");
        }

        if (decel_weight <= 0.5) {
            throw new ConstructionError("RollingAverage(double accel_weight, double decel_weight)", "decel_weight must be greater than 0.5!");
        }
    }
}