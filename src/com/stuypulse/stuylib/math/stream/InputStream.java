package com.stuypulse.stuylib.math.stream;

/**
 * A stream of doubles that is accessed 
 * with the next function
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface InputStream {

    /**
     * Get the next value in the stream
     * @return next value in the stream
     */
    public double get();
}