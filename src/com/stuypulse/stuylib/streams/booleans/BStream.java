/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans;

import com.stuypulse.stuylib.streams.booleans.filters.BFilter;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.button.Button;
import java.util.function.BooleanSupplier;

/**
 * A BStream is similar to an IStream, but instead of a stream of doubles, it represents a stream of
 * booleans.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface BStream extends BooleanSupplier {

    /**
     * Create a BStream from another BStream. This is helpful if you want to use some of the
     * decorator functions with a lambda.
     *
     * @param stream stream to create BStream from
     * @return the resulting BStream
     */
    public static BStream create(BStream stream) {
        return stream;
    }

    /**
     * Create a BStream from a digital source. This can helpful for processing a digital stream,
     * like negating the value when used for switches.
     *
     * @param input digital input object
     * @return the resulting BStream
     */
    public static BStream create(DigitalInput input) {
        return input::get;
    }

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
     * @param dt the time inbetween each poll of the BStream
     * @return The PollingBStream
     */
    public default PollingBStream polling(double dt) {
        return new PollingBStream(this, dt);
    }

    /**
     * Combine two BStreams by and'ing their results together
     *
     * @param other other BStream to and with this one
     * @return the resulting BStream after the and
     */
    public default BStream and(BStream other) {
        return () -> get() & other.get();
    }

    /**
     * Combine two BStreams by or'ing their results together
     *
     * @param other other BStream to or with this one
     * @return the resulting BStream after the or
     */
    public default BStream or(BStream other) {
        return () -> get() | other.get();
    }

    /**
     * Combine two BStreams by xor'ing their results together
     *
     * @param other other BStream to xor with this one
     * @return the resulting BStream after the xor
     */
    public default BStream xor(BStream other) {
        return () -> get() ^ other.get();
    }

    /**
     * Create a BStream that returns the opposite result as the original
     *
     * @return the resulting BStream after the not operation
     */
    public default BStream not() {
        return () -> !get();
    }

    /** @return a WPILib Button that is pressed when this class is true */
    public default Button toButton() {
        return new Button(this);
    }
}
