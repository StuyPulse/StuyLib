package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;

/**
 * A simple class that lets you combine multiple stream filters into one stream
 * filter
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class IStreamFilterGroup implements IStreamFilter {

    // Array of all the filters
    private IStreamFilter[] mFilters;

    /**
     * Make FilterGroup out of an array of stream filters
     * 
     * @param filters va_list of filters to be combined
     */
    public IStreamFilterGroup(IStreamFilter... filters) {
        mFilters = filters;
    }

    public double get(double next) {
        // Put next through each of the filters
        for (IStreamFilter filter : mFilters) {
            next = filter.get(next);
        }

        // Return filtered value
        return next;
    }
}