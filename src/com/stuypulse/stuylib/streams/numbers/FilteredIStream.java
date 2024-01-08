/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.numbers;

import com.stuypulse.stuylib.streams.numbers.filters.IFilter;

/**
 * Takes an {@link IStream} and a {@link IFilter} and makes a {@link FilteredIStream}
 *
 * <p>This could be used to automatically filter controller inputs
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class FilteredIStream implements IStream {

    private IStream mStream; // Stream used
    private IFilter mStreamFilter; // StreamFilter used

    /**
     * Makes filtered stream from stream and stream filter
     *
     * @param stream input stream
     * @param filter stream filter
     */
    public FilteredIStream(IStream stream, IFilter... filter) {
        mStream = stream;
        mStreamFilter = IFilter.create(filter);
    }

    /**
     * Get next value from filtered stream
     *
     * @return next value
     */
    public double get() {
        return mStreamFilter.get(mStream.get());
    }
}
