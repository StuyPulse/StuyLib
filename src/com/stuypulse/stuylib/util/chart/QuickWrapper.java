package com.stuypulse.stuylib.util.chart;

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
public class QuickWrapper extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int MAX_SIZE = 10;

    private List<Chart> charts;
    private List<XChartPanel<XYChart>> chartPanels; // store chart panels for convenience in repainting

    private final CardLayout cardLayout;
    
    private final KeyTracker keyTracker;
    private final MouseTracker mouseTracker;

    // CONSTRUCTORS

    /**
     * Initialize lists, frame, and cardlayout. Doesn't require any charts.
     */
    public QuickWrapper() {
        super("Chart Collection"); // change and update as you switch cards

        charts = new ArrayList<Chart>();
        chartPanels = new ArrayList<XChartPanel<XYChart>>();

        cardLayout = new CardLayout();
        
        getContentPane().setLayout(cardLayout);
        getContentPane().setPreferredSize(new Dimension(694, 694));

        keyTracker = new KeyTracker(this);
        addKeyListener(keyTracker);

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
    public QuickWrapper(Chart... newCharts) {
        this();
        if (newCharts.length > MAX_SIZE) {
            throw new ConstructionError("QuickWrapper(Chart... newCharts)", "Must be less than max size!");
        }
        add(newCharts);
    }

    // GETTERS

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
    public QuickWrapper add(Chart... newCharts) {
        for (Chart newChart : newCharts) {
            charts.add(newChart);

            XChartPanel<XYChart> panel = new XChartPanel<XYChart>(newChart.get());
            panel.setName(newChart.get().getTitle());
            panel.addMouseMotionListener(mouseTracker);

            chartPanels.add(panel);
            getContentPane().add(panel, newChart.get().getTitle());
        }
        return this;
    }

    // REPAINT

    public QuickWrapper updateCharts() {
        for (XChartPanel<XYChart> chartPanel : chartPanels) {
            chartPanel.revalidate();
            chartPanel.repaint();
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

        int m100ax = 694;

        Chart controlChart = new Chart("Control", x, y).setMaxSize(m100ax);
        Chart othersChart = new Chart("Others", x, y).setMaxSize(m100ax);
        Chart ihmaChart = new Chart("IHMA", x, y).setMaxSize(m100ax);
        Chart hmaChart = new Chart("HMA", x, y).setMaxSize(m100ax);
        Chart RA1Chart = new Chart("RA1", x, y).setMaxSize(m100ax);
        Chart RA2Chart = new Chart("RA2", x, y).setMaxSize(m100ax);
        Chart RA3Chart = new Chart("RA3", x, y).setMaxSize(m100ax);
        Chart RA4Chart = new Chart("RA4", x, y).setMaxSize(m100ax);
        Chart RA5Chart = new Chart("RA5", x, y).setMaxSize(m100ax);
        Chart RA6Chart = new Chart("RA6", x, y).setMaxSize(m100ax);

        QuickWrapper test = new QuickWrapper(
            controlChart, 
            othersChart, 
            RA1Chart, 
            RA2Chart, 
            RA3Chart, 
            RA4Chart, 
            RA5Chart, 
            RA6Chart
        );

        // use this to set the scale if the max is 1
        controlChart.update(0,1);
        ihmaChart.update(0,1);
        hmaChart.update(0,1);

        int counter = 0;

        while(true) {

            counter++;

            double rNum = SLMath.limit(((test.mouseTracker.getMouseY() - 0.05) / 0.9), 0, 1);

            if(rNum != 0) {
                if(counter % (m100ax) == 0) {
                    double min = 0;
                    controlChart.update(min);
                    othersChart.update(min);
                    hmaChart.update(min);
                    ihmaChart.update(min);
                    RA1Chart.update(min);
                    RA2Chart.update(min);
                    RA3Chart.update(min);
                    RA4Chart.update(min);
                    RA5Chart.update(min);
                    RA6Chart.update(min);
                } else if(counter % (m100ax) == 1) {
                    double max = 1;
                    controlChart.update(max);
                    othersChart.update(max);
                    hmaChart.update(max);
                    ihmaChart.update(max);
                    RA1Chart.update(max);
                    RA2Chart.update(max);
                    RA3Chart.update(max);
                    RA4Chart.update(max);
                    RA5Chart.update(max);
                    RA6Chart.update(max);
                } else {
                    controlChart.update(rNum);
                    othersChart.update(othersFilter.get(rNum));
                    hmaChart.update(hmaFilter.get(rNum));
                    ihmaChart.update(ihmaFilter.get(rNum));
                    RA1Chart.update(ra1Filter.get(rNum));
                    RA2Chart.update(ra2Filter.get(rNum));
                    RA3Chart.update(ra3Filter.get(rNum));
                    RA4Chart.update(ra4Filter.get(rNum));
                    RA5Chart.update(ra5Filter.get(rNum));
                    RA6Chart.update(ra6Filter.get(rNum));
                }
            }

            test.updateCharts();
    
            Thread.sleep(20);
        }

    }
}