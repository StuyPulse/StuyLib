package com.stuypulse.stuylib.util.chart;

import java.util.LinkedList;
import java.util.List;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class NChart extends Chart {

    private static final long serialVersionUID = 1L;

    private List<Chart> charts;

    public NChart(Chart... charts) {
        super();

        this.charts = new LinkedList<Chart>();

        instance = new XYChartBuilder().xAxisTitle("x").yAxisTitle("y").build();

        chartPanel = new XChartPanel<XYChart>(instance);

        getContentPane().add(chartPanel);

        chartPanel.addMouseMotionListener(mouseTracker);

        add(charts);

        display();
    }

    public Chart add(Chart... charts) {
        String chartTitles = instance.getTitle();
        for(Chart chart : charts) {
            chartTitles += chart.getChartTitle() + ", ";
            this.charts.add(chart);
            chart.undisplay();
            instance.addSeries(chart.getChartTitle(), chart.getXData(),
                    chart.getYData());
            instance.getSeriesMap().get(chart.getChartTitle())
                    .setMarker(SeriesMarkers.NONE);
        }
        instance.setTitle(chartTitles);
        return this;
    }

    @Override
    public void update(double x, double y) {
        for(Chart chart : charts) {
            chart.update(x, y);
            instance.updateXYSeries(chart.getChartTitle(), chart.getXData(),
                    chart.getYData(), null);
        }
    }

    @Override
    public void update(double y) {
        for(Chart chart : charts) {
            chart.update(y);
            instance.updateXYSeries(chart.getChartTitle(), chart.getXData(),
                    chart.getYData(), null);
        }
    }

}
