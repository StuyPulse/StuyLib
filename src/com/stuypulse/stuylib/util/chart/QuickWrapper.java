package com.stuypulse.stuylib.util.chart;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.streams.filters.InvertedHullMovingAverage;
import com.stuypulse.stuylib.streams.filters.RollingAverage;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

public class QuickWrapper extends KeyAdapter {

    /**
     * TODO: Test through using Make NChart.java which can handle mulitple lines
     * (multiple series)
     * 
     * should there be remove method how should mulitiple of the same chart be
     * handled, does it matter??? is everything done practically
     * 
     * optomize updates (and some other functions) for XYChart (most are done for
     * Chart, and not XYChart all specific repaint methods
     * decide which functions should return a reference to the class (QuickWrapper -> return this;)
     */

    private List<Chart> charts;
    private List<XChartPanel<XYChart>> chartPanels; // store chart panels for convenience in repainting
    private JFrame frame;

    private final CardLayout cl;
    private final Container frameContent;

    // CONSTRUCTORS

    /**
     * Initialize lists, frame, and cardlayout.
     * Doesn't require any charts.
     */
    public QuickWrapper() {
        charts = new ArrayList<Chart>();
        frame = new JFrame("QuickWrapper Frame");
        cl = new CardLayout();
        chartPanels = new ArrayList<XChartPanel<XYChart>>();
        frameContent = frame.getContentPane();
        frameContent.setLayout(cl);

        frameContent.setPreferredSize(new Dimension(800, 800));

        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Creates a quick wrapper with some charts.
     * 
     * @param newCharts An array of charts
     */
    public QuickWrapper(Chart... newCharts) {
        this();
        add(newCharts);
    }

    // GETTERS

    /**
     * Returns a panel with the given name. Returns null
     * if not found
     * 
     * @param title Name of the component
     * @return Return the given JPanel if exists
     */
    private JPanel getPanel(String title) {
        for (int i = 0;i < frameContent.getComponentCount(); i++) {
            if (frameContent.getComponent(i).getName().equals(title)) {
                return (JPanel) frameContent.getComponent(i);
            }
        }
        return null;
    }

    /**
     * Returns a panel with the given index. Returns null
     * if not inbounds.
     * 
     * @param index index of the componenet
     * @return Return the given JPanel in bound
     */
    private JPanel getPanel(int index) {
        if (index > frameContent.getComponents().length - 1 || index < 0)
            return null;
        return (JPanel) frameContent.getComponent(index);
    }

    /**
     * Returns a Chart with the given title. Returns null
     * if not found.
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
     * Returns a Chart with the given index. Returns null
     * if out of bounds.
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
        return Arrays.asList(frameContent.getComponents()).indexOf(panel) >= 0;
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

            chartPanels.add(panel);
            frameContent.add(panel, newChart.get().getTitle());
        }
        return this;
    }

    // REPAINT

    public QuickWrapper repaint() {
        for (XChartPanel<XYChart> chartPanel : chartPanels) {
            chartPanel.revalidate();
            chartPanel.repaint();
        }
        return this;
    }


    // TEST toString function
    public String toString() {
        return frameContent.getComponents().length + "\n" + charts.size();
    }

    // KEY BINDINGS
    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }

        if (keyCode == KeyEvent.VK_RIGHT) {
            cl.next(frameContent);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            cl.previous(frameContent);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final String x = "x";
        final String y = "y";
        
        IStreamFilter invertedHullMovingAverage = new InvertedHullMovingAverage(50);
        IStreamFilter rollingAverage = new RollingAverage(24.0);

        Chart chart = new Chart("Chart 1", x, y);
        Chart bruh = new Chart("Chart 2", x, y);

        Chart chart2 = new Chart("Chart 1", x, y);
        Chart bruh2 = new Chart("Chart 2", x, y);

        QuickWrapper ih = new QuickWrapper(chart,bruh);
        QuickWrapper ra = new QuickWrapper(chart2,bruh2);

        for (int i = 1; i < 1000;i++) {
            double rNum = Math.random() * 100;
            chart.update(invertedHullMovingAverage.get(rNum));
            bruh.update(rNum);

            chart2.update(rollingAverage.get(rNum));
            bruh2.update(rNum);


            ih.repaint();
            ra.repaint();
            Thread.sleep(100); // see it update real time
        }

    }

}