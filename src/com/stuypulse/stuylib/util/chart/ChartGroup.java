package com.stuypulse.stuylib.util.chart;

import java.awt.Container;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.awt.*;
import java.awt.event.*;

import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.filters.OrderedLowPassFilter;

/**
 * @author Myles Pasetsky (@selym3)
 */
public class ChartGroup extends Chart {

    private static final long serialVersionUID = 1L;

    public static final int OFFSET = 69;

    private List<Chart> charts;

    private final CardLayout cardLayout;
    private int currentCardIndex;

    private final KeyTracker.BindingFunction leftFunction;
    private final KeyTracker.BindingFunction rightFunction;

    public ChartGroup(Chart... charts) {
        super();
        this.charts = new LinkedList<Chart>();

        currentCardIndex = 0;

        cardLayout = new CardLayout();

        leftFunction = () -> {
            cardLayout.previous(getContentPane());
            updateCurrentCardIndex(false);
        };

        rightFunction = () -> {
            cardLayout.next(getContentPane());
            updateCurrentCardIndex(true);
        };

        addKeyBinding(KeyEvent.VK_LEFT, leftFunction);
        addKeyBinding(KeyEvent.VK_RIGHT, rightFunction);

        getContentPane().setLayout(cardLayout);
        
        add(charts);

        display();
    }

    private int updateCurrentCardIndex(boolean direction) {
        currentCardIndex += direction ? 1 : charts.size() - 1;
        currentCardIndex = currentCardIndex%charts.size();
        return currentCardIndex;
    }

    public ChartGroup add(Chart... charts) {
        for (int i = 0;i < charts.length;i++) {
            this.charts.add(charts[i]);
            charts[i].undisplay();
            getContentPane().add(charts[i].getContentPane());
        }
        return this;
    }

    @Override
    public void update(double x, double y) {
        for (Chart chart : charts) {
            chart.update(x, y);
        }
    }

    @Override
    public void update(double y) {
        for (Chart chart : charts) {
            chart.update(y);
        }
    }

    @Override
    public void reset(double x, double y) {
        for (Chart chart : charts) {
            chart.reset(x, y);
        }
    }

    @Override
    public void reset() {
        System.out.println("THIS IS CALLED");
        for (Chart chart : charts) {
            chart.reset();
        }
    }

    @Override
    public Chart setMaxSize(int max) {
        for (Chart chart : charts) {
            chart.setMaxSize(max);
        }
        return this;
    }

    @Override
    public Chart setXBounds(Double min, Double max) {
        for (Chart chart : charts) {
            chart.setXBounds(min, max);
        }
        return this;
    }

    @Override
    public Chart setYBounds(Double min, Double max) {
        for (Chart chart : charts) {
            chart.setYBounds(min, max);
        }
        return this;
    }

    @Override
    public Chart resetXBounds() {
        for (Chart chart : charts) {
            chart.resetXBounds();
        }
        return this;
    }

    @Override
    public Chart resetYBounds() {
        for (Chart chart : charts) {
            chart.resetYBounds();
        }
        return this;
    }

    @Override
    public void redraw() {
       charts.get(currentCardIndex).redraw();
    }

    @Override
    public double getMouseX() {
        return charts.get(currentCardIndex).getMouseX();
    }

    @Override
    public double getMouseY() {
        return charts.get(currentCardIndex).getMouseY();
    }

    @Override
    public int getMaxSize() {
        return charts.get(currentCardIndex).getMaxSize();
    }

    @Override
    public Double[] getXBounds() {
        return charts.get(currentCardIndex).getXBounds();
    }

    @Override
    public Double[] getYBounds() {
        return charts.get(currentCardIndex).getYBounds();
    }

    @Override
    public String toString() {
        return Integer.toString(charts.size());
    }

    public static void main(String[] args) throws InterruptedException {

        /**
         * get the content pane of the Chart if its in a chartgroup and add it to the single chart frame
         * ChartGroup should be a JFrame
         * add hide/show methods
         * 
         */

        Chart control = new Chart("Control", "X", "Y");
        Chart speed = new FilteredChart("Speed", "X", "Y", new OrderedLowPassFilter(0.5, 1));
        Chart angle = new FilteredChart("Angle", "X", "Y", new OrderedLowPassFilter(0.1, 1));

        Chart group = new ChartGroup(control, speed, angle).setYBounds(0.0,1.2).setMaxSize(400);

        double rNum = 0.0;

        while (true) {
            rNum = SLMath.limit(((group.getMouseY() - 0.04)/0.9),0,1);
            
            group.update(rNum);

            group.redraw();

            Thread.sleep(20);

        }
    }
}