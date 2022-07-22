/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles.filters;

import com.stuypulse.stuylib.math.Angle;

/**
 * This is the Filter interface class that gives a definition for how to implement a filter.
 *
 * <p>All that a filter does is take in the next double in a series and gives you the filtered
 * value.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface AFilter {

    /**
     * Create an AFilter from a list of AFilters. This will create an AFilter with the least
     * possible overhead for the number of filters provided.
     *
     * @param filters list of AFilters to apply
     * @return an AFilter that uses every filter given
     */
    public static AFilter create(AFilter... filters) {
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
                return new AFilterGroup(filters);
        }
    }

    /**
     * Get next value in Filter based on the next value given
     *
     * @param next next input value in the stream
     * @return the output value of the filter
     */
    public Angle get(Angle next);

    /**
     * Combine an AFilter with another AFilter
     *
     * @param next filter to be evaluated after this one
     * @return the combined filter
     */
    public default AFilter then(AFilter next) {
        return x -> next.get(get(x));
    }

    /**
     * Combine two AFilters by adding their results together
     *
     * @param other other AFilter to add to this one
     * @return the resulting AFilter after the sum
     */
    public default AFilter add(AFilter other) {
        return x -> get(x).add(other.get(x));
    }

    /**
     * Combine two AFilters by subtracting their results together
     *
     * @param other other AFilter to subtract from this one
     * @return the resulting AFilter after the subtraction
     */
    public default AFilter sub(AFilter other) {
        return x -> get(x).sub(other.get(x));
    }

    /**
     * Invert an AFilter by subtracting the input from the result of the AFilter.
     *
     * <p>Inverting a LowPassFilter gives you a HighPassFilter and vise versa.
     *
     * <p>Inverting something twice gives you the original value.
     *
     * @return the inverted filter
     */
    public default AFilter invert() {
        return x -> x.sub(get(x));
    }
}
