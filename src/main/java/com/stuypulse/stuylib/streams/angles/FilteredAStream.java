/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.streams.angles.filters.AFilter;

/**
 * Takes an {@link AStream} and a {@link AFilter} and makes a {@link FilteredAStream}
 *
 * <p>This could be used to automatically filter controller inputs
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class FilteredAStream implements AStream {

    private AStream mStream; // Stream used
    private AFilter mStreamFilter; // StreamFilter used

    /**
     * Makes filtered stream from stream and stream filter
     *
     * @param stream input stream
     * @param filter stream filter
     */
    public FilteredAStream(AStream stream, AFilter... filter) {
        mStream = stream;
        mStreamFilter = AFilter.create(filter);
    }

    /**
     * Get next value from filtered stream
     *
     * @return next value
     */
    public Angle get() {
        return mStreamFilter.get(mStream.get());
    }
}
