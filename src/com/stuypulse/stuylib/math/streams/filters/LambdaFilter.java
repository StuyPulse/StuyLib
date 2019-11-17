package com.stuypulse.stuylib.math.streams.filters;

import com.stuypulse.stuylib.math.streams.filters.IStreamFilter;

/**
 * This class lets you take a lambda button and turn it 
 * into a filter that can be used with StreamFilter
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class LambdaFilter implements IStreamFilter {

    /**
     * Simple class lets us pass lambdas into filter
     */
    public interface Filter {
        public double filter(double val);
    }

    // Filter for the lambda filter
    private Filter mFilter; 

    /**
     * Makes a Lambda Filter using user supplied lambda
     * @param lambda lambda that takes in double and gives out double
     */
    public LambdaFilter(Filter lambda) {
        mFilter = lambda;
    }

    /**
     * @param next value to modify
     * @return modified value
     */
    public double get(double next) {
        return mFilter.filter(next);
    }
}