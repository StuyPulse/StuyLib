package com.stuypulse.stuylib.math.streams;

/**
 * A stream of doubles that is accessed 
 * with the next function
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface IStream {

    /**
     * Get the next value in the stream
     * @return next value in the stream
     */
    public double get();
}