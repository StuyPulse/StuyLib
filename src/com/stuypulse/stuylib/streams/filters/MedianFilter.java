package com.stuypulse.stuylib.streams.filters;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A filter that takes a buffer of X values and returns the median
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class MedianFilter implements IFilter {
    
    // Store target size and buffer of values
    private final int mSize;
    private final List<Double> mBuffer;

    // Initialize the buffer and size
    public MedianFilter(int size) {
        mSize = size;
        mBuffer = new LinkedList<Double>();
    }

    // Add the next value to the buffer and get the median
    public double get(double next) {
        mBuffer.add(next);

        while(mSize < mBuffer.size()) {
            mBuffer.remove(0);
        }

        return getMedian();
    }

    // Get the median (THIS IS A BAD WAY)
    // TODO: FIX THIS PLS
    private double getMedian() {
        int size = mBuffer.size();
        if(size > 0) {
            List<Double> copy = new LinkedList<>(mBuffer);
            Collections.sort(copy);

            if((size % 2) == 0) {
                return ((copy.get(size / 2 - 1) + copy.get(size / 2))) / 2.0;
            } else {
                return copy.get(size / 2);
            }
        } else {
            return 0.0;
        }
    }
}