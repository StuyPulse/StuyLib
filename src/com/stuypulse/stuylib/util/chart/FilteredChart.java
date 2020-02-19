package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;

/**
 * A single session chart with a stream filter.
 * 
 * @author Myles Pasetsky (selym3) 
 */
public class FilteredChart extends Chart {

    private final IStreamFilter filter;

    /**
     * Chart with a filter.
     * 
     * @param IStreamFilter IStreamFilter to apply to all incoming y-values 
     */
    public FilteredChart(String title, String x, String y, IStreamFilter filter) {
        super(title,x,y);
        this.filter = filter;
    }

    /**
     * Update function used by FilteredChart to apply the filter. Must be used
     * because it autoincrements the x-value, as to not mess with time-dependent
     * filters.
     * 
     * @param y Y value to be filtered
     */
    @Override
    public void update(double y) {
        update(xData.get(xData.size() - 1) + 1,filter.get(y));
    }

}