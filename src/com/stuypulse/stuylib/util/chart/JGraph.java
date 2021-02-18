package com.stuypulse.stuylib.util.chart;

import javax.swing.*;

import java.awt.*;

import org.knowm.xchart.*;

/**
 * GUI wrapper for graph data.
 *
 * @author Sam (sam.belliveau@gmail.com)
 * @author Myles Pasetsky (@selym3)
 */
public class JGraph {

    /**
     * Graphical data.
     */
    private final GraphData[] graphs;

    /**
     * GUI frame.
     */
    private JFrame frame;

    /**
     * XChart internals.
     */
    private XYChart instance;

    /**
     * XChart internals.
     */
    private XChartPanel<XYChart> chartPanel;

    /**
     * Mouse tracker internals.
     */
    private final MouseTracker mouseTracker;

    /**
     * Mouse tracker internals.
     */
    private final KeyTracker keyTracker;

    /**
     * Create a JGraph
     *
     * @param graphs any graphical information to be represented
     */
    public JGraph(GraphData... graphs) {
        this.graphs = graphs;

        // CREATE FRAME //
        frame = new JFrame(this.graphs[0].getName());

        frame.getContentPane().setPreferredSize(new Dimension(694, 694));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // CREATE XYCHART //
        instance = new XYChartBuilder().title(this.graphs[0].getName())
                .xAxisTitle("x").yAxisTitle("y").build();

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for(GraphData i : this.graphs) {
            min = Math.min(min, i.getMinValue());
            max = Math.max(max, i.getMaxValue());
        }

        instance.getStyler().setYAxisMax(max);
        instance.getStyler().setYAxisMin(min);

        // CHART PANEL //
        chartPanel = new XChartPanel<XYChart>(instance);

        mouseTracker = new MouseTracker(chartPanel);
        keyTracker = new KeyTracker();

        frame.addKeyListener(keyTracker);

        // CHART PANEL SPECIFICATIONS //
        chartPanel.setName(this.graphs[0].getName());

        // FRAME LISTENER //
        frame.getContentPane().add(chartPanel);

        frame.setResizable(false);
        
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * @see KeyTracker
     * @return key tracker
     */
    public KeyTracker getKeyTracker() {
        return keyTracker;
    }

    /**
     * @see MouseTracker
     * @return mouse tracker
     */
    public MouseTracker getMouseTracker() {
        return mouseTracker;
    }

    /**
     * Update the graphs without sending any new value
     *
     * This is useful because it lets you send the update function directly instead of through the
     * JGraph.
     */
    public void update() {
        for(GraphData i : graphs) {
            i.updateXYChart(instance);
        }

        Toolkit.getDefaultToolkit().sync();
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    /**
     * Update all the charts with the same value
     *
     * @param val the value to send to all the charts
     */
    public void update(double val) {
        for(GraphData i : graphs) {
            i.update(val);
            i.updateXYChart(instance);
        }

        Toolkit.getDefaultToolkit().sync();
        chartPanel.revalidate();
        chartPanel.repaint();
    }

}
