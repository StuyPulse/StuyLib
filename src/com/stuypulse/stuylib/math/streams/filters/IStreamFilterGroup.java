package com.stuypulse.stuylib.math.streams.filters;

import com.stuypulse.stuylib.math.streams.filters.IStreamFilter;

import java.util.ArrayList;

/**
 * A simple class that lets you combine multiple 
 * stream filters into one stream filter
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class IStreamFilterGroup implements IStreamFilter {

    // Array of all the filters
    private ArrayList<IStreamFilter> mFilters; 

    /**
     * Make FilterGroup out of an 
     * array of stream filters
     * @param filters va_list of filters to be combined
     */
    public IStreamFilterGroup(IStreamFilter... filters) {
        mFilters = new ArrayList<>();

        for(IStreamFilter filter : filters) {
            mFilters.add(filter);
        }
    }

    /**
     * Filter value through all filters
     * @param next next value in stream
     * @return filtered value
     */
    public double get(double next) {
        // Put mLastValue through each of the filters
        for(IStreamFilter filter : mFilters) {
            next = filter.get(next);
        }

        // Return filtered value
        return next;
    }
}