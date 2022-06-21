/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.streams.IStream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TimeSeries extends Series {

    private List<Double> xValues;
    private List<Double> yValues;

    private final IStream stream;

    public TimeSeries(Config config, IStream stream) {
        super(config);

        xValues = new ArrayList<>();
        yValues = new LinkedList<>();
        for (int i = 0; i < config.getDuration(); ++i) {
            xValues.add((i * 1.0) / config.getDuration());
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
}
