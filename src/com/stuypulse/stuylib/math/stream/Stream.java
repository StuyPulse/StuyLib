package com.stuypulse.stuylib.math.stream;

/**
 * A stream of doubles that is accessed 
 * with the next function
 */
public interface Stream {
    
    /**
     * Get the next value in the stream
     * @return next value in the stream
     */
    public double next();
}