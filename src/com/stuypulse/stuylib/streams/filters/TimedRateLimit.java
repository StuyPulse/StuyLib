package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.exception.ConstructionError;
import com.stuypulse.stuylib.math.SLMath;

/**
 * This class lets you rate limit a stream of inputs where the values are
 * limited based on time passed
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class TimedRateLimit implements IStreamFilter {

    /**
     * This is the default unit when no unit is provided. 1.0 was choosen as it is
     * most common for motor input to be givin on a scale of -1.0 - 1.0
     */
    public static final double kDefaultUnit = 1.0;

    private double mUnit; // Max change per givin time
    private long mTimePerUnit; // Stores how long it needs to take per unit [NANO]

    private double mLastValue; // Stores current value
    private long mLastTime; // Stores last time in nanoseconds

    /**
     * Makes a timed rate limit with a specified amount of time. This time denotes
     * how long it would take for the value to change by one unit.
     * 
     * @param time time for value to change by one unit (miliseconds)
     * @param unit the size of a unit in rate limit
     */
    public TimedRateLimit(long time, double unit) throws ConstructionError {
        if (time <= 0) {
            throw new ConstructionError("TimedRateLimit(long time, double unit)", "time must be greater than 0!");
        }

        if (unit <= 0.0) {
            throw new ConstructionError("TimedRateLimit(long time, double unit)", "unit must be greater than 0!");
        }

        mLastValue = 0;
        mLastTime = System.currentTimeMillis();

        mTimePerUnit = time;
        mUnit = unit;
    }

    /**
     * Makes a timed rate limit with a specified amount of time. This time denotes
     * how long it would take for the value to change by one unit. (1.0)
     * 
     * @param time time for value to change by one unit (miliseconds)
     */
    public TimedRateLimit(long time) {
        this(time, kDefaultUnit);
    }

    /**
     * Get the max amount that the filter will change based on the time since last
     * call
     * 
     * @return get the limit based on time since last call
     */
    private double getLimit() {
        long current = System.currentTimeMillis();
        double limit = (current - mLastTime) * (mUnit / mTimePerUnit);
        mLastTime = current;
        return limit;
    }

    public double get(double next) {
        return mLastValue += SLMath.limit(next - mLastValue, getLimit());
    }
}