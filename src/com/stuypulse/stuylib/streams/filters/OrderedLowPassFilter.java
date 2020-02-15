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

public class OrderedLowPassFilter implements IStreamFilter {

    private IStreamFilter mFilter;

    /**
     * Multiple low pass filters combined into one filter
     *
     * @param rc    Time Constant. The time constant is the amount of time in
     *              seconds that it takes to get 63.2% of the way to the target
     *              value. 63.2% is (1 - (1 / e)).
     * @param order number of rolling averages (greater than or equal to 1)
     */
    public OrderedLowPassFilter(double rc, int order) throws ConstructionError {

        IStreamFilter[] filters = new IStreamFilter[order];
        for(int i = 0; i < filters.length; ++i) {
            filters[i] = new LowPassFilter(rc / order);
        }

        mFilter = new IStreamFilterGroup(filters);

        if(rc < 0.0) {
            throw new ConstructionError(
                    "OrderedLowPassFilter(double rc, int order)",
                    "rc must be greater than 0.0!");
        }

        if(order < 1) {
            throw new ConstructionError(
                    "OrderedLowPassFilter(double rc, int order)",
                    "order must be greater than 0!");
        }
    }

    public double get(double next) {
        return mFilter.get(next);
    }
}
