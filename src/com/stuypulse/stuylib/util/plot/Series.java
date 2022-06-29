/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import java.awt.BasicStroke;
import java.util.List;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.markers.SeriesMarkers;

public abstract class Series {

    public static class Config {
        private String name;
        private int capacity;

        public Config(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }

        public String getName() {
            return name;
        }

        public int getCapacity() {
            return capacity;
        }
    }

    private final Config config;

    public Series(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public abstract int size();

    protected abstract List<Double> getSafeXValues();

    protected abstract List<Double> getSafeYValues();

    protected abstract void pop();

    protected abstract void poll();

    protected abstract boolean isPolling();

    private final void update() {
        final int capacity = getConfig().getCapacity();
        poll();
        while (size() > capacity) {
            pop();
        }
    }

    public final void update(XYChart chart) {
        update();

        String name = getConfig().getName();
        var x = getSafeXValues();
        var y = getSafeYValues();

        if (chart.getSeriesMap().containsKey(name)) {
            chart.updateXYSeries(name, x, y, null);
        } else {
            chart.addSeries(name, x, y);
            chart.getSeriesMap()
                    .get(name)
                    .setXYSeriesRenderStyle(XYSeriesRenderStyle.Line)
                    .setMarker(SeriesMarkers.NONE)
                    .setLineStyle(new BasicStroke(2.5f));
        }
    }
}
