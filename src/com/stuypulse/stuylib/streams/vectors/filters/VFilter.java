/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors.filters;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.numbers.filters.IFilter;

/**
 * This is the VFilter interface class that gives a definition for how to implement a filter.
 *
 * <p>All that a VFilter does is take in the next vector in a stream and gives you the filtered
 * value.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface VFilter {

    /**
     * Create a VFilter from a list of VFilters. This will create a VFilter with the least possible
     * overhead for the number of filters provided.
     *
     * @param filters list of VFilters to apply
     * @return an VFilter that uses every filter given
     */
    public static VFilter create(VFilter... filters) {
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
                return new VFilterGroup(filters);
        }
    }

    /**
     * Create a VFilter by using an IFilter on the x and y components.
     *
     * @param x IFilter used on x component
     * @param y IFilter used on y component
     * @return resulting VFilters
     */
    public static VFilter create(IFilter x, IFilter y) {
        return new XYFilter(x, y);
    }

    /**
     * Get next value in Filter based on the next value given
     *
     * @param next next input value in the stream
     * @return the output value of the filter
     */
    public Vector2D get(Vector2D next);

    /**
     * Combine an VFilter with another VFilter
     *
     * @param next filter to be evaluated after this one
     * @return the combined filter
     */
    public default VFilter then(VFilter next) {
        return x -> next.get(get(x));
    }

    /**
     * Combine two VFilters by adding their results together
     *
     * @param other other VFilter to and with this one
     * @return the resulting VFilter after the and
     */
    public default VFilter add(VFilter other) {
        return x -> get(x).add(other.get(x));
    }

    /**
     * Combine two VFilters by subtracting their results together
     *
     * @param other other VFilter to or with this one
     * @return the resulting VFilter after the or
     */
    public default VFilter sub(VFilter other) {
        return x -> get(x).sub(other.get(x));
    }
}
