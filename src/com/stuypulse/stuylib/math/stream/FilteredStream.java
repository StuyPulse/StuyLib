package com.stuypulse.stuylib.math.stream;

import com.stuypulse.stuylib.math.stream.InputStream;
import com.stuypulse.stuylib.math.stream.filter.StreamFilter;

/**
 * Takes a Stream and a StreamFilter and makes a FilteredStream
 * 
 * This could be used to automatically filter controller inputs
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class FilteredStream implements InputStream {

    private InputStream mStream; // Stream used
    private StreamFilter mStreamFilter; // StreamFilter used

    /**
     * Makes filtered stream from stream and stream filter
     * @param stream input stream
     * @param filter stream filter
     */
    public FilteredStream(InputStream stream, StreamFilter filter) {
        mStream = stream;
        mStreamFilter = filter;
    }

    /**
     * Get next value from filtered stream
     * @return next value
     */
    public double next() {
        return mStreamFilter.get(mStream.next());
    }
}