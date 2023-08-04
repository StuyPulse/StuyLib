/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.vectors;

import com.stuypulse.stuylib.math.Vector2D;

import edu.wpi.first.wpilibj.Notifier;

/**
 * A PollingVStream is a VStream but its .get() method is called for you at a certain rate. This
 * contains race conditions.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PollingVStream implements VStream {

    private Notifier mPoller;
    private volatile Vector2D mResult;

    /**
     * Creates a PollingVStream from an VStream and a time value
     *
     * @param stream VStream to poll from
     * @param dt time inbetween each poll
     */
    public PollingVStream(VStream stream, double dt) {
        if (dt <= 0) {
            throw new IllegalArgumentException("dt must be greater than 0");
        }

        mResult = Vector2D.kOrigin;
        mPoller = new Notifier(() -> this.set(stream.get()));
        mPoller.startPeriodic(dt);
    }

    private final synchronized void set(Vector2D result) {
        mResult = result;
    }

    public final synchronized Vector2D get() {
        return mResult;
    }

    protected synchronized void finalize() {
        close();
    }

    public synchronized void close() {
        mPoller.close();
        mResult = Vector2D.kOrigin;
    }
}
