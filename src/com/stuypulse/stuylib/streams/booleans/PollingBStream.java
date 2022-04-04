/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans;

/** @author Sam (sam.belliveau@gmail.com) */
public class PollingBStream {

    private Thread mPoller;
    private boolean mResult;

    /**
     * Creates a PollingBStream from an BStream and a time value
     *
     * @param stream BStream to poll from
     * @param hz Number of calls per second
     */
    public PollingBStream(BStream stream, double hz) {
        if (hz <= 0) {
            throw new IllegalArgumentException("hz must be greater than 0");
        }

        long wait = (long) (1000.0 / hz);

        mPoller =
                new Thread() {

                    public void run() {
                        while (!isInterrupted()) {
                            long start = System.currentTimeMillis();
                            set(stream.get());
                            long end = System.currentTimeMillis();

                            try {
                                Thread.sleep(Math.max(0, wait - (end - start)));
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                };
    }

    /**
     * Thread safe read to the double mResult
     *
     * @return mResult
     */
    private synchronized void set(boolean value) {
        mResult = value;
    }

    /**
     * Thread safe read to the double mResult
     *
     * @return mResult
     */
    public synchronized boolean get() {
        if (!mPoller.isAlive()) {
            mPoller.start();
        }

        return mResult;
    }
}
