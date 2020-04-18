package com.stuypulse.stuylib.streams.filters;

/**
 * A simple class that lets you combine multiple stream filters into one stream
 * filter
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class IFilterGroup implements IFilter {

    // Array of all the filters
    private IFilter[] mFilters;

    /**
     * Make FilterGroup out of an array of stream filters
     *
     * @param filters va_list of filters to be combined
     */
    public IFilterGroup(IFilter... filters) {
        mFilters = filters;
    }

    public double get(double next) {
        // Put next through each of the filters
        for (IFilter filter : mFilters) {
            next = filter.get(next);
        }

        // Return filtered value
        return next;
    }
}
