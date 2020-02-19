package com.stuypulse.stuylib.util.chart;

import java.awt.event.*;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.stuypulse.stuylib.exception.ConstructionError;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.filters.HullMovingAverage;
import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.streams.filters.InvertedHullMovingAverage;
import com.stuypulse.stuylib.streams.filters.OrderedLowPassFilter;
import com.stuypulse.stuylib.streams.filters.RateLimit;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

/**
 * @author Myles Pasetsky (selym3)
 */
public class ChartGroup extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int MAX_SIZE = 10;

    private List<Chart> charts;
    private List<XChartPanel<XYChart>> chartPanels; // store chart panels for convenience in repainting

    private final CardLayout cardLayout;
    
    private final KeyTracker keyTracker;
    private final MouseTracker mouseTracker;

    private double[] xBounds;
    private double[] yBounds;

    // CONSTRUCTORS

    /**
     * Initialize lists, frame, and cardlayout. Doesn't require any charts.
     */
    public ChartGroup() {
        super("Chart Collection"); // change and update as you switch cards

        // If bounds are == to each other and == -1, ignore the bounds
        xBounds = new double[] {0,0};
        yBounds = new double[] {0,0};

        charts = new ArrayList<Chart>();
        chartPanels = new ArrayList<XChartPanel<XYChart>>();

        cardLayout = new CardLayout();
        
        getContentPane().setLayout(cardLayout);
        getContentPane().setPreferredSize(new Dimension(694, 694));

        keyTracker = new KeyTracker(this);
        addKeyListener(keyTracker);

        // UTILITY MOUSE TRACKER FOR GETTING THE X AND Y AS A PERCENTAGE OF THE WIDTH AND HEIGHT, ADD TO EACH
        mouseTracker = new MouseTracker(getContentPane());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(true);
        pack();
        setVisible(true);
    }

    /**
     * Creates a quick wrapper with some charts.
     * 
     * @param newCharts An array of charts
     */
    public ChartGroup(Chart... newCharts) {
        this();
        if (newCharts.length > MAX_SIZE) {
            throw new ConstructionError("ChartGroup(Chart... newCharts)", "Must be less than max size!");
        }
        add(newCharts);
    }

    // GETTERS / SETTERS

    public void addKey(int... e) {
        keyTracker.addKey(e);
    }

    public boolean getKey(int e) {
        return keyTracker.getKey(e);
    }

    protected CardLayout getCardLayout() {
        return cardLayout;
    }

    /**
     * Returns a panel with the given name. Returns null if not found
     * 
     * @param title Name of the component
     * @return Return the given JPanel if exists
     */
    private JPanel getPanel(String title) {
        for (int i = 0; i < getContentPane().getComponentCount(); i++) {
            if (getContentPane().getComponent(i).getName().equals(title)) {
                return (JPanel) getContentPane().getComponent(i);
            }
        }
        return null;
    }

    /**
     * Returns a panel with the given index. Returns null if not inbounds.
     * 
     * @param index index of the componenet
     * @return Return the given JPanel in bound
     */
    private JPanel getPanel(int index) {
        if (index > getContentPane().getComponents().length - 1 || index < 0)
            return null;
        return (JPanel) getContentPane().getComponent(index);
    }

    /**
     * Returns a Chart with the given title. Returns null if not found.
     * 
     * @param title title of the chart
     * @return Return the given Chart if exists
     */
    private Chart getChart(String title) {
        for (int i = 0; i < charts.size(); i++) {
            if (charts.get(i).get().getTitle().equals(title)) {
                return charts.get(i);
            }
        }
        return null;
    }

    /**
     * Returns a Chart with the given index. Returns null if out of bounds.
     * 
     * @param index index of the chart
     * @return Return the given Chart if in bound
     */
    private Chart getChart(int index) {
        if (index > charts.size() - 1 || index < 0) {
            return null;
        }
        return charts.get(index);
    }

    /**
     * Returns true if the chart is found
     * 
     * @param chart the chart to search for
     */
    private boolean containsChart(Chart chart) {
        return charts.indexOf(chart) >= 0;
    }

    /**
     * Returns true if the panel is found
     * 
     * @param chart the chart to search for
     */
    private boolean containsPanel(JPanel panel) {
        return Arrays.asList(getContentPane().getComponents()).indexOf(panel) >= 0;
    }

    // ADD CHART

    /**
     * Add charts and create Chart panels.
     * 
     * @param newCharts charts to add and create respective panels
     */
    public ChartGroup add(Chart... newCharts) {
        for (Chart newChart : newCharts) {
            charts.add(newChart);

            XChartPanel<XYChart> panel = new XChartPanel<XYChart>(newChart.get());
            panel.setName(newChart.get().getTitle());
            panel.addMouseMotionListener(mouseTracker);

            if (!(yBounds[0] == 0 && yBounds[1] == 0)) {
                newChart.setYBounds(yBounds[0], yBounds[1]);
            }
            if (!(xBounds[0] == 0 && xBounds[1] == 0)) {
                newChart.setXBounds(xBounds[0], xBounds[1]);
            }

            chartPanels.add(panel);
            getContentPane().add(panel, newChart.get().getTitle());
        }
        return this;
    }

    // CHART METHODS (repainting, resetting, etc)

    /**
     * Revalidates and repaints each chart's panel. 
     * Use for real-time updating
     */
    public void repaintAll() {
        for (XChartPanel<XYChart> chartPanel : chartPanels) {
            chartPanel.revalidate();
            chartPanel.repaint();
        }
    }

    /**
     * Uses chart's reset function to reset each chart's
     * values to only 0,0 (default)
     */
    public void resetAll() {
        for (Chart chart : charts) {
            chart.reset();
        }
    }

    /**
     * Uses chart's reset function to reset each chart's
     * values to only 0,0
     */
    public void resetAll(double x, double y) {
        for (Chart chart : charts) {
            chart.reset(x,y);
        }
    }

    // append a value pair to all the charts
    public void updateAll(double x, double y) {
        for (Chart chart : charts) {
            chart.update(x,y);
        }
    }
    // append a y value to all charts and increment x by 1
    public void updateAll(double y) {
        for (Chart chart : charts) {
            chart.update(y);
        }
    }

    // CHART DISPLAY SETTINGS
    
    /**
     * Set X boundaries for all charts. Further charts added will
     * have the same boundaries. All boundaries can be set for individual
     * charts.
     */
    public ChartGroup setXBounds(double minX, double maxX) { 
        xBounds[0] = minX;
        xBounds[1] = maxX;
        
        for (Chart chart : charts) { 
            chart.setXBounds(minX, maxX);
        }
        return this;
    }

    /**
     * Remove X boundaries for all charts. Further charts will have
     * no boundaries. All boundaries can be set for individual
     * charts.
     */
    public ChartGroup resetXBounds() {
        xBounds[0] = 0;
        xBounds[1] = 0;

        for (Chart chart : charts) {
            chart.setXBounds(Double.MIN_VALUE, Double.MAX_VALUE);
        }
        return this;
    }

    /**
     * Set Y boundaries for all charts. Further charts added will
     * have the same boundaries. All boundaries can be set for individual
     * charts.
     */
    public ChartGroup setYBounds(double minY, double maxY) {
        yBounds[0] = minY;
        yBounds[1] = maxY;

        for (Chart chart : charts ) {
            chart.setYBounds(minY, maxY);
        }
        return this;
    }

    /**
     * Remove Y boundaries for all charts. Further charts will have
     * no boundaries. All boundaries can be set for individual
     * charts.
     */
    public ChartGroup resetYBounds() {
        yBounds[0] = 0;
        yBounds[1] = 0;

        for (Chart chart : charts) {
            chart.setYBounds(Double.MIN_VALUE, Double.MAX_VALUE);
        }
        return this;
    }

    // TEST toString function
    public String toString() {
        return getContentPane().getComponents().length + "\n" + charts.size();
    }

    public static void main(String[] args) throws InterruptedException {

        /**
         * TODO:
         * 
         * use concurrent Chart.xData and Chart.yData
         * 
         * check if a title exists already in QuickWrapper.charts because QuickChart.getChart is singleton (<- important)
         * 
         * which methods should return a QuickWrapper reference 
         * 
         * Potentially improve on keytracker (have it return booleans based on a given map of keycodes)
         * MouseTracker should get x and y position on the frame
         * 
         * NChart.java (refer to Chart)
         */


        final String x = "x";
        final String y = "y";

        IStreamFilter ihmaFilter = new InvertedHullMovingAverage(50);
        IStreamFilter othersFilter = new RateLimit(1.0 / 64.0);
        IStreamFilter hmaFilter = new HullMovingAverage(50);
        IStreamFilter ra1Filter = new OrderedLowPassFilter(0.5, 1);
        IStreamFilter ra2Filter = new OrderedLowPassFilter(0.5, 2);
        IStreamFilter ra3Filter = new OrderedLowPassFilter(0.5, 3);
        IStreamFilter ra4Filter = new OrderedLowPassFilter(0.5, 4);
        IStreamFilter ra5Filter = new OrderedLowPassFilter(0.5, 5);
        IStreamFilter ra6Filter = new OrderedLowPassFilter(0.5, 6);

        int m100ax = 200;

        Chart controlChart = new Chart("Control", x, y).setMaxSize(m100ax);
        Chart othersChart = new FilteredChart("Others", x, y,othersFilter).setMaxSize(m100ax);
        Chart ihmaChart = new FilteredChart("IHMA", x, y,ihmaFilter).setMaxSize(m100ax);
        Chart hmaChart = new FilteredChart("HMA", x, y, hmaFilter).setMaxSize(m100ax);
        Chart RA1Chart = new FilteredChart("RA1", x, y, ra1Filter).setMaxSize(m100ax);
        Chart RA2Chart = new FilteredChart("RA2", x, y, ra2Filter).setMaxSize(m100ax);
        Chart RA3Chart = new FilteredChart("RA3", x, y, ra3Filter).setMaxSize(m100ax);
        Chart RA4Chart = new FilteredChart("RA4", x, y, ra4Filter).setMaxSize(m100ax);
        Chart RA5Chart = new FilteredChart("RA5", x, y, ra5Filter).setMaxSize(m100ax);
        Chart RA6Chart = new FilteredChart("RA6", x, y, ra6Filter).setMaxSize(m100ax);

        double min = 0;
        double max = 1;

        ChartGroup test = new ChartGroup(
            controlChart, 
            othersChart,
            RA1Chart,
            RA2Chart,
            RA3Chart,
            RA4Chart,
            RA5Chart,
            RA6Chart
        ).setYBounds(min, max).add(ihmaChart,hmaChart);

        test.addKey(KeyEvent.VK_SPACE);

        boolean previousValue = test.getKey(KeyEvent.VK_SPACE);
        boolean drawing = false;

        // hmaChart.setYBounds(min, 1.5);
        // ihmaChart.setYBounds(min,1.5);

        while(true) {

            double rNum = SLMath.limit(((test.mouseTracker.getMouseY() - 0.05) / 0.9), 0, 1);

            // this returns true onyl when the space key is pressed the first time (and not held down)
            if (test.getKey(KeyEvent.VK_SPACE) && test.getKey(KeyEvent.VK_SPACE) != previousValue) {
                drawing = !drawing;
            }

            // CLICK SPACE TO TOGGLE DRAWING
            if(drawing) {
                test.updateAll(rNum);
            }

            test.repaintAll();
            
            previousValue = test.getKey(KeyEvent.VK_SPACE);

            Thread.sleep(20);
        }

    }
}