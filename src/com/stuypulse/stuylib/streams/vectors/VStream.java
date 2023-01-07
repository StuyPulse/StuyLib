/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.angles.AStream;
import com.stuypulse.stuylib.streams.vectors.filters.VFilter;

import java.util.function.Supplier;

/**
 * A VStream is similar to an IStream, but instead of a stream of doubles, it represents a stream of
 * Vector2Ds.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface VStream extends Supplier<Vector2D> {

    public static VStream create(VStream stream) {
        return stream;
    }

    public static VStream create(AStream stream) {
        return () -> stream.get().getVector();
    }

    public static VStream create(IStream x, IStream y) {
        return () -> new Vector2D(x.get(), y.get());
    }

    /** @return next value in the stream */
    public Vector2D get();

    /**
     * Create a new FilteredVStream from the current stream
     *
     * @param filters the filters you want to apply to the VStream
     * @return The FilteredVStream
     */
    public default FilteredVStream filtered(VFilter... filters) {
        return new FilteredVStream(this, filters);
    }

    /**
     * Create a new PollingVStream from the current stream
     *
     * @param dt the time inbetween each poll of the VStream
     * @return The PollingVStream
     */
    public default PollingVStream polling(double dt) {
        return new PollingVStream(this, dt);
    }

    /**
     * Combine two VStreams by adding their results together
     *
     * @param other other VStream to and with this one
     * @return the resulting VStream after the and
     */
    public default VStream add(VStream other) {
        return () -> get().add(other.get());
    }

    /**
     * Combine two VStreams by subtracting their results together
     *
     * @param other other VStream to or with this one
     * @return the resulting VStream after the or
     */
    public default VStream sub(VStream other) {
        return () -> get().sub(other.get());
    }

    /**
     * Combine two VStreams into an IStream by dot producting them together
     *
     * @param other other VStream to dot with this one
     * @return the IStream with the dot product
     */
    public default IStream dot(VStream other) {
        return () -> get().dot(other.get());
    }
}
