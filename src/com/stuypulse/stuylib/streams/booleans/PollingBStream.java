/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans;

import com.stuypulse.stuylib.util.Looper;

/**
 * A PollingBStream is a BStream but its .get() method is called for you at a certain rate. This is
 * really helpful when you want to read from a sensor using a debouncer, but you dont always call
 * .get() in your periodic loop. This will do that for you, and give you the most recent result. It
 * is not recommended to filter a
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PollingBStream implements BStream {

    private Looper mPoller;
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
        mPoller = new Looper(() -> mResult = stream.get(), dt);
    }

    public boolean get() {
        if (!mPoller.isAlive()) {
            mPoller.start();
        }

        return mResult;
    }
}
