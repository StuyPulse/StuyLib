/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.math.interpolation.*;
import com.stuypulse.stuylib.streams.*;
import com.stuypulse.stuylib.streams.booleans.*;
import com.stuypulse.stuylib.streams.booleans.filters.*;
import com.stuypulse.stuylib.streams.filters.*;
import com.stuypulse.stuylib.streams.vectors.*;
import com.stuypulse.stuylib.streams.vectors.filters.*;
import com.stuypulse.stuylib.util.plot.FuncSeries.Domain;
import com.stuypulse.stuylib.util.plot.Series.Config;
import com.stuypulse.stuylib.util.plot.TimeSeries.TimeSpan;

public class Playground {

    public interface Constants {
        int CAPACITY = 200;

        String TITLE = "StuyLib Plotting Library";
        String X_AXIS = "x-axis";
        String Y_AXIS = "y-axis";

        int WIDTH = 1600;
        int HEIGHT = 1200;

        double MIN_X = 0.0;
        double MAX_X = 1.0;

        double MIN_Y = 0.0;
        double MAX_Y = 1.0;

        Settings SETTINGS =
                new Settings()
                        .setAxes(TITLE, X_AXIS, Y_AXIS)
                        .setXRange(MIN_X, MAX_X)
                        .setYRange(MIN_Y, MAX_Y);

        public static Series make(String id, IFilter function) {
            return new FuncSeries(new Config(id, CAPACITY), new Domain(MIN_X, MAX_X), function);
        }

        public static Series make(String id, IStream series) {
            return new TimeSeries(new Config(id, CAPACITY), new TimeSpan(MIN_X, MAX_X), series);
        }

        public static Series make(String id, VStream series) {
            return new XYSeries(new Config(id, CAPACITY), series);
        }

        public static Series make(String id, BStream series) {
            return make(id, IStream.create(series));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Plot fplot = new Plot(Constants.SETTINGS)
                .addSeries(Constants.make("y=x", x -> x))
                .addSeries(
                        Constants.make(
                                "interp",
                                new LinearInterpolator(
                                        new Vector2D(0.0, 0.43),
                                        new Vector2D(0.2, 0.56),
                                        new Vector2D(0.4, 0.72),
                                        new Vector2D(0.6, 0.81),
                                        new Vector2D(0.8, 0.02),
                                        new Vector2D(1.0, 0.11))));

        Plot plot = new Plot(Constants.SETTINGS);
        
        plot.addSeries(Constants.make("mouse y", IStream.create(plot::getMouseY)))
                .addSeries(
                        Constants.make(
                                "lpf",
                                IStream.create(plot::getMouseY).filtered(new LowPassFilter(0.2))))
                .addSeries(
                        Constants.make("mouse bool", BStream.create(() -> plot.getMouseY() > 0.5)))
                .addSeries(
                        Constants.make(
                                "debounced",
                                BStream.create(() -> plot.getMouseY() > 0.5)
                                        .filtered(new BDebounce.Both(1.0))));

        Plot vplot = new Plot(Constants.SETTINGS);

        vplot.addSeries(Constants.make("mouse position", VStream.create(plot::getMouse)))
                .addSeries(
                        Constants.make(
                                "jerk limit",
                                VStream.create(plot::getMouse)
                                        .filtered(new VJerkLimit(10.0, 5.0))));

        Window window = new Window(
                Constants.TITLE,
                Constants.WIDTH,
                Constants.HEIGHT,
                plot,
                fplot,
                vplot);
                                        
        for (;;) {
            window.update();
            Thread.sleep(20);
        }
    }
}
