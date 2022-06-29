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
        super(config, false);

        xValues = new ArrayList<Double>();
        yValues = new ArrayList<Double>();

        // Duration is the number of points
        for (int i = 0; i < config.getDuration(); i++) {
            double x = (i * (domain.max - domain.min)) / config.getDuration() + domain.min;

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
