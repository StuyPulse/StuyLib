package com.stuypulse.stuylib.util.chart;

import java.util.LinkedList;
import java.util.List;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * A class for handling multi-series graphing by combining the data from multiple singe=series
 * charts.
 *
 * @author Myles Pasetsky (@selym3)
 */
public class NChart extends Chart {

    private static final long serialVersionUID = 1L;

    /**
     * Stores the charts included in the NChart.
     */
    private List<Chart> charts;

    /**
     * Creates an NChart with the data of given charts.
     *
     * @param charts Charts to incorperate.
     */
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

    /**
     * Adds charts.
     *
     * @param charts Charts to add.
     * @return Returns reference to self.
     */
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

    /**
     * Updates each chart's data and a corresponding series based on an ordered pair.
     *
     * @param x new x value
     * @param y new y value
     */
    @Override
    public void update(double x, double y) {
        for(Chart chart : charts) {
            chart.update(x, y);
            instance.updateXYSeries(chart.getChartTitle(), chart.getXData(),
                    chart.getYData(), null);
        }
    }

    /**
     * Updates each chart's data and a corresponding series based on a given y value
     *
     * @param y new y value
     */
    @Override
    public void update(double y) {
        for(Chart chart : charts) {
            chart.update(y);
            instance.updateXYSeries(chart.getChartTitle(), chart.getXData(),
                    chart.getYData(), null);
        }
    }

    /**
     * Reset each chart to an ordered pair and reset each's corresponding series.
     *
     * @param x default x value
     * @param y default y value
     */
    @Override
    public void reset(double x, double y) {
        for(Chart chart : charts) {
            chart.reset(x, y);
            instance.updateXYSeries(chart.getChartTitle(), chart.getXData(),
                    chart.getYData(), null);
        }
    }

    /**
     * Reset each chart to 0,0 and reset each's corresponding series.
     */
    @Override
    public void reset() {
        for(Chart chart : charts) {
            chart.reset();
            instance.updateXYSeries(chart.getChartTitle(), chart.getXData(),
                    chart.getYData(), null);
        }
    }

    /**
     * Set max size of each chart.
     *
     * @param max max size
     * @return Reference to chart.
     */
    @Override
    public Chart setMaxSize(int max) {
        maxDataSize = 0;
        for(Chart chart : charts) {
            chart.setMaxSize(max);
        }
        return this;
    }

}
