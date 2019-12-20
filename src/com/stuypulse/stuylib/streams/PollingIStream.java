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
     * @param stream istream to poll from
     * @param hz     Number of calls per second
     */
    public PollingIStream(IStream stream, double hz) {
        if (hz <= 0) {
            throw new ConstructionError("PollingIStream(IStream stream, long hz)", "hz must be greater than 0!");
        }

        mRunning = true;
        mStream = stream;
        mDelta = (long) (1000.0 / hz);
        mResult = 0;

        setName("PollingIStreamThread : " + System.currentTimeMillis());
        setDaemon(true);
        start();
    }

    /**
     * Poll the IStream and put it in mResult
     */
    public void run() {
        while (mRunning) {
            set(mStream.get());
            try {
                Thread.sleep(mDelta);
            } catch (InterruptedException e) {
                mRunning = false;
            }
        }
    }

    /**
     * Thread safe write to the double mResult
     * 
     * @param value new value for mResult
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
        return mResult;
    }
}