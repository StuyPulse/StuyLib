package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.exception.ConstructionError;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Takes an IStream and an IStreamFilter and makes a PollingIStream
 * 
 * A PollingIStream calls .get() every x milliseconds instead of when the user
 * calls get
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class PollingIStream implements IStream {

    private Thread mPoller;
    private double mResult;

    /**
     * Creates a PollingIStream from an IStream and a time value
     * 
     * @param stream istream to poll from
     * @param hz     Number of calls per second
     */
    public PollingIStream(IStream stream, double hz) {
        if (hz <= 0) {
            throw new ConstructionError("PollingIStream(IStream stream, long hz)", "hz must be greater than 0!");
        }

        long wait = (long)(1000.0 / hz);

        mPoller = new Thread() {
            public void run() {
                while(!isInterrupted()) {
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
    private synchronized void set(double value) {
        mResult = value;
    }

    /**
     * Thread safe read to the double mResult
     * 
     * @return mResult
     */
    public synchronized double get() {
        if(!mPoller.isAlive()) {
            mPoller.start();
        }

        return mResult;
    }
}