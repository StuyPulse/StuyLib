/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.math.Vector2D;

/**
 * A class that lets you combine multiple vector filters into one vector filter.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class VFilterGroup implements VFilter {

    // Array of all the filters
    private final VFilter[] mFilters;

    /**
     * Make VFilterGroup out of an array of Filters
     *
     * @param filters list of filters to combine
     */
    public VFilterGroup(VFilter... filters) {
        mFilters = filters;
    }

    public Vector2D get(Vector2D next) {
        // Put next through each of the filters
        for (VFilter filter : mFilters) {
            next = filter.get(next);
        }

        // Return filtered value
        return next;
    }
}
