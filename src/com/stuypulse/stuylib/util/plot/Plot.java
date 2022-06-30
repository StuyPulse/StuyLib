/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

import com.stuypulse.stuylib.math.Vector2D;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

/**
 * A plot contains and manages the window to which any data
 * is drawn.
 * 
 * It stores the Series that it is going draw. It also has 
 * methods that read the mouse's position, which can be used as 
 * an input stream for a series.  
 * 
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class Plot {

    /** A collection of Series to be graphed */
    private List<Series> plots;

    /** The window that is created */
    private JFrame frame;

    /** A reference to the XChart library */
    private XYChart instance;
    private XChartPanel<XYChart> panel;

    /** A utility for finding mouse positions */
    private MouseTracker mouse;

    /** A boolean to ensure the plot is updated at least once */
    private boolean runOnce;
    
    /**
     * Creates a configured plot
     * 
     * @param settings plot & window settings
     */
    public Plot(Settings settings) {
        // Setup series
        plots = new ArrayList<>();

        // Setup window
        frame = new JFrame(settings.getTitle());

        frame.getContentPane()
                .setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create XYChart using settings
        instance =
                new XYChartBuilder()
                        .title(settings.getTitle())
                        .xAxisTitle(settings.getXAxis())
                        .yAxisTitle(settings.getYAxis())
                        .build();

        instance.getStyler().setYAxisMin(settings.getYMin());
        instance.getStyler().setYAxisMax(settings.getYMax());

        instance.getStyler().setXAxisMin(settings.getXMin());
        instance.getStyler().setXAxisMax(settings.getXMax());

        panel = new XChartPanel<XYChart>(instance);
        panel.setName(settings.getTitle());

        mouse = new MouseTracker(panel);

        frame.getContentPane().add(panel);
        frame.setResizable(false);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        runOnce = true;
    }

    /** Creates a plot with default settings */
    public Plot() {
        this(new Settings());
    }

    /** @return mouse position */
    public Vector2D getMouse() {
        return mouse.getPosition();
    }

    /** @return mouse y position */
    public double getMouseY() {
        return mouse.getY();
    }
    
    /** @return mouse x position */
    public double getMouseX() {
        return mouse.getX();
    }

    /**
     * Adds series to be graphed
     * 
     * @return reference to self 
     */
    public Plot addSeries(Series... series) {
        for (Series e : series) plots.add(e);
        return this;
    }
    
    /** allows the series to update the XYChart  */
    public void updateSeries() {
        for (Series plot : plots) {
            plot.update(instance);
        }
    }

    /** repaints the screen */
    public void display() {
        Toolkit.getDefaultToolkit().sync();
        panel.revalidate();
        panel.repaint();
    }

    public void update() {
        updateSeries();
        display();
    }

    /**
     * Checks if any series are polling to see if the plot
     * should still update. 
     * 
     * @return if the plot should still run 
     */
    public boolean isRunning() {
        if (runOnce) {
            runOnce = false;
            return true;
        }

        for (Series e : plots) {
            if (e.isPolling()) return true;
        }
        return false;
    }
}
