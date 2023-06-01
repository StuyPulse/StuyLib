/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
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

        VStream m = VStream.create(() -> plot.getMouse().sub(new Vector2D(0.5, 0.5)).mul(2));
        
        while (plot.isRunning()) {
            plot.update();
            Thread.sleep(10);
        }
    }
}
