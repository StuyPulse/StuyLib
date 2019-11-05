package com.stuypulse.stuylib.math.stream.filter;

import com.stuypulse.stuylib.math.stream.filter.StreamFilter;

/**
 * A simple class that lets you combine multiple 
 * stream filters into one stream filter
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class StreamFilterGroup implements StreamFilter {

    private double mLastValue; // Last Value of the Filters
    private Iterable<StreamFilter> mFilters; // Array of all the filters

    /**
     * Make FilterCombo out of an 
     * array of stream filters
     * @param filters array of filters to be combined
     */
    public StreamFilterGroup(Iterable<StreamFilter> filters) {
        mLastValue = 0;
        mFilters = filters;
    }

    /**
     * Return current value of last filter
     * @return current value of last filter
     */
    public double get() {
        return mLastValue;
    }

    /**
     * Filter value through all filters
     * @param next next value in stream
     * @return filtered value
     */
    public double get(double next) {
        // Put mLastValue through each of the filters
        mLastValue = next;
        for(StreamFilter filter : mFilters) {
            mLastValue = filter.get(mLastValue);
        }

        // Return filtered value
        return mLastValue;
    }
}