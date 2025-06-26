/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans;

import edu.wpi.first.wpilibj.Notifier;

/**
 * A PollingBStream is a BStream but its .get() method is called for you at a certain rate. This is
 * really helpful when you want to read from a sensor using a debouncer, but you dont always call
 * .get() in your periodic loop. This will do that for you, and give you the most recent result. It
 * is not recommended to filter a PollingBStream as you should ideally filter before you poll.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PollingBStream implements BStream {

    private Notifier mPoller;
    private volatile boolean mResult;

    /**
     * Creates a PollingBStream from an BStream and a time value
     *
     * @param stream BStream to poll from
     * @param dt time inbetween each poll
     */
    public PollingBStream(BStream stream, double dt) {
        if (dt <= 0) {
            throw new IllegalArgumentException("dt must be greater than 0");
        }

        mResult = false;
        mPoller = new Notifier(() -> mResult = stream.get());
        mPoller.startPeriodic(dt);
    }

    public boolean get() {
        return mResult;
    }

    protected void finalize() {
        close();
    }

    public void close() {
        mPoller.close();
        mResult = false;
    }
}
