/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles;

import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.wpilibj.Notifier;

/**
 * A PollingAStream calls {@link AStream#get()} every x milliseconds instead of when the user calls
 * get
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PollingAStream implements AStream, AutoCloseable {

    private Notifier mPoller;
    private volatile Angle mResult;

    /**
     * Creates a PollingAStream from an AStream and a time value
     *
     * @param stream astream to poll from
     * @param dt Number of calls per second
     */
    public PollingAStream(AStream stream, double dt) {
        if (dt <= 0) {
            throw new IllegalArgumentException("dt must be greater than 0");
        }

        mResult = Angle.kNull;
        mPoller = new Notifier(() -> this.set(stream.get()));
        mPoller.startPeriodic(dt);
    }

    private final synchronized void set(Angle result) {
        mResult = result;
    }

    public final synchronized Angle get() {
        return mResult;
    }

    protected synchronized void finalize() {
        close();
    }

    public synchronized void close() {
        mPoller.close();
        mResult = Angle.kNull;
    }
}
