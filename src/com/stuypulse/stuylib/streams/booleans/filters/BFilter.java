/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

import com.stuypulse.stuylib.streams.filters.IFilter;

/**
 * This is the BFilter interface class that gives a definition for how to implement a filter.
 *
 * <p>All that a BFilter does is take in the next boolean in a stream and gives you the filtered
 * value.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface BFilter {

    /** @return a filter that just returns it's input */
    public static BFilter create() {
        return x -> x;
    }

    /**
     * Create an BFilter from another BFilter. This is helpful if you want to use some of the
     * decorator functions with a lambda.
     *
     * @param filter filter to create BFilter from
     * @return the resulting BFilter
     */
    public static BFilter create(BFilter filter) {
        return filter;
    }

    /**
     * Create a BFilter from an IFilter. This will cast the boolean to a double, filter it, and cast
     * it back to a boolean.
     *
     * @param filter filter to create IFilter from
     * @return the resulting IFilter
     */
    public static BFilter create(IFilter filter) {
        return x -> Math.abs(filter.get(x ? 1.0 : 0.0)) > 0.5;
    }

    /**
     * Get next value in Filter based on the next value given
     *
     * @param next next input value in the stream
     * @return the output value of the filter
     */
    public boolean get(boolean next);

    /**
     * Combine an BFilter with another BFilter
     *
     * @param next filter to be evaluated after this one
     * @return the combined filter
     */
    public default BFilter then(BFilter next) {
        return x -> next.get(get(x));
    }

    /**
     * Combine two BFilters by and'ing their results together
     *
     * @param other other BFilter to and with this one
     * @return the resulting BFilter after the and
     */
    public default BFilter and(BFilter other) {
        return x -> get(x) & other.get(x);
    }

    /**
     * Combine two BFilters by or'ing their results together
     *
     * @param other other BFilter to or with this one
     * @return the resulting BFilter after the or
     */
    public default BFilter or(BFilter other) {
        return x -> get(x) | other.get(x);
    }

    /**
     * Combine two BFilters by xor'ing their results together
     *
     * @param other other BFilter to xor with this one
     * @return the resulting BFilter after the xor
     */
    public default BFilter xor(BFilter other) {
        return x -> get(x) ^ other.get(x);
    }
}
