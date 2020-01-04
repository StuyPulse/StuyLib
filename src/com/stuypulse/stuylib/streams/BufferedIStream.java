package com.stuypulse.stuylib.streams;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.exception.ConstructionError;

import java.util.ArrayList;

/**
 * This class allows you to use an input stream while recording the last N
 * values from the stream
 * 
 * It extends from IStream, so it also workes with the existing IStream classes
 * 
 * @author Kevin (kc16777216@gmail.com)
 * @author Sam (sam.belliveau@gmail.com)
 */

public class BufferedIStream implements IStream {

    /**
     * Default size of the buffer in BufferedIStream
     */
    public static final int kDefaultSize = 50;

    private ArrayList<Double> mBuffer;
    private IStream mIStream;

    /**
     * Creates a buffered istream with an istream and a custom buffer size
     * 
     * @param istream istream that will be buffered
     * @param size    size of buffer
     */
    public BufferedIStream(IStream istream, int size) throws ConstructionError {
        if (size <= 0) {
            throw new ConstructionError("BufferedIStream(IStream istream, int size)", "size must be greater than 0!");
        }

        mBuffer = new ArrayList<Double>(size);
        mIStream = istream;
    }

    /**
     * Creates a buffered istream with default buffer size (kDefaultSize)
     * 
     * @param istream istren array listam that will be buffered
     */
    public BufferedIStream(IStream istream) {
        this(istream, kDefaultSize);
    }

    /**
     * Get value from istream and add value to buffer
     * 
     * @return value from the istream
     */
    public double get() {
        return get(0);
    }

    /**
     * Get value from istream and add value to buffer
     * 
     * @param delta how far back in the buffer to go
     * @return value from the istream
     */
    public double get(int delta) {
        for (int i = mBuffer.size() - 1; i > 0; --i) {
            mBuffer.set(i, mBuffer.get(i - 1));
        }
        
        mBuffer.set(0, mIStream.get());
        return last(delta);
    }

    /**
     * Get the most recent value from the istream
     * 
     * @return most recent value from the istream
     */
    public double last() {
        return last(0);
    }

    /**
     * Go back [delta] amount in the buffer, ie. 2 elements back
     * 
     * @param delta how far back in the buffer to go
     * @return the value of that spot in the buffer
     */
    public double last(int delta) {
        return mBuffer.get(delta);
    }
}
