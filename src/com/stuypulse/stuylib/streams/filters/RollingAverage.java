package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.exception.ConstructionError;

/**
 * Simple implementation of an Exponential Moving Average
 *
 * This is not time dependant, so the values will change if you change the rate
 * that you call this filter, the filter will not adapt for that.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class RollingAverage extends OnDerivative {

    /**
     * Make an Exponential Moving Average If exp = 1, it will instantly update
     * The weight must be greater than or equal to 1. The higher the weight, the
     * longer it takes to change value
     *
     * @param weight weight (greater than or equal to 1)
     */
    public RollingAverage(double weight) throws ConstructionError {
        super((x) -> x / weight);

        if(weight <= 1.0) {
            throw new ConstructionError("RollingAverage(double weight)",
                    "weight must be greater than 1.0!");
        }
    }
}
