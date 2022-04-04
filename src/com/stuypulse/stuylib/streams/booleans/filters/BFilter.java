/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

/** @author Sam (sam.belliveau@gmail.com) */
public interface BFilter {

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
}
