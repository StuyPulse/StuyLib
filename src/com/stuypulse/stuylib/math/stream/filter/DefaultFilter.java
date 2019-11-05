package com.stuypulse.stuylib.math.stream.filter;

import com.stuypulse.stuylib.math.stream.filter.StreamFilter;

/**
 * This Filter just returns the last value, it can be used 
 * as default values when stream filters are needed
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class DefaultFilter implements StreamFilter {

    // Last value lambda returned
    private double mLastValue;

    /**
     * Makes a DefaultFilter
     */
    public DefaultFilter() {
        mLastValue = 0;
    }

    /**
     * @return last value
     */
    public double get() {
        return mLastValue;
    }

    /**
     * @param next value
     * @return value
     */
    public double get(double next) {
        return mLastValue = next;
    }

}