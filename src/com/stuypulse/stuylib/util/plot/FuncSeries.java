package com.stuypulse.stuylib.util.plot;

import java.util.ArrayList;
import java.util.List;

import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.util.plot.TimeSeries.TimeSpan;

/**
 * 
 * @author Ben Goldfisher 
 */
public class FuncSeries extends Series {

    private List<Double> xValues;
    private List<Double> yValues;
    
    public FuncSeries(Config config, TimeSpan time, IFilter func) {
        super(config);

        xValues = new ArrayList<Double>();
        yValues = new ArrayList<Double>();

        // Duration is the number of points
        for (int i = 0; i < config.getDuration(); i++) {
            double x = (i * (time.max - time.min)) / config.getDuration() + time.min;

            xValues.add(x);
            yValues.add(func.get(x));
        }
    }

    @Override
    public int size() {
        return getConfig().getDuration();
    }

    @Override
    protected List<Double> getSafeXValues() {
        return xValues;
    }

    @Override
    protected List<Double> getSafeYValues() {
        return yValues;
    }

    @Override
    protected void pop() {}

    @Override
    protected void poll() {}

    @Override
    protected boolean isPolling() {
        return false;
    }

}
