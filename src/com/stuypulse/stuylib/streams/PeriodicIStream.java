package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.IFilterGroup;

/**
 * A periodic stream is used to periodically update a value. When retrieving
 * this value, a filter can be applied.
 * 
 * @author Myles Pasetsky (selym3)
 */
public class PeriodicIStream implements IStream {

    /**
     * Stored value.
     */
    private double value;

    /**
     * Filter to apply during retrieval.
     */
    private IFilter filter;

    /**
     * Create a PeriodicIStream.
     * 
     * @param filters list of filters
     */
    public PeriodicIStream(IFilter... filters) {
        this.filter = new IFilterGroup(filters);
    }

    /**
     * Update the stored value.
     * 
     * @param value new value
     * @return a reference to the istream
     */
    public PeriodicIStream set(double value) {
        this.value = value;
        return this;
    }

    /**
     * Get the next value.
     * 
     * @return the filtered value
     */
    public double get() {
        return filter.get(value);
    }

}
