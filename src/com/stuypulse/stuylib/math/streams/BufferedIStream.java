package com.stuypulse.stuylib.math.streams;

import com.stuypulse.stuylib.math.streams.IStream;

import edu.wpi.first.wpilibj.CircularBuffer;

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

    /**
     * Stores the number of elements in the stream buffer, and stores the last n
     * values in a circular buffer
     */
    private CircularBuffer mBuffer;

    /**
     * The input stream that is buffered. This class
     * is effectively passed through get()
     * The input stream that is buffered. This class is effectivly passed through
     * get()
     */
    private IStream mIStream;

    /**
     * Creates a buffered istream with an istream and a custom buffer size
     * 
     * @param istream istream that will be buffered
     * @param size    size of buffer
     */
    public BufferedIStream(IStream istream, int size) {
        mBuffer = new CircularBuffer(size);
        mIStream = istream;

        for (int i = 0; i < size; ++i) {
            mBuffer.addFirst(0.0);
        }
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
        double value = mIStream.get();
        mBuffer.addFirst(value);
        return value;
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
