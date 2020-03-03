package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;

/**
 * A single session chart with a stream filter.
 *
 * @author Myles Pasetsky (selym3)
 */
public class FilteredChart extends Chart {

    private static final long serialVersionUID = 1L;

    /**
     * Filter for this FilteredChart
     */
    private final IStreamFilter filter;

    /**
     * Chart with a filter.
     *
     * @param title  Chart title
     * @param x      x-axis title
     * @param y      y-axis title
     * @param filter IStreamFilter to apply to all incoming y-values
     */
    public FilteredChart(String title, String x, String y,
            IStreamFilter filter) {
        super(title, x, y);
        this.filter = filter;
    }

    /**
     * Chart with a filter.
     *
     * @param title  Chart title
     * @param filter IStreamFilter to apply to all incoming y-values
     */
    public FilteredChart(String title, IStreamFilter filter) {
        this(title, "x", "y", filter);
    }

    /**
     * Update Y value with the chart's filter.
     *
     * @param y Y value to be filtered
     */
    @Override
    public void update(double y) {
        update(getXData().get(getXData().size() - 1) + 1, filter.get(y));
    }

    /**
     * Update Y value with the chart's filter. Y is filtered in order, set X accordingly
     *
     * @param x X value
     * @param y Y value to be filtered
     */
    @Override
    public void update(double x, double y) {
        super.update(x, filter.get(y));
    }

}
