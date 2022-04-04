/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans;

import com.stuypulse.stuylib.streams.booleans.filters.BFilter;

import java.util.function.BooleanSupplier;

/** @author Sam (sam.belliveau@gmail.com) */
public interface BStream extends BooleanSupplier {

    /** @return next value in the stream */
    public boolean get();

    /** @return get BStream as a Boolean */
    public default boolean getAsBoolean() {
        return get();
    }

    /**
     * Create a new FilteredBStream from the current stream
     *
     * @param filters the filters you want to apply to the BStream
     * @return The FilteredBStream
     */
    public default FilteredBStream filtered(BFilter... filters) {
        return new FilteredBStream(this, filters);
    }

    /**
     * Create a new PollingBStream from the current stream
     *
     * @param hz the frequency that you want to poll the BStream
     * @return The PollingBStream
     */
    public default PollingBStream polling(double hz) {
        return new PollingBStream(this, hz);
    }
}
