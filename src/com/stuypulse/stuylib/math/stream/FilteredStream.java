package com.stuypulse.stuylib.math.stream;

import com.stuypulse.stuylib.math.stream.Stream;
import com.stuypulse.stuylib.math.stream.filter.StreamFilter;

/**
 * Takes a Stream and a StreamFilter and makes a FilteredStream
 * 
 * This could be used to automatically filter controller inputs
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class FilteredStream implements Stream {

    private Stream mStream; // Stream used
    private StreamFilter mStreamFilter; // StreamFilter used

    /**
     * Makes filtered stream from stream and stream filter
     * @param stream stream
     * @param filter filter
     */
    public FilteredStream(Stream stream, StreamFilter filter) {
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