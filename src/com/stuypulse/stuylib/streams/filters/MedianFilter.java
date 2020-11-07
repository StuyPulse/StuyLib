package com.stuypulse.stuylib.streams.filters;

import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A median filter implementation using an ordered window and a value queue.
 *  
 * @author Myles Pasetsky (selym3)
 */
public class MedianFilter implements IFilter {

    /**
     * The size of the input window to get the median of.
     */
    private final int mSize;

    /**
     * This queue stores values in the order they are inputted.
     */
    private Queue<Double> mBuffer;

    /**
     * This list is acts as an ordered window. All operations to this
     * list maintain its ascending order.
     */
    private List<Double> mOrdered;

    /**
     * Creates a median filter with a fixed size window.
     * 
     * @param size window size
     */
    public MedianFilter(int size) {
        if (size < 1) 
            throw new IllegalArgumentException("Window size for MedianFilter must be greater than 0");
        
        mSize = size;

        mBuffer = new LinkedList<>();
        mOrdered = new ArrayList<>();

    }

    /**
     * Uses the binary search algorithm to find the index to
     * insert next so that the ordered window stays ordered.
     * 
     * @param next the new input
     * @return the index to insert at
     */
    private int getSortedIndex(double next) {
        int idx = Collections.binarySearch(mOrdered, next);

        if (idx < 0)
            idx = -1 * (idx + 1); 

        return idx;
    }

    @Override
    public double get(double next) {
        // maintain the sorted list 
        int idx = getSortedIndex(next);
        mOrdered.add(idx, next);

        int orderedSize = mOrdered.size();
        
        // if the ordered list is greater than the window size
        // remove the first element in the buffer from the window 
        if (orderedSize > mSize) {
            mOrdered.remove(mBuffer.remove());
            --orderedSize;
        }

        // push the value to the back of the buffer
        // because mSize > 0, there will always be
        // a value in the buffer ready to remove
        mBuffer.add(next);
        
        // get the median from the ordered window
        double mid = mOrdered.get(orderedSize/2);
        if ((orderedSize%2)==1)
            return mid;
        else
            return (mOrdered.get(orderedSize/2 - 1) + mid) / 2.0;
        
    }
    
}
