package com.stuypulse.stuylib.util.plot;

import java.util.LinkedList;
import java.util.List;

public class XYSeries extends Series {

    private List<Double> xValues;
    private List<Double> yValues;

    public XYSeries(String name) {
        super(name);

        xValues = new LinkedList<>();
        yValues = new LinkedList<>();
    }

    @Override
    protected List<Double> getSafeXValues() {
        return null;
    }

    @Override
    protected List<Double> getSafeYValues() {
        return null;
    }

    @Override
    protected void update() {
        
    }
    
}
