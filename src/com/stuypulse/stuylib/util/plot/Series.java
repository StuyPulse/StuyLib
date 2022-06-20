package com.stuypulse.stuylib.util.plot;

import java.util.List;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;

public abstract class Series {
    
    private final String name;

    public Series(String name) {
        this.name = name;
    }

    protected abstract List<Double> getSafeXValues();
    protected abstract List<Double> getSafeYValues();

    protected abstract void update();

    protected final void update(XYChart chart) {
        var x = getSafeXValues();
        var y = getSafeYValues();

        if (chart.getSeriesMap().containsKey(name)) {
            chart.updateXYSeries(name, x, y, null);
        } else {
            chart.addSeries(name, x, y);
            chart.getSeriesMap().get(name).setMarker(SeriesMarkers.NONE);
        }

        update();
    }

}
