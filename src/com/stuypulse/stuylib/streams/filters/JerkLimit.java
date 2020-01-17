package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.OnDerivative;
import com.stuypulse.stuylib.exception.ConstructionError;
import com.stuypulse.stuylib.math.SLMath;

/**
 * This class lets you limit the jerk a stream of inputs
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class JerkLimit extends OnDerivative {

    /**
     * Makes a new jerk limiter with specified jerk limit
     * 
     * @param jerkLimit desired jerk limit
     */
    public JerkLimit(double jerkLimit) throws ConstructionError {
        super(new OnDerivative((x) -> SLMath.limit(x, jerkLimit)));

        if (jerkLimit <= 0) {
            throw new ConstructionError("KerkLimit(double jerkLimit)", "jerkLimit must be greater than 0!");
        }
    }
}