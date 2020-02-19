package com.stuypulse.stuylib.util.chart;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Basic single session chart for a chart group.
 * 
 * @author Myles Pasetsky (selym3)
 * @author Sam Belliveau (sexy gamer)
 */
public class Chart {

    /**
     * TODO: creat NChart with N-number of series create a chart that stores an
     * IStream
     * 
     * reset should take in a value to set the default value
     * 
     * migght be cool to have a function to get specific coordinates (X,Y) returns
     * new double[] {};
     */

    private XYChart instance;
    protected List<Double> xData;
    protected List<Double> yData;
    private int maxSize;

    /**
     * Get the XYChart
     * 
     * @return Singleton XYChart
     */
    protected XYChart get() {
        return instance; // used in ChartGroup to add the XYChart to a chart panel
    }

    /**
     * Increment x by 1 and append y data with a given value
     * 
     * @param y value to update y data with
     */
    public void update(double y) {
        update(xData.get(xData.size() - 1) + 1, y);
    }

    /**
     * Append a (x,y)
     * 
     * @param x value to update x data with (x values can repeat)
     * @param y value to update y data with
     */
    public void update(double x, double y) {
        xData.add(x);
        yData.add(y);

        if (get() != null) {
            get().updateXYSeries(get().getTitle(), xData, yData, null);
        }

        if (maxSize > 0) { // If a maxSize is set
            while (xData.size() > maxSize) {
                xData.remove(0);
            }

            while (yData.size() > maxSize) {
                yData.remove(0);
            }
        }
    }

    /**
     * Clear x data and y data and set them to a default (x,y) coordinate (to
     * prevent errors)
     * 
     * @param x x value of default coordinate
     * @param y y value of default coordiante
     */
    public void reset(double x, double y) {
        xData.clear();
        yData.clear();
        update(x, y);
    }

    /**
     * Clear x data and y data and set them to a default (0,0)
     * 
     * @param x x value of default coordinate
     * @param y y value of default coordiante
     */
    public void reset() {
        reset(0, 0);
    }

    /**
     * Set max size.
     * 
     * @param maxSize Set to -1 for no max size.
     */
    public Chart setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    protected Chart setYBounds(double minY, double maxY) {
        instance.getStyler().setYAxisMin(minY);
        instance.getStyler().setYAxisMax(maxY);
        return this;
    }

    protected Chart setXBounds(double minX, double maxX) {
        instance.getStyler().setXAxisMin(minX);
        instance.getStyler().setXAxisMax(maxX);
        return this;
    }

    // toString for testing
    public String toString() {
        String xVals = "X: ";
        String yVals = "Y: ";

        for (double x : xData) {
            xVals += x + ", ";
        }
        for (double y : yData) {
            yVals += y + ", ";
        }

        return xVals + "\n" + yVals;
    }

    /**
     * Initialize chart data, set maxSize to -1, and set default coordinate to (0,0)
     */
    public Chart(String title, String x, String y) {
        xData = new CopyOnWriteArrayList<Double>();
        yData = new CopyOnWriteArrayList<Double>();
        maxSize = -1;
        reset();
        
        instance = new XYChartBuilder().title(title).xAxisTitle(x).yAxisTitle(y).build();

        instance.addSeries(title, xData, yData);
        instance.getSeriesMap().get(title).setMarker(SeriesMarkers.NONE);
    }

}