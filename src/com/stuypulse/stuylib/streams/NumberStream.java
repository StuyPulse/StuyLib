/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams;

/**
 * A number which reads from a streams.
 *
 * <p>Useful for filtering streams (including SmartNumbers) and converting them back into Numbers.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class NumberStream extends Number implements IStream {

    /** the underlying stream */
    private final IStream mStream;

    /**
     * Create a number stream given a stream
     *
     * @param stream
     */
    public NumberStream(IStream stream) {
        mStream = stream;
    }

    /** @return value from stream */
    @Override
    public double get() {
        return mStream.get();
    }

    /** @return value from stream */
    @Override
    public double doubleValue() {
        return get();
    }

    /** @return value from stream as float */
    @Override
    public float floatValue() {
        return (float) get();
    }

    /** @return value from stream as integer */
    @Override
    public int intValue() {
        return (int) get();
    }

    /** @return value from stream as long */
    @Override
    public long longValue() {
        return (long) get();
    }
}
