/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.math.interpolation.Interpolator;
import com.stuypulse.stuylib.math.interpolation.NearestInterpolator;
import com.stuypulse.stuylib.util.plot.Series.Config;
import com.stuypulse.stuylib.util.plot.TimeSeries.TimeSpan;

public class Playground {

    public interface Distances {
        double RING = 150;
        double POINT_A = 167;
        double POINT_B = 184;
        double LAUNCHPAD = 217;
    }

    public interface RPMs {
        double RING = 2950;
        double POINT_A = 3050;
        double POINT_B = 3200;
        double LAUNCHPAD = 3650;
    }

    public interface Interpolators {
        Interpolator TWO =
            new NearestInterpolator(
                new Vector2D(Distances.RING, RPMs.RING),  
                new Vector2D(Distances.LAUNCHPAD, RPMs.LAUNCHPAD));

        Interpolator FOUR =
            new NearestInterpolator(
                new Vector2D(Distances.RING, RPMs.RING),
                new Vector2D(Distances.POINT_A, RPMs.POINT_A),
                new Vector2D(Distances.POINT_B, RPMs.POINT_B),    
                new Vector2D(Distances.LAUNCHPAD, RPMs.LAUNCHPAD));
    }

    public interface Constants {
        int DURATION = 500;

        String TITLE = "RPM vs Distance";
        String X_AXIS = "Distance (inches)";
        String Y_AXIS = "RPM";

        int WIDTH = 640;
        int HEIGHT = 480;

        double MIN_X = Distances.RING;
        double MAX_X = Distances.LAUNCHPAD;

        double MIN_Y = RPMs.RING;
        double MAX_Y = RPMs.LAUNCHPAD;

        Settings SETTINGS = new Settings()
                .setSize(WIDTH, HEIGHT)
                .setAxes(TITLE, X_AXIS, Y_AXIS)
                .setXRange(MIN_X, MAX_X)
                .setYRange(MIN_Y, MAX_Y)
            ;

        public static Series make(String id, Interpolator i) {
            return new FuncSeries(
                new Config(id, DURATION), 
                new TimeSpan(MIN_X, MAX_X), 
                i
            );
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Plot plot = new Plot(Constants.SETTINGS);

        plot.addSeries(Constants.make("2 points", Interpolators.TWO));
        plot.addSeries(Constants.make("4 points", Interpolators.FOUR));

        while (plot.isRunning()) {
            plot.update();
            Thread.sleep(20);
        }
    }
}
