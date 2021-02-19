// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

package com.stuypulse.stuylib.streams.filters;

import com.stuypulse.stuylib.util.StopWatch;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A Simple Moving Average where instead of averaging the past x values, you average all the values
 * given in the last x seconds.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class TimedMovingAverage implements IFilter {

    /** Class used to store value and time */
    private static class Value {

        public final double value;
        public final double time;

        public Value(double value, double time) {
            this.value = value * time;
            this.time = time;
        }
    }

    // All information needed to calculate value
    private StopWatch mTimer;
    private Number mMaxTime;
    private double mCurrentTime;
    private Queue<Value> mValues;
    private double mTotal;

    /**
     * Make Timed Moving Average with time span
     *
     * @param time time span for which to average
     */
    public TimedMovingAverage(Number time) {
        if (time.doubleValue() <= 0) {
            throw new IllegalArgumentException("time must be > 0");
        }

        mTimer = new StopWatch();
        mMaxTime = time;
        mCurrentTime = 0.0;
        mValues = new LinkedList<>();
        mTotal = 0.0;
    }

    private void add(double next) {
        final Value val = new Value(next, mTimer.reset());
        mTotal += val.value;
        mCurrentTime += val.time;
        mValues.add(val);
    }

    private void remove() {
        final Value val = mValues.remove();
        mTotal -= val.value;
        mCurrentTime -= val.time;
    }

    public double get(double next) {
        while (mMaxTime.doubleValue() < mCurrentTime) {
            remove();
        }

        add(next);

        if (mCurrentTime <= 0) {
            return 0.0;
        } else {
            return mTotal / mCurrentTime;
        }
    }
}
