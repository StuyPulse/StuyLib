/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.streams.filters.IFilter;

import java.util.function.DoubleSupplier;

/**
 * A stream of doubles that is accessed with the {@link IStream#get()} function
 *
 * <p>Can be created with lambdas to represent a stream of inputs.
 *
 * <p>It can be filitered with the available filtering options.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface IStream extends DoubleSupplier {

    /** @return next value in the stream */
    public double get();

    /** @return get IStream as a double */
    public default double getAsDouble() {
        return get();
    }

    /**
     * Create a new FilteredIStream from the current stream
     *
     * @param filters the filters you want to apply to the IStream
     * @return The FilteredIStream
     */
    public default FilteredIStream filtered(IFilter... filters) {
        return new FilteredIStream(this, filters);
    }

    /**
     * Create a new PollingIStream from the current stream
     *
     * @param hz the frequency that you want to poll the IStream
     * @return The PollingIStream
     */
    public default PollingIStream polling(double hz) {
        return new PollingIStream(this, hz);
    }
}
