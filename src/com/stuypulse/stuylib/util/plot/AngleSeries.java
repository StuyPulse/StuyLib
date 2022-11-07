/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.angles.AStream;

import java.util.LinkedList;
import java.util.List;

/**
 * An AngleSeries is used to plot a stream of points (AStream) that changes over time.
 *
 * @author Benjamin Goldfisher (ben@goldfisher.com)
 */
public class AngleSeries extends Series {

    /** Contains the (x, y) data points */
    private List<Double> xValues;

    private List<Double> yValues;

    /** Outputs values to be plotted */
    private final AStream stream;

    /** Magnitude of points to be plotted */
    private final double magnitude;

    /**
     * Creates an AngleSeries and specifies that it is polling.
     *
     * @param config series config
     * @param stream determines the points to be plotted
     * @param magnitude magnitude of points
     */
    public AngleSeries(Config config, AStream stream, double magnitude) {
        super(config, true);

        xValues = new LinkedList<>();
        yValues = new LinkedList<>();

        this.stream = stream;
        this.magnitude = magnitude;
    }

    /**
     * @return copied list of x values
     */
    @Override
    protected List<Double> getSafeXValues() {
        return new LinkedList<>(xValues);
    }

    /**
     * @return copied list of y values
     */
    @Override
    protected List<Double> getSafeYValues() {
        return new LinkedList<>(yValues);
    }

    /** Converts next angle from stream and adds to x and y values */
    @Override
    protected void poll() {
        Vector2D next = stream.get().getVector().mul(magnitude);

        xValues.add(next.x);
        yValues.add(next.y);
    }

    /** Removes oldest point from x and y values */
    @Override
    protected void pop() {
        xValues.remove(0);
        yValues.remove(0);
    }

    /**
     * @return number of stored points
     */
    @Override
    public int size() {
        return yValues.size();
    }
}
