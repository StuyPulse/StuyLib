package com.stuypulse.stuylib.util.plot;

import java.util.ArrayList;
import java.util.List;

import com.stuypulse.stuylib.streams.filters.IFilter;

// Doesnt use duration
public class FuncSeries extends Series {

    private List<Double> xValues;
    private List<Double> yValues;
    
    public FuncSeries(IFilter func, Config config, double left, double right) {
        super(config);

        xValues = new ArrayList<Double>();
        yValues = new ArrayList<Double>();

        // Duration is the number of points
        for (int i = 0; i < config.getDuration(); i++) {
            double x = (i * (right - left)) / config.getDuration() + left;

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

}
