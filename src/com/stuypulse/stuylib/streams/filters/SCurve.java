package com.stuypulse.stuylib.streams.filters;

/**
 * A filter that takes in real time inputs and profiles it into an S Curve. This is done with time
 * dependant filters to make sure that no matter how many times you call it a second, the resulting
 * profile is the same.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class SCurve implements IStreamFilter {

    private IStreamFilter mRateLimit;
    private IStreamFilter mAverage;

    /**
     * Create an SCurve with accel and jerk settings
     *
     * @param accel amount acceleration profile can change in a second
     * @param jerk  the amount of the ramp that is S shaped [1 by default]
     */
    public SCurve(double accel, double jerk) {
        if(jerk < 0.0) {
            throw new IllegalArgumentException("jerk must be positive");
        }

        if(accel <= 0.0) {
            throw new IllegalArgumentException("accel speed must be positive");
        }

        mRateLimit = new TimedRateLimit(accel);
        mAverage = new TimedMovingAverage(jerk / accel);
    }

    /**
     * Create an SCurve with accel and jerk settings
     *
     * @param accel amount acceleration profile can change in a second
     */
    public SCurve(double accel) {
        this(accel, 1.0);
    }

    public double get(double next) {
        return mAverage.get(mRateLimit.get(next));
    }
}
