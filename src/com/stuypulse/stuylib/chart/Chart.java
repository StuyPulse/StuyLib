package com.stuypulse.stuylib.chart;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

public class Chart {

    /**
     * TODO:
     * add functionality to reset adn replace xData and yData with custom lists
     * reset xData and yData
     * creat NChart with N-number of series
     */

    private XYChart instance;
    private List<Double> xData;
    private List<Double> yData;

    protected XYChart get() {
        return instance;
    }

    protected void update(double x, double y) {
        xData.add(x);
        yData.add(y);
    }

    protected List<Double> getXData() {
        return xData;
    }

    protected List<Double> getYData() {
        return yData;
    }

    public Chart(String title, String x, String y) {
        xData = new ArrayList<Double>();
        yData = new ArrayList<Double>();
        xData.add(0.0);
        yData.add(0.0);
        instance = QuickChart.getChart(title,x,y, title, xData, yData);
    }

}