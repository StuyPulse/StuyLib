/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans;

import com.stuypulse.stuylib.streams.booleans.filters.BFilter;

/**
 * A FilteredBStream is similar to a FilteredIStream.
 *
 * <p>It works like a BStream, but every time you call .get(), it runs the value through the filters
 * you provided it.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class FilteredBStream implements BStream {

    private BStream mStream; // Stream used
    private BFilter mStreamFilter; // StreamFilter used

    /**
     * Makes filtered stream from stream and stream filter
     *
     * @param stream input stream
     * @param filter stream filter
     */
    public FilteredBStream(BStream stream, BFilter... filter) {
        mStream = stream;
        mStreamFilter = BFilter.create(filter);
    }

    /**
     * Get next value from filtered stream
     *
     * @return next value
     */
    public boolean get() {
        return mStreamFilter.get(mStream.get());
    }
}
