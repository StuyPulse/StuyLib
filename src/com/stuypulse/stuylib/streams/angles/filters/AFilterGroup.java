/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles.filters;

import com.stuypulse.stuylib.math.Angle;

/**
 * A class that lets you combine multiple stream filters into one stream filter
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class AFilterGroup implements AFilter {

    // Array of all the filters
    private final AFilter[] mFilters;

    /**
     * Make AFilterGroup out of an array of Filters
     *
     * @param filters list of filters to combine
     */
    public AFilterGroup(AFilter... filters) {
        mFilters = filters;
    }

    public Angle get(Angle next) {
        // Put next through each of the filters
        for (AFilter filter : mFilters) {
            next = filter.get(next);
        }

        // Return filtered value
        return next;
    }
}
