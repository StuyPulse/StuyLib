package com.stuypulse.stuylib.util.plot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.stuypulse.stuylib.streams.IStream;

public class TimeSeries extends Series {

    private List<Double> xValues;
    private List<Double> yValues;

    private final IStream stream;

    public TimeSeries(String name, IStream stream) {
        super(name);

        xValues = new ArrayList<>();
        yValues = new LinkedList<>();

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
    protected void update() {
        yValues.add(stream.get());
    }
}
