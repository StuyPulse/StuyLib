package com.stuypulse.stuylib.math.streams.filters;

import com.stuypulse.stuylib.math.InputScaler;

/**
 * A set of filters that uses algorithms from InputScaler
 * that can be used as a StreamFilter.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public interface BasicFilters {

    /**
     * Stream Filter that deadbands each input
     */
    public static class Deadband extends LambdaFilter {
        /**
         * Makes filter that deadbands values within window
         * @param window deadband window
         */
        public Deadband(double window) {
            super((x) -> InputScaler.deadband(x, window));
        }
    }

    /**
     * Stream Filter that squares each input
     */
    public static class Square extends LambdaFilter {
        /**
         * Makes filter that squares every value
         */
        public Square() {
            super((x) -> InputScaler.square(x));
        }
    }

    /**
     * Stream Filter that cubes each input
     */
    public static class Cube extends LambdaFilter {
        /**
         * Makes filter that cubes every value
         */
        public Cube() {
            super((x) -> InputScaler.cube(x));
        }
    }

    /**
     * Stream Filter that raises each input to a power
     */
    public static class Pow extends LambdaFilter {
        /**
         * @param power the power that the filter raises inputs to
         */
        public Pow(double power) {
            super((x) -> InputScaler.pow(x, power));
        }
    }

    /**
     * Stream Filter that uses circular 
     * algorithm to filter each input
     */
    public static class Circular extends LambdaFilter {
        /**
         * Uses circular algorithm with p value of 2
         */
        public Circular() {
            super((x) -> InputScaler.circular(x));
        }

        /**
         * Uses circular algorithm with custom p value
         * @param p p value
         */
        public Circular(double p) {
            super((x) -> InputScaler.circular(x, p));
        }
    }
}