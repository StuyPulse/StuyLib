package com.stuypulse.stuylib.util.chart;

import java.util.LinkedList;

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
    private LinkedList<Double> xData;
    private LinkedList<Double> yData;

    protected XYChart get() {
        return instance;
    }

    protected void update(double y) {
        update(xData.getLast() + 1,y);
    }

    protected void update(double x, double y) {
        xData.add(x);
        yData.add(y);

        get().updateXYSeries(get().getTitle(), getXData(), getYData(), null);
    }

    protected LinkedList<Double> getXData() {
        return xData;
    }

    protected LinkedList<Double> getYData() {
        return yData;
    }

    protected void setXData(LinkedList<Double> newData) {
        xData = newData;
    }

    protected void setYData(LinkedList<Double> newData) {
        yData = newData;
    }

    protected void setData(LinkedList<Double> xData,LinkedList<Double> yData) {
        setXData(xData);
        setYData(yData);
    }

    protected void reset() {
        xData = new LinkedList<Double>();
        yData = new LinkedList<Double>();
        xData.add(0.0);
        yData.add(0.0);
    }

    public Chart(String title, String x, String y) {
        reset();
        instance = QuickChart.getChart(title,x,y, title, xData, yData);
    }

}