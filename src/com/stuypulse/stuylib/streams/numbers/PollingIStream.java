/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.numbers;

import edu.wpi.first.wpilibj.Notifier;

/**
 * A PollingIStream calls {@link IStream#get()} every x milliseconds instead of when the user calls
 * get
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PollingIStream implements IStream, AutoCloseable {

    private Notifier mPoller;
    private volatile double mResult;

    /**
     * Creates a PollingIStream from an IStream and a time value
     *
     * @param stream istream to poll from
     * @param dt Number of calls per second
     */
    public PollingIStream(IStream stream, double dt) {
        if (dt <= 0) {
            throw new IllegalArgumentException("dt must be greater than 0");
        }

        mResult = 0.0;
        mPoller = new Notifier(() -> mResult = stream.get());
        mPoller.startPeriodic(dt);
    }

    public double get() {
        return mResult;
    }

    protected void finalize() {
        close();
    }

    public void close() {
        mPoller.close();
        mResult = 0.0;
    }
}
