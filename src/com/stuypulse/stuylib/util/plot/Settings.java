package com.stuypulse.stuylib.util.plot;

public final class Settings {
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

    public Settings setXMin(double xMin) {
        this.xMin = xMin;
        return this;
    }

    public Settings setXMax(double xMax) {
        this.xMax = xMax;
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

    public Settings setWidth(int width) {
        this.width = width;
        return this;
    }

    public Settings setHeight(int height) {
        this.height = height;
        return this;
    }

}
