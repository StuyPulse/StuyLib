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

public class Plot {

    private List<Series> plots;

    private JFrame frame;

    private XYChart instance;
    private XChartPanel<XYChart> panel;

    private MouseTracker mouse;

    private boolean runOnce;

    public Plot(Settings settings) {
        plots = new ArrayList<>();

        frame = new JFrame(settings.getTitle());

        frame.getContentPane()
                .setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

    public Plot() {
        this(new Settings());
    }

    public Vector2D getMouse() {
        return mouse.getPosition();
    }

    public double getMouseY() {
        return mouse.getY();
    }

    public double getMouseX() {
        return mouse.getX();
    }

    public Plot addSeries(Series... series) {
        for (Series e : series) plots.add(e);
        return this;
    }

    public void updateSeries() {
        for (Series plot : plots) {
            plot.update(instance);
        }
    }

    public void update() {
        updateSeries();
        display();
    }

    public void display() {
        Toolkit.getDefaultToolkit().sync();
        panel.revalidate();
        panel.repaint();
    }

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
