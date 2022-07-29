package com.stuypulse.stuylib.util.plot;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

public class Tab {

    /** A collection of Series to be graphed */
    private List<Series> plots;
	
	private XYChart instance;

	protected XChartPanel<XYChart> panel;

    /** A boolean to ensure the plot is updated at least once */
    private boolean runOnce;

	public Tab(Settings settings) {
        // Setup series
        plots = new ArrayList<>();
		
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

        instance.getStyler().setLegendPosition(LegendPosition.InsideNW);
		
        panel = new XChartPanel<XYChart>(instance);
        panel.setName(settings.getTitle());

        runOnce = true;
	}
	
	public void addSeries(Series... series) {
		for (Series s : series) plots.add(s);
	}

    /** allows the series to update the XYChart */
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
     * Checks if any series are polling to see if the plot should still update.
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
