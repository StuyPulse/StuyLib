package com.stuypulse.stuylib.util.plot;

import java.util.ArrayList;
import java.util.List;

import com.stuypulse.stuylib.streams.filters.IFilter;

/**
 * 
 * @author Ben Goldfisher 
 */
public class FuncSeries extends Series {

    public static class Domain {
        public final double min, max;
        public Domain(double min, double max) {
            this.min = min; 
            this.max = max;
        }
    }

    private List<Double> xValues;
    private List<Double> yValues;
    
    public FuncSeries(Config config, Domain domain, IFilter func) {
        super(config);

        xValues = new ArrayList<Double>();
        yValues = new ArrayList<Double>();

        for (int i = 0; i < config.getCapacity(); i++) {
            double x = (i * (domain.max - domain.min)) / config.getCapacity() + domain.min;

            xValues.add(x);
            yValues.add(func.get(x));
        }
    }

    @Override
    public int size() {
        return getConfig().getCapacity();
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
