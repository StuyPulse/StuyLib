package com.stuypulse.stuylib.chart;

import com.stuypulse.stuylib.exception.ConstructionError;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

public class Chart {

    private XYChart instance;

    protected XYChart get() {
        return instance;
    }

    public Chart(String title, String x, String y) {
        instance = QuickChart.getChart(title,x,y,title, new double[] {0.0},new double[] {0.0});
    }

}