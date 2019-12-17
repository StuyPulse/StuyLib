package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.exception.ConstructionError;

/**
 * Takes an IStream and an IStreamFilter and makes a PollingIStream
 * 
 * A PollingIStream calles .get() every x milliseconds instead of when the user
 * calls get
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class PollingIStream extends Thread implements IStream {

    // If the class should be polling or not
    private boolean mRunning;

    // The stream that this class polls from
    private IStream mStream;

    // The result of each poll
    private double mResult;

    // Time inbetween each poll
    private long mDelta;

    /**
     * Creates a PollingIStream from an IStream and a time value
     * 
     * @param stream  istream to poll from
     * @param hz Number of calls per second
     */
    public PollingIStream(IStream stream, long hz) {
        if (hz <= 0) {
            throw new ConstructionError("PollingIStream(IStream stream, long hz)", "hz must be greater than 0!");
        }

        mRunning = true;
        mStream = stream;
        mDelta = 1000 / hz;
        mResult = 0;
        start();
    }

    /**
     * Poll the IStream and put it in mResult
     */
    public void run() {
        while (mRunning) {
            try { Thread.sleep(mDelta); } 
            catch (InterruptedException e) {}
            set(mStream.get());
        }
    }

    /**
     * Stops polling the IStream
     */
    public void stopPolling() {
        mRunning = false;
    }

    /**
     * Thread safe write to the double mResult
     * @param value new value for mResult
     */
    private synchronized void set(double value) {
        mResult = value;
    }

    /**
     * Thread safe read to the double mResult
     * @return mResult
     */
    public synchronized double get() {
        return mResult;
    }
}