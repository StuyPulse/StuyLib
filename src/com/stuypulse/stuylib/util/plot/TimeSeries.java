/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.streams.IStream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TimeSeries extends Series {

    public static class TimeSpan {
        public final double min, max;

        public TimeSpan(double min, double max) {
            this.min = min;
            this.max = max;
        }
    }

    private List<Double> xValues;
    private List<Double> yValues;

    private final IStream stream;

    public TimeSeries(Config config, TimeSpan span, IStream stream) {
        super(config);

        xValues = new ArrayList<>();
        yValues = new LinkedList<>();
        
        final double delta = (span.max - span.min) / config.getCapacity();
        for (int i = 0; i < config.getCapacity(); ++i) {
            xValues.add(span.min + i * delta);
            yValues.add(0.0);
        }

        this.stream = stream;
    }

    @Override
    protected List<Double> getSafeXValues() {
        return xValues;
    }

    @Override
    protected List<Double> getSafeYValues() {
        return new LinkedList<>(yValues);
    }

    @Override
    protected void poll() {
        yValues.add(stream.get());
    }

    @Override
    protected void pop() {
        yValues.remove(0);
    }

    @Override
    public int size() {
        return yValues.size();
    }

    @Override
    protected boolean isPolling() {
        return true;
    }
}
