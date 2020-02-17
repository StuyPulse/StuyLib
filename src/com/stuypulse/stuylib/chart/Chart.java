package com.stuypulse.stuylib.chart;

import com.stuypulse.stuylib.exception.ConstructionError;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

public class Chart {

    private final int binding;
    private XYChart instance;

    public XYChart get() {
        return instance;
    }

    public int getBinding() {
        return binding;
    }

    public Chart(String title, String x, String y, int e) {
        if (e < 0) {
            throw new ConstructionError("Chart(String title, String x, String y, int e)","Binding must not be less than 0");
        }
        instance = QuickChart.getChart(title,x,y,title, new double[] {0.0},new double[] {0.0});
        binding = e;
    }

    public Chart(String title, String x, String y) {
        this(title,x,y,-1);
    }

}