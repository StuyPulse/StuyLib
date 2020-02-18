package com.stuypulse.stuylib.util.chart;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

/**
 * @author Myles Pasetsky (selym3) 
 * @author Sam Belliveau (sexy gamer)
 */
public class Chart {

    /**
     * TODO:
     * creat NChart with N-number of series
     */

    private XYChart instance;
    private List<Double> xData;
    private List<Double> yData;
    private int maxSize;

    protected XYChart get() {
        return instance;
    }

    public void update(double y) {
        update(xData.get(xData.size() - 1) + 1,y);
    }

    public void update(double x, double y) {
        xData.add(x);
        yData.add(y);
        
        if(get() != null) {
            get().updateXYSeries(get().getTitle(), xData, yData, null);
        }

        if(maxSize > 0) { // If a maxSize is set
            while(xData.size() > maxSize) {
                xData.remove(0);
            }

            while(yData.size() > maxSize) {
                yData.remove(0);
            }
        }
    }

    public void reset() {
        xData.clear();
        yData.clear();
        update(0.0, 0.0);
    }

    public Chart setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }
    
    public Chart(String title, String x, String y) {
        xData = new CopyOnWriteArrayList<Double>();
        yData = new CopyOnWriteArrayList<Double>();
        maxSize = -1;
        reset();
        instance = QuickChart.getChart(title, x, y, title, xData, yData);
    }

}