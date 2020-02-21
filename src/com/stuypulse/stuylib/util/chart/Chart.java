package com.stuypulse.stuylib.util.chart;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

import com.stuypulse.stuylib.math.SLMath;

import java.awt.*;

import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Basic single session chart for a chart group.
 * 
 * @author Myles Pasetsky (@selym3)
 */
public class Chart extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * The instance of the XYChart
     */
    private XYChart instance;

    /**
     * CopyOnWriteArrayList for tracking x data
     */
    private List<Double> xData;

    /**
     * CopyOnWriteArrayList for y data
     */
    private List<Double> yData;

    /**
     * Stores the max number of ordered pairs
     * 
     * Anything less than 0 turns off max size
     */
    private int maxDataSize;

    /**
     * Stores the x boundaries of the chart in an array.
     * Acts to scale the chart.
     */
    private Double[] xBounds;

    /**
     * Stores the y boundaries of the chart in an array.
     * Acts to scale the chart.
     */
    private Double[] yBounds; 

    /**
     * Mouse tracker for getting mouse x and y as a
     * percentage of the screen width and height.
     */
    private final MouseTracker mouseTracker;

    /**
     * Key tracker with built in escape function and
     * adding key binding functionality. Gets key presses
     * as booleans.
     */
    private final KeyTracker keyTracker;

    /**
     * Swing panel to store the chart.
     */
    private XChartPanel<XYChart> chartPanel;

    /**
     * Default constructor that creates an empty frame.
     */
    protected Chart() {
        super();

        // DEFAULTS (not set yet) //

        chartPanel = null;
        instance = null;

        // LISTENER, DATA, & FRAME //

        xData = new CopyOnWriteArrayList<Double>();
        yData = new CopyOnWriteArrayList<Double>();

        keyTracker = new KeyTracker();
        mouseTracker = new MouseTracker(this);

        xBounds = new Double[] { null, null };
        yBounds = new Double[] { null, null };

        maxDataSize = -1;

        // reset(); when reset is called in the constructor for chart group it breaks
        // because
        // it's list of charts is defined afterwards. Should be careful w/ calling
        // methods in
        // the constructor. Replace with setting to 0, like how xBounds & yBounds are
        // set to null
        // by default.
        xData.add(0.0);
        yData.add(0.0);

        getContentPane().setPreferredSize(new Dimension(694, 694));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(keyTracker);

        setResizable(true);
        pack();
        // don't set visible by default
    }

    /**
     * Conveience chart with default 
     * x and y axes.
     * @param title Title of the chart
     */
    public Chart(String title) {
        this(title, "x", "y");
    }

    /**
     * Full constructor for creating a chart.
     * 
     * @param title Title of the chart
     * @param x x-axis name
     * @param y y-axis name
     */
    public Chart(String title, String x, String y) {
        this();

        instance = new XYChartBuilder().title(title).xAxisTitle(x).yAxisTitle(y).build();
        instance.addSeries(title, xData, yData);
        instance.getSeriesMap().get(title).setMarker(SeriesMarkers.NONE);

        // CHART PANEL //
        chartPanel = new XChartPanel<XYChart>(get());

        // CHART PANEL SPECIFICATIONS //
        chartPanel.setName(getChartTitle());

        // FRAME LISTENER //
        chartPanel.addMouseMotionListener(mouseTracker);

        getContentPane().add(chartPanel);

    }

    /**
     * Get x data.
     * 
     * @return x data list
     */
    public List<Double> getXData() {
        return xData;
    }

    /**
     * Get y data.
     * 
     * @return y data list
     */
    public List<Double> getYData() {
        return yData;
    }

    /**
     * Add key binding to chart's key tracker
     */
    public Chart addKeyBinding(int keyCode, KeyTracker.BindingFunction bindingFunction) {
        keyTracker.addBinding(keyCode, bindingFunction);
        return this;
    }

    /**
     * Show the chart.
     */
    public Chart display() {
        setVisible(true);
        return this;
    }

    /**
     * Hide the chart.
     */
    public Chart undisplay() {
        setVisible(false);
        return this;
    }

    /**
     * Get the XYChart instance.
     */
    protected XYChart get() {
        return instance;
    }

    /**
     * Return chart title. Title is also session name.
     */
    public String getChartTitle() {
        return get().getTitle();
    }

    /**
     * Add a new ordered pair to the x-data and y-data.
     */
    public void update(double x, double y) {
        // ADD DATA //
        xData.add(x);
        yData.add(y);

        if (get() != null) {
            get().updateXYSeries(get().getTitle(), xData, yData, null);
        }

        // REMOVE DATA (IF OVER MAX LIMIT) //
        if (getMaxSize() > 0) {
            while (xData.size() > getMaxSize()) {
                xData.remove(0);
            }

            while (yData.size() > getMaxSize()) {
                yData.remove(0);
            }
        }
    }
    
    /**
     * Add y-data and increment x by 1.
     */
    public void update(double y) {
        update(xData.get(xData.size() - 1) + 1, y);
    }

    /**
     * Clears x and y data.
     * Reset x-data and y-data to a default value.
     */
    public void reset(double x, double y) {
        // CLEAR DATA (AND SET FIRST POINT) //
        xData.clear();
        yData.clear();
        update(x, y);
    }

    /**
     * Clears x and y data, and sets x and y
     * to a default 0,0.
     */
    public void reset() {
        // CLEAR DATA AND SET TO ORIGIN//
        reset(0, 0);
    }

    /**
     * Return the maximum number of (x,y) entries
     */
    public int getMaxSize() {
        return maxDataSize;
    }

    /**
     * Set the maximum number of (x,y) entries
     */
    public Chart setMaxSize(int max) {
        maxDataSize = max;
        return this;
    }

    /**
     * Return x bounds of the chart as an array.
     */
    public Double[] getXBounds() {
        return xBounds;
    }

    /**
     * Set x boundaries of the chart.
     * 
     * @param min minimum x value
     * @param max maximum x value
     */
    public Chart setXBounds(Double min, Double max) {
        instance.getStyler().setXAxisMin(min);
        instance.getStyler().setXAxisMax(max);

        yBounds[0] = min;
        yBounds[1] = max;

        return this;
    }

    /**
     * Remove x boundaries of the graph.
     */
    public Chart resetXBounds() {
        return setXBounds(null, null);
    }

    /**
     * Return y bounds as an array.
     */
    public Double[] getYBounds() {
        return yBounds;
    }

    /**
     * Set y boundaries of the chart.
     * 
     * @param min minimum y value
     * @param max maximum y value
     */
    public Chart setYBounds(Double min, Double max) {
        instance.getStyler().setYAxisMin(min);
        instance.getStyler().setYAxisMax(max);

        yBounds[0] = min;
        yBounds[1] = max;

        return this;
    }

    /**
     * Remove y boundaries of the chart.
     */
    public Chart resetYBounds() {
        return setYBounds(null, null);
    }

    /**
     * Revalidate and repaint panel.
     * Necessary for real-time graphing.
     */
    public void redraw() {
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    /**
     * Gets the mouse position as
     * a percentage of the height
     */
    public double getMouseY() {
        return mouseTracker.getMouseY();
    }

    /**
     * Gets the mouse position as
     * a percentage of the width
     */
    public double getMouseX() {
        return mouseTracker.getMouseX();
    }

    /**
     * Gets the boolean value of
     * a key
     */
    public boolean getKey(int e) {
        return keyTracker.getKey(e);
    }

    @Override
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

}