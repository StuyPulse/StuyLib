package com.stuypulse.stuylib.math.filter;

import com.stuypulse.stuylib.math.filter.StreamFilter;

/**
 * A simple class that lets you combine multiple 
 * stream filters into one stream filter
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class StreamFilterGroup implements StreamFilter {

    // Array of all the filters
    private StreamFilter[] mFilters;

    /**
     * Make FilterCombo out of an 
     * array of stream filters
     * @param filters array of filters to be combined
     */
    public StreamFilterGroup(StreamFilter[] filters) {
        mFilters = filters.clone();
    }

    /**
     * Return current value of last filter
     * @return current value of last filter
     */
    public double get() {
        return mFilters[mFilters.length - 1].get();
    }

    /**
     * Filter value through all filters
     * @param next next value in stream
     * @return filtered value
     */
    public double get(double next) {
        double val = next;
        for(int i = 0; i < mFilters.length; ++i) {
            val = mFilters[i].get(val);
        }
        return val;
    }
}