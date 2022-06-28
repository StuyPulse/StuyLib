/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

public final class Settings {
    public static class Axes {
        private String title, x, y;

        public Axes(String title, String x, String y) {
            this.title = title;
            this.x = x;
            this.y = y;
        }
    }

    
    private String title;
    private String xAxis;
    private String yAxis;

    private int width;
    private int height;

    private double xMin;
    private double xMax;

    private double yMin;
    private double yMax;

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

    public String getTitle() {
        return title;
    }

    public String getXAxis() {
        return xAxis;
    }

    public String getYAxis() {
        return yAxis;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public double getYMin() {
        return yMin;
    }

    public double getYMax() {
        return yMax;
    }

    public Settings setTitle(String title) {
        this.title = title;
        return this;
    }

    public Settings setXAxis(String xAxis) {
        this.xAxis = xAxis;
        return this;
    }

    public Settings setYAxis(String yAxis) {
        this.yAxis = yAxis;
        return this;
    }

    public Settings setAxes(String title, String xAxis, String yAxis) {
        setTitle(title);
        setXAxis(xAxis);
        setYAxis(yAxis);
        return this;
    }

    public Settings setAxes(Axes axes) {
        return setAxes(axes.title, axes.x, axes.y);
    }

    public Settings setXMin(double xMin) {
        this.xMin = xMin;
        return this;
    }

    public Settings setXMax(double xMax) {
        this.xMax = xMax;
        return this;
    }

    public Settings setXRange(double min, double max) {
        setXMax(max);
        setXMin(min);
        return this;
    }

    public Settings setYMin(double yMin) {
        this.yMin = yMin;
        return this;
    }

    public Settings setYMax(double yMax) {
        this.yMax = yMax;
        return this;
    }

    public Settings setYRange(double min, double max) {
        setYMax(max);
        setYMin(min);
        return this;
    }

    public Settings setWidth(int width) {
        this.width = width;
        return this;
    }

    public Settings setHeight(int height) {
        this.height = height;
        return this;
    }

    public Settings setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
        return this;
    }
}
