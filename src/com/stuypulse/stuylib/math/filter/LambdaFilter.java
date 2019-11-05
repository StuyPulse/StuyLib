package com.stuypulse.stuylib.math.filter;

import com.stuypulse.stuylib.math.filter.StreamFilter;

/**
 * This class lets you take a lambda button and turn it 
 * into a filter that can be used with StreamFilter
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class LambdaFilter implements StreamFilter {

    /**
     * Simple class lets us pass lambdas into filter
     */
    public interface Filter {
        public double filter(double val);
    }

    private double mLastValue; // Last value lambda returned
    private Filter mFilter; // Filter for the lambda filter

    /**
     * Makes a Lambda Filter using user supplied lambda
     * @param lambda lambda that takes in double and gives out double
     */
    public LambdaFilter(Filter lambda) {
        mLastValue = 0;
        mFilter = lambda;
    }

    /**
     * @return last value
     */
    public double get() {
        return mLastValue;
    }

    /**
     * @param next value to modify
     * @return modified value
     */
    public double get(double next) {
        return mLastValue = mFilter.filter(next);
    }

}
