package com.stuypulse.stuylib.math.streams.filters;

import com.stuypulse.stuylib.math.streams.filters.IStreamFilter;

/**
 * A simple class that lets you combine multiple 
 * stream filters into one stream filter
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class IStreamFilterGroup implements IStreamFilter {

    // Array of all the filters
    private Iterable<IStreamFilter> mFilters; 

    /**
     * Make FilterCombo out of an 
     * array of stream filters
     * @param filters array of filters to be combined
     */
    public IStreamFilterGroup(Iterable<IStreamFilter> filters) {
        mFilters = filters;
    }

    /**
     * Filter value through all filters
     * @param next next value in stream
     * @return filtered value
     */
    public double get(double next) {
        // Put mLastValue through each of the filters
        double value = next;
        for(IStreamFilter filter : mFilters) {
            value = filter.get(value);
        }

        // Return filtered value
        return value;
    }
}