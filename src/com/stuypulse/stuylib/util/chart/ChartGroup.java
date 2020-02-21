package com.stuypulse.stuylib.util.chart;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * Group of charts.
 *
 * @author Myles Pasetsky (@selym3)
 */
public class ChartGroup extends Chart {

    private static final long serialVersionUID = 1L;

    /**
     * Stores charts.
     */
    private List<Chart> charts;

    /**
     * Swing layout for organizing charts.
     */
    private final CardLayout cardLayout;

    /**
     * Tracks the current chart the card layout is on.
     */
    private int currentCardIndex;

    /**
     * Built-in utility for using left arrow key to scroll through charts.
     */
    private final KeyTracker.BindingFunction leftFunction;

    /**
     * Built-in utility for using right arrow key to scroll through charts.
     */
    private final KeyTracker.BindingFunction rightFunction;

    /**
     * Constructor to take in charts.
     *
     * @param charts Charts to group.
     */
    public ChartGroup(Chart... charts) {
        super();
        this.charts = new LinkedList<Chart>();

        currentCardIndex = 0;

        cardLayout = new CardLayout();

        leftFunction = () -> {
            cardLayout.previous(getContentPane());
            updateCurrentCardIndex(false);
            this.charts.get(currentCardIndex).requestFocus();
        };

        rightFunction = () -> {
            cardLayout.next(getContentPane());
            updateCurrentCardIndex(true);
            this.charts.get(currentCardIndex).requestFocus();
        };

        addKeyBinding(KeyEvent.VK_LEFT, leftFunction);
        addKeyBinding(KeyEvent.VK_RIGHT, rightFunction);

        getContentPane().setLayout(cardLayout);

        add(charts);

        display();
    }

    /**
     * Update card index based on direction of scrolling.
     */
    private int updateCurrentCardIndex(boolean direction) {
        currentCardIndex += direction ? 1 : charts.size() - 1;
        currentCardIndex = currentCardIndex % charts.size();
        return currentCardIndex;
    }

    /**
     * Add addition charts to the chart group.
     *
     * @param charts Charts to add
     */
    public ChartGroup add(Chart... charts) {
        for(int i = 0; i < charts.length; i++) {
            this.charts.add(charts[i]);
            charts[i].undisplay();
            getContentPane().add(charts[i].getContentPane());
        }
        return this;
    }

    /**
     * Update each chart's data.
     */
    @Override
    public void update(double x, double y) {
        for(Chart chart : charts) {
            chart.update(x, y);
        }
    }

    /**
     * Update each chart's data.
     */
    @Override
    public void update(double y) {
        for(Chart chart : charts) {
            chart.update(y);
        }
    }

    /**
     * Reset each chart's data.
     */
    @Override
    public void reset(double x, double y) {
        for(Chart chart : charts) {
            chart.reset(x, y);
        }
    }

    /**
     * Reset each chart's data.
     */
    @Override
    public void reset() {
        System.out.println("THIS IS CALLED");
        for(Chart chart : charts) {
            chart.reset();
        }
    }

    /**
     * Set each chart's max size.
     */
    @Override
    public Chart setMaxSize(int max) {
        for(Chart chart : charts) {
            chart.setMaxSize(max);
        }
        return this;
    }

    /**
     * Set x bounds of each chart.
     */
    @Override
    public Chart setXBounds(Double min, Double max) {
        for(Chart chart : charts) {
            chart.setXBounds(min, max);
        }
        return this;
    }

    /**
     * Set y bounds of each chart.
     */
    @Override
    public Chart setYBounds(Double min, Double max) {
        for(Chart chart : charts) {
            chart.setYBounds(min, max);
        }
        return this;
    }

    /**
     * Reset each chart's x boundaries.
     */
    @Override
    public Chart resetXBounds() {
        for(Chart chart : charts) {
            chart.resetXBounds();
        }
        return this;
    }

    /**
     * Reset each chart's y data.
     */
    @Override
    public Chart resetYBounds() {
        for(Chart chart : charts) {
            chart.resetYBounds();
        }
        return this;
    }

    /**
     * Redraw the current selected chart.
     */
    @Override
    public void redraw() {
        charts.get(currentCardIndex).redraw();
    }

    /**
     * Gets the mouse x from the current selected chart.
     */
    @Override
    public double getMouseX() {
        return charts.get(currentCardIndex).getMouseX();
    }

    /**
     * Gets the mouse y from the current selected chart.
     */
    @Override
    public double getMouseY() {
        return charts.get(currentCardIndex).getMouseY();
    }

    /**
     * Gets the max size from the current selected chart.
     */
    @Override
    public int getMaxSize() {
        return charts.get(currentCardIndex).getMaxSize();
    }

    /**
     * Get the x bounds from the current selected chart.
     */
    @Override
    public Double[] getXBounds() {
        return charts.get(currentCardIndex).getXBounds();
    }

    /**
     * Get the y bounds from the current selected chart.
     */
    @Override
    public Double[] getYBounds() {
        return charts.get(currentCardIndex).getYBounds();
    }

}
