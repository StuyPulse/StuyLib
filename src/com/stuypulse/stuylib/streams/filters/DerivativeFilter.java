package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.exception.ConstructionError;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class lets you rate limit a stream of inputs
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class DerivativeFilter implements IStreamFilter {

    private Queue<Double> mBuffer;

    /**
     * Creates a derivative filter with a sample size of delta elements
     * 
     * @param delta the sample size that the derivative works with
     */
    public DerivativeFilter(int delta) throws ConstructionError {
        if (delta <= 0) {
            throw new ConstructionError("Derivative(int delta)", "delta must be greater than 0!");
        }

        mBuffer = new LinkedList<Double>();
        for (int i = 0; i < delta; ++i) {
            mBuffer.add(0.0);
        }
    }

    /**
     * Creates a derivative filter
     */
    public DerivativeFilter() {
        this(1);
    }

    public double get(double next) {
        mBuffer.add(next);
        return next - mBuffer.remove();
    }
}