/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

/**
 * This is the BFilter interface class that gives a definition for how to implement a filter.
 *
 * <p>All that a BFilter does is take in the next boolean in a stream and gives you the filtered
 * value.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface BFilter {

    /**
     * Create a BFilter from a list of BFilters. This will create a BFilter with the least possible
     * overhead for the number of filters provided.
     *
     * @param filters list of BFilters to apply
     * @return an BFilter that uses every filter given
     */
    public static BFilter create(BFilter... filters) {
        switch (filters.length) {
            case 0:
                return x -> x;
            case 1:
                return filters[0];
            case 2:
                return x -> filters[1].get(filters[0].get(x));
            case 3:
                return x -> filters[2].get(filters[1].get(filters[0].get(x)));
            case 4:
                return x -> filters[3].get(filters[2].get(filters[1].get(filters[0].get(x))));
            default:
                return new BFilterGroup(filters);
        }
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
