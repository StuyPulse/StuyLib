package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.exception.ConstructionError;

/**
 * This class lets you take the integral of an IStream using a filter
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class IntegralFilter implements IStreamFilter {

    private double mDivisor;
    private double mIntegral;

    /**
     * Make an integral filter
     * 
     * @param divisor The amount to scale the integral
     */
    public IntegralFilter(double divisor) throws ConstructionError {
        if (divisor <= 0) {
            throw new ConstructionError("Integral(double divisor)", "divisor must be greater than 0!");
        }

        mDivisor = divisor;
        mIntegral = 0;
    }

    /**
     * Makes an integral filter with a divisor of 1
     */
    public IntegralFilter() {
        this(1);
    }

    public double get(double next) {
        return (mIntegral += next) / mDivisor;
    }
}