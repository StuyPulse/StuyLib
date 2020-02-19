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

    // CHART INSTANCE //
    private XYChart instance;

    // CHART DATA //
    private List<Double> xData;
    private List<Double> yData;

    // CHART SPECIFICATIONS //
    private int maxDataSize; // anything less than 0 means max is turned off

    private Double[] xBounds; // null is off
    private Double[] yBounds; // null is off

    // FRAME LISTENERS //
    private final MouseTracker mouseTracker;
    private final KeyTracker keyTracker;

    // CHART PANEL //
    private XChartPanel<XYChart> chartPanel;

    // this is protected for testing purposes, it could eventually change to public,
    // but some methods will null pointer exception
    // because the instance is chart
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

    public Chart(String title) {
        this(title, "x", "y");
    }

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

    public List<Double> getXData() {
        return xData;
    }

    public List<Double> getYData() {
        return yData;
    }

    public Chart addKeyBinding(int keyCode, KeyTracker.BindingFunction bindingFunction) {
        keyTracker.addBinding(keyCode, bindingFunction);
        return this;
    }

    public Chart display() {
        setVisible(true);
        return this;
    }

    public Chart undisplay() {
        setVisible(false);
        return this;
    }

    protected XYChart get() {
        return instance;
    }

    public String getChartTitle() {
        return get().getTitle();
    }

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

    public void update(double y) {
        // ADD DATA (INCREMENT BY ONE) //
        update(xData.get(xData.size() - 1) + 1, y);
    }

    public void reset(double x, double y) {
        // CLEAR DATA (AND SET FIRST POINT) //
        xData.clear();
        yData.clear();
        update(x, y);
    }

    public void reset() {
        // CLEAR DATA AND SET TO ORIGIN//
        reset(0, 0);
    }

    public int getMaxSize() {
        return maxDataSize;
    }

    public Chart setMaxSize(int max) {
        maxDataSize = max;
        return this;
    }

    public Double[] getXBounds() {
        return xBounds;
    }

    public Chart setXBounds(Double min, Double max) {
        instance.getStyler().setXAxisMin(min);
        instance.getStyler().setXAxisMax(max);

        yBounds[0] = min;
        yBounds[1] = max;

        return this;
    }

    public Chart resetXBounds() {
        return setXBounds(null, null);
    }

    public Double[] getYBounds() {
        return yBounds;
    }

    public Chart setYBounds(Double min, Double max) {
        instance.getStyler().setYAxisMin(min);
        instance.getStyler().setYAxisMax(max);

        yBounds[0] = min;
        yBounds[1] = max;

        return this;
    }

    public Chart resetYBounds() {
        return setYBounds(null, null);
    }

    public void redraw() {
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public double getMouseY() {
        return mouseTracker.getMouseY();
    }

    public double getMouseX() {
        return mouseTracker.getMouseX();
    }

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

    public static void main(String[] args) throws InterruptedException {
        Chart chart = new Chart("Title").setMaxSize(694).setYBounds(0.0, 1.0).setXBounds(0.0, 1.0).display();

        double y = 0;
        while (true) {
            y = SLMath.limit((chart.getMouseY() - 0.05) / 0.9, 0, 1);

            chart.update(y);

            chart.repaint();

            Thread.sleep(50);
        }
    }

}