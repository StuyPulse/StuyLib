package com.stuypulse.stuylib.math.streams;

import com.stuypulse.stuylib.math.streams.IStream;
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
     * @param deltams time inbetween each poll in ms
     */
    public PollingIStream(IStream stream, long deltams) {
        if (deltams <= 0) {
            throw new ConstructionError("PollingIStream(IStream stream, long deltams)", "deltams must be greater than 0!");
        }

        mRunning = true;
        mStream = stream;
        mDelta = deltams;
        mResult = 0;
        start();
    }

    /**
     * Poll the IStream and put it in mResult
     */
    public synchronized void run() {
        while (mRunning) {
            mResult = mStream.get();
            try {
                Thread.sleep(mDelta);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Stops polling the IStream
     */
    public synchronized void stopPolling() {
        mRunning = false;
    }

    /**
     * Get the last value from the IStream that was polled
     */
    public synchronized double get() {
        return mResult;
    }
}