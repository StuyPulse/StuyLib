/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.numbers.filters;

/**
 * A class that lets you combine multiple stream filters into one stream filter
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class IFilterGroup implements IFilter {

    // Array of all the filters
    private final IFilter[] mFilters;

    /**
     * Make IFilterGroup out of an array of Filters
     *
     * @param filters list of filters to combine
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
