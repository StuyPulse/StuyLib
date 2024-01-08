/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.numbers.filters;

import com.stuypulse.stuylib.util.StopWatch;

import java.util.LinkedList;

/**
 * A Simple Moving Average where instead of averaging the past x values, you average all the values
 * given in the last x seconds.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class TimedMovingAverage implements IFilter {

    /**
     * How many removals the moving average can do before it does a complete recount for better
     * accuracy.
     *
     * <p>This is a trade off between speed and accuracy. Having this at 512 means a complete
     * recount ~ every 10 seconds on robot code.
     */
    private static final int kMaxRemovalsBeforeReset = 512;

    /** Class used to store value and time */
    private static class Value {

        public double value;
        public double time;

        public Value(double value, double time) {
            this.value = value;
            this.time = time;
        }

        public double truncate(double t) {
            time -= t;
            return value * t;
        }

        public double sum() {
            return value * time;
        }
    }

    // All information needed to calculate value
    private Number mMaxTime;

    private StopWatch mTimer;

    private LinkedList<Value> mValues;
    private double mCurrentTime;
    private double mTotal;

    private int mRemovals;

    /**
     * Make Timed Moving Average with time span
     *
     * @param time time span for which to average
     */
    public TimedMovingAverage(Number time) {
        if (time.doubleValue() <= 0) {
            throw new IllegalArgumentException("time must be > 0");
        }

        mMaxTime = time;

        mTimer = new StopWatch();

        mValues = new LinkedList<>();
        mCurrentTime = 0.0;
        mTotal = 0.0;

        mRemovals = 0;
    }

    /**
     * Does a recount of the entire moving average to make sure that floating point errors don't
     * build up over long periods of time.
     */
    private void reset() {
        mCurrentTime = 0.0;
        mTotal = 0.0;
        mRemovals = 0;

        for (final Value val : mValues) {
            mCurrentTime += val.time;
            mTotal += val.sum();
        }
    }

    /**
     * Remove entries from the list
     *
     * @param t the amount of time that needs to be removed
     * @return if this should be the last removal
     */
    private boolean remove(double t) {
        // Redo the count if there have been too many removals
        if (kMaxRemovalsBeforeReset < ++mRemovals) {
            reset();
            return false;
        }

        // If the list is empty or we have negative t we should reset
        if (mValues.isEmpty() || t <= 0.0) {
            reset();
            return true;
        }

        // get the value that we should be removing
        final Value val = mValues.getFirst();

        // if we can remove the entire entry, do that,
        // but that means we are not done
        if (val.time <= t) {
            mValues.removeFirst();
            mTotal -= val.sum();
            mCurrentTime -= val.time;
            return false;
        }

        // This partially removes something from the list
        // this allows us to sample over an exact amount of time
        else {
            mTotal -= val.truncate(t);
            mCurrentTime -= t;
            return true;
        }
    }

    public double get(double next) {
        // Add new value to running average
        final Value val = new Value(next, mTimer.reset());
        mValues.addLast(val);
        mCurrentTime += val.time;
        mTotal += val.sum();

        // Remove values until our time is consistent
        while (!remove(mCurrentTime - mMaxTime.doubleValue())) {}

        // Return running average value
        if (mCurrentTime <= 0) {
            return next;
        } else {
            return mTotal / mCurrentTime;
        }
    }
}
