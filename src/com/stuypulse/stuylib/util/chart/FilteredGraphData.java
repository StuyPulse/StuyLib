/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.streams.filters.IFilter;

/**
 * Graph data with a filter.
 *
 * @author Sam (sam.belliveau@gmail.com)
 * @author Myles Pasetsky (@selym3)
 */
public class FilteredGraphData extends GraphData {

    private IFilter mFilter;

    /**
     * Create a Graph (no gui) with the following configuration
     *
     * @param graph graph configuration to make a filtered graph from
     * @param filter filter to filter all the values through
     */
    public FilteredGraphData(GraphData graph, IFilter filter) {
        super(graph);
        mFilter = filter;
    }

    /**
     * Add a value to the graph as the next filtered entry
     *
     * @param val the next value for the graph
     */
    @Override
    public void update(double val) {
        super.update(mFilter.get(val));
    }
}
