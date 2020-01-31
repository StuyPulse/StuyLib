package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.streams.filters.IStreamFilterGroup;
import com.stuypulse.stuylib.streams.filters.RollingAverage;
import com.stuypulse.stuylib.exception.ConstructionError;

/**
 * Simple implementation of an Exponential Moving Average
 * 
 * This is not time dependant, so the values will change if you change the rate
 * that you call this filter, the filter will not adapt for that.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class OrderedRollingAverage implements IStreamFilter {
    
    private IStreamFilter mFilter;

    /**
     * Multiple Rolling Average's combined into one filter
     * 
     * @param weight weight of rolling averages (greater than or equal to 1.0)
     * @param order number of rolling averages (greater than or equal to 1)
     */
    public OrderedRollingAverage(double weight, int order) throws ConstructionError {

        IStreamFilter[] filters = new IStreamFilter[order];
        for(int i = 0; i < filters.length; ++i) {
            filters[i] = new RollingAverage(weight);
        }

        mFilter = new IStreamFilterGroup(filters);

        if (weight < 1.0) {
            throw new ConstructionError("OrderedRollingAverage(double weight, int order)", "weight must be greater than 1.0!");
        }

        if (order < 1) {
            throw new ConstructionError("OrderedRollingAverage(double weight, int order)", "order must be greater than 0!");
        }
    }

    public double get(double next) {
        return mFilter.get(next);
    }
}