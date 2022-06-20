package com.stuypulse.stuylib.util.plot;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

public class Plot {
    
    private JFrame frame;
    
    private XYChart instance;
    private XChartPanel<XYChart> panel;

    public Plot(Settings settings) {
        frame = new JFrame(settings.getTitle());
        
        frame.getContentPane().setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        instance = new XYChartBuilder()
            .title(settings.getTitle())
            .xAxisTitle(settings.getXAxis())
            .yAxisTitle(settings.getYAxis())
            .build();

        instance.getStyler().setYAxisMin(settings.getYMin());
        instance.getStyler().setYAxisMax(settings.getYMax());

        panel = new XChartPanel<XYChart>(instance);
        panel.setName(settings.getTitle());


        frame.getContentPane().add(panel);
        frame.setResizable(false);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
