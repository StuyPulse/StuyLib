/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

/**
 * Settings for a plot.
 *
 * <p>Supports changing labels, axis bounds, and window size.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public final class Settings {

    /** Convenience struct to represent axis labels */
    public static class Axes {

        /** all the labels stored */
        private String title, x, y;

        /**
         * Create an axes object
         *
         * @param title title
         * @param x x-axis label
         * @param y y-axis label
         */
        public Axes(String title, String x, String y) {
            this.title = title;
            this.x = x;
            this.y = y;
        }
    }

    /** labels of the plot */
    private String title;

    private String xAxis;
    private String yAxis;

    /** window size */
    private int width;

    private int height;

    /** x-axis bounds */
    private double xMin;

    private double xMax;

    /** y-axis bounds */
    private double yMin;

    private double yMax;

    /** Create a settings object with default values */
    public Settings() {
        title = "Plot";
        xAxis = "x";
        yAxis = "y";

        width = 640;
        height = 480;

        xMin = 0.0;
        xMax = 1.0;

        yMin = 0.0;
        yMax = 1.0;
    }

    /** @return title */
    public String getTitle() {
        return title;
    }

    /** @return x-axis label */
    public String getXAxis() {
        return xAxis;
    }

    /** @return y-axis label */
    public String getYAxis() {
        return yAxis;
    }

    /** @return width of window */
    public int getWidth() {
        return width;
    }

    /** @return height of window */
    public int getHeight() {
        return height;
    }

    /** @return lower bound of x-axis */
    public double getXMin() {
        return xMin;
    }

    /** @return upper bound of x-axis */
    public double getXMax() {
        return xMax;
    }

    /** @return lower bound of y-axis */
    public double getYMin() {
        return yMin;
    }

    /** @return upper bound of y-axis */
    public double getYMax() {
        return yMax;
    }

    /**
     * Sets title of plot
     *
     * @param title title
     * @return reference to self for chaining methods
     */
    public Settings setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets x-axis label
     *
     * @param xAxis x-axis label
     * @return reference to self for chaining methods
     */
    public Settings setXAxis(String xAxis) {
        this.xAxis = xAxis;
        return this;
    }

    /**
     * Set y-axis label
     *
     * @param yAxis y-axis label
     * @return reference to self for chaining methods
     */
    public Settings setYAxis(String yAxis) {
        this.yAxis = yAxis;
        return this;
    }

    /**
     * Set all axis labels at once
     *
     * @param title title
     * @param xAxis x-axis label
     * @param yAxis y-axis label
     * @return reference to self for chaining methods
     */
    public Settings setAxes(String title, String xAxis, String yAxis) {
        setTitle(title);
        setXAxis(xAxis);
        setYAxis(yAxis);
        return this;
    }

    /**
     * Sets all the axis labels at once
     *
     * @param axes object containing axis labels
     * @return reference to self for chaining methods
     */
    public Settings setAxes(Axes axes) {
        return setAxes(axes.title, axes.x, axes.y);
    }

    /**
     * Sets lower bound for x-axis
     *
     * @param xMin lower bound for x-axis
     * @return reference to self for chaining methods
     */
    public Settings setXMin(double xMin) {
        this.xMin = xMin;
        return this;
    }

    /**
     * Sets upper bound for x-axis
     *
     * @param xMax upper bound for x-axis
     * @return reference to self for chaining methods
     */
    public Settings setXMax(double xMax) {
        this.xMax = xMax;
        return this;
    }

    /**
     * Sets range of x-values on plot
     *
     * @param min x axis lower bound
     * @param max x axis upper bound
     * @return reference to self for chaining methods
     */
    public Settings setXRange(double min, double max) {
        setXMax(max);
        setXMin(min);
        return this;
    }

    /**
     * Sets lower bound for y-axis
     *
     * @param yMin lower bound for y-axis
     * @return reference to self for chaining methods
     */
    public Settings setYMin(double yMin) {
        this.yMin = yMin;
        return this;
    }

    /**
     * Sets upper bound for y-axis
     *
     * @param yMax upper bound for y-axis
     * @return reference to self for chaining methods
     */
    public Settings setYMax(double yMax) {
        this.yMax = yMax;
        return this;
    }

    /**
     * Sets range of y-values on plot
     *
     * @param min y axis lower bound
     * @param max y axis upper bound
     * @return reference to self for chaining methods
     */
    public Settings setYRange(double min, double max) {
        setYMax(max);
        setYMin(min);
        return this;
    }

    /**
     * Sets width of window
     *
     * @param width width of window
     * @return reference to self for chaining methods
     */
    public Settings setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * Sets height of window
     *
     * @param height height of window
     * @return reference to self for chaining methods
     */
    public Settings setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Sets size of window
     *
     * @param width width of window
     * @param height height of window
     * @return reference to self for chaining methods
     */
    public Settings setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
        return this;
    }
}
