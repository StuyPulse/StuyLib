package com.stuypulse.stuylib.streams.filters;

/**
 * This lets us make sub-classes that change can modify values in this way
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public interface IFilter {

    /**
     * Get next value in Filter based on the next value given
     *
     * @param next next input value
     * @return next modified value
     */
    public double get(double next);
}
