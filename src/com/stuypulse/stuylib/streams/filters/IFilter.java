/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.booleans.filters.BFilter;

/**
 * This is the Filter interface class that gives a definition for how to implement a filter.
 *
 * <p>All that a filter does is take in the next double in a series and gives you the filtered
 * value.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface IFilter {

    /** @return a filter that just returns it's input */
    public static IFilter create() {
        return x -> x;
    }

    /**
     * Create an IFilter from another IFilter. This is helpful if you want to use some of the
     * decorator functions with a lambda.
     *
     * @param filter filter to create IFilter from
     * @return the resulting IFilter
     */
    public static IFilter create(IFilter filter) {
        return filter;
    }

    /**
     * Create an IFilter from a BFilter. This will cast the double to a boolean, filter it, and cast
     * it back to a double.
     *
     * @param filter filter to create IFilter from
     * @return the resulting IFilter
     */
    public static IFilter create(BFilter filter) {
        return x -> filter.get(Math.abs(x) > 0.5) ? 1.0 : 0.0;
    }

    /**
     * Get next value in Filter based on the next value given
     *
     * @param next next input value in the stream
     * @return the output value of the filter
     */
    public double get(double next);

    /**
     * Combine an IFilter with another IFilter
     *
     * @param next filter to be evaluated after this one
     * @return the combined filter
     */
    public default IFilter then(IFilter next) {
        return x -> next.get(get(x));
    }

    /**
     * Combine two IFilters by adding their results together
     *
     * @param other other IFilter to add to this one
     * @return the resulting IFilter after the sum
     */
    public default IFilter add(IFilter other) {
        return x -> get(x) + other.get(x);
    }

    /**
     * Combine two IFilters by subtracting their results together
     *
     * @param other other IFilter to subtract from this one
     * @return the resulting IFilter after the subtraction
     */
    public default IFilter sub(IFilter other) {
        return x -> get(x) - other.get(x);
    }
}
