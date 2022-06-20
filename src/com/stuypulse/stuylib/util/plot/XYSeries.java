package com.stuypulse.stuylib.util.plot;

import java.util.LinkedList;
import java.util.List;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.vectors.VStream;

public class XYSeries extends Series {

    private List<Double> xValues;
    private List<Double> yValues;

    private final VStream stream;

    public XYSeries(Config config, VStream stream) {
        super(config);

        xValues = new LinkedList<>();
        yValues = new LinkedList<>();

        this.stream = stream;
    }

    @Override
    protected List<Double> getSafeXValues() {
        return new LinkedList<>(xValues);
    }

    @Override
    protected List<Double> getSafeYValues() {
        return new LinkedList<>(yValues);
    }

    @Override
    protected void poll() {
        Vector2D next = stream.get();
        xValues.add(next.x);
        yValues.add(next.y);
    }

    @Override
    protected void pop() {
        xValues.remove(0);
        yValues.remove(0);
    }

    @Override
    public int size() {
        return yValues.size();
    }
    
}