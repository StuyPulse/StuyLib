package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.math.SLMath;

/**
 * A set of filters that uses algorithms from InputScaler that can be used as a
 * StreamFilter.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public interface SLMathFilter {

    /**
     * Make stream filter that deadbands values within window
     * 
     * @param window deadband window
     * @return deadband filter
     */
    public static IStreamFilter deadband(double window) {
        return (x) -> SLMath.deadband(x, window);
    }

    /**
     * Makes filter that squares every value while keeping its sign
     * 
     * @return square filter
     */
    public static IStreamFilter square() {
        return (x) -> SLMath.square(x);
    }

    /**
     * Makes filter that cubes every value
     * 
     * @return cube filter
     */
    public static IStreamFilter cube() {
        return (x) -> SLMath.cube(x);
    }

    /**
     * Makes filter that raises every value to a power while keeping its sign
     * 
     * @param power the power that the filter raises inputs to
     * @return pow filter
     */
    public static IStreamFilter pow(double power) {
        return (x) -> SLMath.pow(x, power);
    }

    /**
     * Makes filter that runs each value through the circular algorithm
     * 
     * @return circular filter
     */
    public static IStreamFilter circular() {
        return (x) -> SLMath.circular(x);
    }

    /**
     * Makes filter that runs each value through the circular algorithm
     * 
     * @param p the power that the circular algorithm uses
     * @return circular filter
     */
    public static IStreamFilter circular(double p) {
        return (x) -> SLMath.circular(x, p);
    }
}