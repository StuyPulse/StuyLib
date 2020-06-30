package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.streams.filters.IFilter;

/**
 * A stream of doubles that is accessed with the .get() function
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public interface IStream {

    /**
     * Get the next value in the stream
     *
     * @return next value in the stream
     */
    public double get();

    /**
     * Create a new FilteredIStream
     *
     * @param filters the filters you want to apply to the IStream
     * @return The FilteredIStream
     */
    public default FilteredIStream filtered(IFilter... filters) {
        return new FilteredIStream(this, filters);
    }
}
