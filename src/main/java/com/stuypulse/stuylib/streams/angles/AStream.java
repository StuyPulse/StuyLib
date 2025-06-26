/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.streams.angles.filters.AFilter;
import com.stuypulse.stuylib.streams.numbers.IStream;
import com.stuypulse.stuylib.streams.vectors.VStream;

import java.util.function.Supplier;

public interface AStream extends Supplier<Angle> {

    public static AStream create(AStream stream) {
        return stream;
    }

    public static AStream create(IStream stream) {
        return () -> Angle.fromRadians(stream.get());
    }

    public static AStream create(VStream stream) {
        return () -> stream.get().getAngle();
    }

    /** @return next value in the stream */
    public Angle get();

    /**
     * Create a new FilteredAStream from the current stream
     *
     * @param filters the filters you want to apply to the AStream
     * @return The FilteredAStream
     */
    public default FilteredAStream filtered(AFilter... filters) {
        return new FilteredAStream(this, filters);
    }

    /**
     * Create a new PollingAStream from the current stream
     *
     * @param dt the time inbetween each poll of the AStream
     * @return The PollingAStream
     */
    public default PollingAStream polling(double dt) {
        return new PollingAStream(this, dt);
    }

    /**
     * Combine two AStreams by adding their results together
     *
     * @param other other AStream to add to this one
     * @return the resulting AStream after the sum
     */
    public default AStream add(AStream other) {
        return () -> get().add(other.get());
    }

    /**
     * Combine two AStreams by subtracting their results together
     *
     * @param other other AStream to subtract from this one
     * @return the resulting AStream after the subtraction
     */
    public default AStream sub(AStream other) {
        return () -> get().sub(other.get());
    }
}
