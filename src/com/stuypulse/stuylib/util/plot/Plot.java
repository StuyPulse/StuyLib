package com.stuypulse.stuylib.util.plot;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.stuypulse.stuylib.util.chart.MouseTracker;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Plot {

    private JFrame frame;

    private XYChart instance;
    private XChartPanel<XYChart> panel;

    private MouseTracker mouse;

    public Plot(Settings settings) {
        frame = new JFrame(settings.getTitle());

        frame.getContentPane().setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        instance = new XYChartBuilder()
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
    }

    public MouseTracker getMouseTracker() {
        return mouse;
    }

    public void update(String name, List<Double> x, List<Double> y) {
        x = new ArrayList<>(x);
        y = new ArrayList<>(y);
        if (instance.getSeriesMap().containsKey(name)) {
            instance.updateXYSeries(name, x, y, null);
        } else {
            instance.addSeries(name, x, y);
            instance.getSeriesMap().get(name).setMarker(SeriesMarkers.NONE);
        }
    }

    public void display() {
        Toolkit.getDefaultToolkit().sync();
        panel.revalidate();
        panel.repaint();
    }

}
