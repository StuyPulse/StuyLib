package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.streams.filters.RollingAverage;

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
    public FilteredChart(String title, String x, String y, IStreamFilter filter) {
        super(title,x,y);
        this.filter = filter;
    }

    public FilteredChart(String title, IStreamFilter filter) { 
        this(title,"x","y",filter);
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
        update(getXData().get(getXData().size() - 1) + 1,filter.get(y));
    }

    public static void main(String[] args) throws InterruptedException {
        Chart chart = new FilteredChart("Title", new RollingAverage(24)).setMaxSize(694).setYBounds(0.0, 1.0).display();

        double y = 0;
        while (true) {
            y = SLMath.limit((chart.getMouseY() - 0.05) / 0.9, 0, 1);

            chart.update(y);

            chart.repaint();

            Thread.sleep(50);
        }
    }

}