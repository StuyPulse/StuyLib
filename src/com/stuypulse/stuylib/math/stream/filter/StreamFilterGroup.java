package com.stuypulse.stuylib.math.stream.filter;

import com.stuypulse.stuylib.math.stream.filter.StreamFilter;

/**
 * A simple class that lets you combine multiple 
 * stream filters into one stream filter
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class StreamFilterGroup implements StreamFilter {

    // Array of all the filters
    private Iterable<StreamFilter> mFilters; 

    /**
     * Make FilterCombo out of an 
     * array of stream filters
     * @param filters array of filters to be combined
     */
    public StreamFilterGroup(Iterable<StreamFilter> filters) {
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
        for(StreamFilter filter : mFilters) {
            value = filter.get(value);
        }

        // Return filtered value
        return value;
    }
}