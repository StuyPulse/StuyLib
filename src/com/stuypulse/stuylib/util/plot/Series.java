package com.stuypulse.stuylib.util.plot;

import java.util.List;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.vectors.VStream;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;

public abstract class Series {

    public static class Config {
        private String name;
        private int duration;

        public Config(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }

        public String getName() { return name; }
        public int getDuration() { return duration; }
    }
    
    public static final Series make(Config config, IStream stream) {
        return new TimeSeries(config, stream);
    }

    public static final Series make(Config config, VStream stream) {
        return new XYSeries(config, stream);
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

    private final void update() {
        final int duration = getConfig().getDuration(); 
        poll();
        while (size() > duration) {
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
            chart.getSeriesMap().get(name).setMarker(SeriesMarkers.NONE);
        }
    }

}