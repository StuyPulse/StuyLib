package com.stuypulse.stuylib.util.plot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.stuypulse.stuylib.streams.IStream;

public class TimeSeries extends Series {

    private List<Double> xValues;
    private List<Double> yValues;

    private final IStream stream;

    public TimeSeries(Config config, IStream stream) {
        super(config);

        xValues = new ArrayList<>();
        yValues = new LinkedList<>();
        for (int i = 0; i < config.getDuration(); ++i) {
            xValues.add((i * 1.0)/config.getDuration());
            yValues.add(0.0);
        }

        this.stream = stream;
    }

    @Override
    protected List<Double> getSafeXValues() {
        return xValues;
    }

    @Override
    protected List<Double> getSafeYValues() {
        return new LinkedList<>(yValues);
    }

    @Override
    protected void poll() {
        yValues.add(stream.get());
    }

    @Override
    protected void pop() {
        yValues.remove(0);
    }

    @Override
    public int size() {
        return yValues.size();
    }
}
