/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

/**
 * A class that lets you combine multiple boolean filters into one boolean filter
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class BFilterGroup implements BFilter {

    // Array of all the filters
    private final BFilter[] mFilters;

    /**
     * Make BFilterGroup out of an array of Filters
     *
     * @param filters list of filters to combine
     */
    public BFilterGroup(BFilter... filters) {
        mFilters = filters;
    }

    public boolean get(boolean next) {
        // Put next through each of the filters
        for (BFilter filter : mFilters) {
            next = filter.get(next);
        }

        // Return filtered value
        return next;
    }
}
