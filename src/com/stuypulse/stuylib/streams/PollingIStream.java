package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.exception.ConstructionError;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Takes an IStream and an IStreamFilter and makes a PollingIStream
 * 
 * A PollingIStream calles .get() every x milliseconds instead of when the user
 * calls get
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class PollingIStream implements IStream {

    private Timer mTimer;
    private volatile double mResult;

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

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(
            new TimerTask() {
                public void run() {
                    mResult = stream.get();
                }
            }, 0, (long)(1000.0 / hz)
        );
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