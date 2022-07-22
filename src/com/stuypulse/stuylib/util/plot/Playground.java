/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.math.interpolation.*;
import com.stuypulse.stuylib.streams.*;
import com.stuypulse.stuylib.streams.angles.AStream;
import com.stuypulse.stuylib.streams.angles.filters.AHighPassFilter;
import com.stuypulse.stuylib.streams.angles.filters.AJerkLimit;
import com.stuypulse.stuylib.streams.angles.filters.ALowPassFilter;
import com.stuypulse.stuylib.streams.angles.filters.ARateLimit;
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
        int CAPACITY = 10;

        String TITLE = "StuyLib Plotting Library";
        String X_AXIS = "x-axis";
        String Y_AXIS = "y-axis";

        int WIDTH = 800;
        int HEIGHT = 600;

        double MIN_X = -1.0;
        double MAX_X = 1.0;

        double MIN_Y = -1.0;
        double MAX_Y = 1.0;

        Settings SETTINGS =
                new Settings()
                        .setSize(WIDTH, HEIGHT)
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
        Plot plot = new Plot(Constants.SETTINGS);

        VStream m =
                VStream.create(() -> plot.getMouse().sub(new Vector2D(0.5, 0.5)).mul(2))
                        .filtered(new VJerkLimit(1, 2));
        AStream angle_mouse = AStream.create(() -> m.get().getAngle());
        AStream jerk_angle = angle_mouse.filtered(new AJerkLimit(-1, 4));
        AStream rate_angle = angle_mouse.filtered(new ARateLimit(1));
        AStream lpf_angle = angle_mouse.filtered(new ALowPassFilter(0.5));
        AStream hpf_angle = angle_mouse.filtered(new AHighPassFilter(0.5));

        VStream mouse = VStream.create(() -> angle_mouse.get().getVector().mul(1.0));
        VStream jerk = VStream.create(() -> jerk_angle.get().getVector().mul(0.95));
        VStream rate = VStream.create(() -> rate_angle.get().getVector().mul(0.9));
        VStream lpf = VStream.create(() -> lpf_angle.get().getVector().mul(0.85));
        VStream hpf = VStream.create(() -> hpf_angle.get().getVector().mul(0.8));

        plot.addSeries(Constants.make("Angle", mouse))
                .addSeries(Constants.make("Jerk", jerk))
                .addSeries(Constants.make("Rate", rate))
                .addSeries(Constants.make("LPF", lpf))
                // .addSeries(Constants.make("HPF", hpf))
                .addSeries(Constants.make("mouse", m));
        //
        // .addSeries(Constants.make("y=x", x -> x))
        // .addSeries(
        //         Constants.make(
        //                 "interp",
        //                 new LinearInterpolator(
        //                         new Vector2D(0.0, 0.43),
        //                         new Vector2D(0.2, 0.56),
        //                         new Vector2D(0.4, 0.72),
        //                         new Vector2D(0.6, 0.81),
        //                         new Vector2D(0.8, 0.02),
        //                         new Vector2D(1.0, 0.11))))
        // .addSeries(Constants.make("mouse y", IStream.create(plot::getMouseY)))
        // .addSeries(
        //         Constants.make(
        //                 "lpf",
        //                 IStream.create(plot::getMouseY).filtered(new LowPassFilter(0.2))))
        // .addSeries(
        //         Constants.make("mouse bool", BStream.create(() -> plot.getMouseY() > 0.5)))
        // .addSeries(
        //         Constants.make(
        //                 "debounced",
        //                 BStream.create(() -> plot.getMouseY() > 0.5)
        //                         .filtered(new BDebounce.Both(1.0))))
        // .addSeries(Constants.make("mouse position", VStream.create(plot::getMouse)))
        // .addSeries(
        //         Constants.make(
        //                 "jerk limit",
        //                 VStream.create(plot::getMouse)
        //                         .filtered(new VJerkLimit(10.0, 5.0))));

        while (plot.isRunning()) {
            plot.update();
            Thread.sleep(10);
        }
    }
}
