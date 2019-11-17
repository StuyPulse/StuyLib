package com.stuypulse.stuylib.math.streams.filters;

import com.stuypulse.stuylib.math.streams.filters.IStreamFilter;

/**
 * This Filter just returns the last value, it can be used 
 * as default values when stream filters are needed
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class DefaultFilter implements IStreamFilter {

    /**
     * Makes a DefaultFilter
     */
    public DefaultFilter() { }

    /**
     * @param next value
     * @return value
     */
    public double get(double next) {
        return next;
    }
}