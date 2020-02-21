package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;

/**
 * A single session chart with a stream filter.
 *
 * @author Myles Pasetsky (selym3)
 */
public class FilteredChart extends Chart {

    private static final long serialVersionUID = 1L;

    private final IStreamFilter filter;

    /**
     * Chart with a filter.
     *
     * @param IStreamFilter IStreamFilter to apply to all incoming y-values
     */
    public FilteredChart(String title, String x, String y,
            IStreamFilter filter) {
        super(title, x, y);
        this.filter = filter;
    }

    /**
     * Chart with a filter.
     *
     * @param IStreamFilter IStreamFilter to apply to all incoming y-values
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

}
