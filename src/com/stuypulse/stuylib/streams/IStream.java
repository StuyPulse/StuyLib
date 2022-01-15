/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.streams.filters.IFilter;

/**
 * A stream of doubles that is accessed with the {@link IStream#get()} function
 *
 * <p>Can be created with lambdas to represent a stream of inputs.
 *
 * <p>It can be filitered with the available filtering options.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface IStream {

    /** @return next value in the stream */
    public double get();

    /**
     * Create a new FilteredIStream from the current stream
     *
     * @param filters the filters you want to apply to the IStream
     * @return The FilteredIStream
     */
    public default FilteredIStream filtered(IFilter... filters) {
        return new FilteredIStream(this, filters);
    }
}
