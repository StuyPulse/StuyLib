package com.stuypulse.stuylib.math.stream.filter;

/**
 * This lets us make sub-classes that change
 * can modify values in this way
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface StreamFilter {

    /**
     * Get next value in StreamModifier
     * @param next next input value
     * @return next modified value
     */
    double get(double next);
}