// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.


package com.stuypulse.stuylib.util.chart;

import java.util.ArrayList;
import java.util.LinkedList;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Specialized structure for representing graph data.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class GraphData {

    private final String mGraphName;

    private final int mMaxLength;

    private final double mMinValue;
    private final double mMaxValue;

    private final LinkedList<Double> mValues;
    private final ArrayList<Double> mIndexes;

    /**
     * Create a Graph (no gui) with the following configuration
     *
     * @param name the name of the graph
     * @param maxLength the number of entries in the graph
     * @param min the lowest value of the graph
     * @param max the highest value of the graph
     */
    public GraphData(String name, int maxLength, double min, double max) {
        mGraphName = name;
        mMaxLength = maxLength;
        mMinValue = Math.min(min, max);
        mMaxValue = Math.max(min, max);

        mValues = new LinkedList<>();
        mIndexes = new ArrayList<>();

        while (mValues.size() < mMaxLength) {
            mValues.addFirst(0.0);
        }

        for (int i = 0; i < mMaxLength; ++i) {
            mIndexes.add(0.0 - i);
        }
    }

    /**
     * Create a Graph (no gui) with the following configuration
     *
     * @param other GraphData to copy from
     */
    public GraphData(GraphData other) {
        mGraphName = other.getName();
        mMaxLength = other.getLength();
        mMinValue = other.getMinValue();
        mMaxValue = other.getMaxValue();

        mValues = new LinkedList<>(other.mValues);
        mIndexes = new ArrayList<>();

        for (int i = 0; i < mMaxLength; ++i) {
            mIndexes.add(0.0 - i);
        }
    }

    // UPDATE GRAPH //
    /**
     * Add a value to the graph as the next entry
     *
     * @param val the next value for the graph
     */
    public void update(double val) {
        mValues.addFirst(val);

        while (mValues.size() < mMaxLength) {
            mValues.addFirst(0.0);
        }

        while (mValues.size() > mMaxLength) {
            mValues.removeLast();
        }
    }

    // GETTERS //
    /** @return name */
    public final String getName() {
        return mGraphName;
    }

    /** @return length */
    public final int getLength() {
        return mMaxLength;
    }

    /** @return minumum value */
    public final double getMinValue() {
        return mMinValue;
    }

    /** @return maximum value */
    public final double getMaxValue() {
        return mMaxValue;
    }

    /**
     * Get a value from the buffer of graph data
     *
     * @param delta how many entries you want to go back
     * @return the value in the buffer
     */
    public final double getValue(int delta) {
        return mValues.get(delta);
    }

    /**
     * Draw the Graph to an XYChart
     *
     * @param chart XYChart to draw to
     */
    public final void updateXYChart(XYChart chart) {
        final LinkedList<Double> values = new LinkedList<Double>(this.mValues);
        if (chart.getSeriesMap().containsKey(this.getName())) {
            chart.updateXYSeries(this.getName(), this.mIndexes, values, null);
        } else {
            chart.addSeries(this.getName(), this.mIndexes, values);
            chart.getSeriesMap().get(this.getName()).setMarker(SeriesMarkers.NONE);
        }
    }
}
