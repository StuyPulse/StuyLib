/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.math.interpolation.Interpolator;
import com.stuypulse.stuylib.math.interpolation.NearestInterpolator;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.vectors.VStream;
import com.stuypulse.stuylib.util.plot.Series.Config;

import edu.wpi.first.math.util.Units;

public class Playground {

    public interface Constants {
        int DURATION = 100;

        public static Series make(String id, IStream stream) {
            return Series.make(new Config(id, DURATION), stream);
        }

        public static Series make(String id, VStream stream) {
            return Series.make(new Config(id, DURATION), stream);
        }
    }

    public interface Distances {
        double RING = Units.inchesToMeters(150);
        double POINT_A = Units.inchesToMeters(167);
        double POINT_B = Units.inchesToMeters(184);
        double LAUNCHPAD = Units.inchesToMeters(217);
    }

    public interface RPMs {
        double RING = 2950;
        double POINT_A = 3050;
        double POINT_B = 3200;
        double LAUNCHPAD = 3650;
    }

    public interface Yaws {
        double RING = 6.50694;
        double POINT_A = 6.25;
        double POINT_B = 5.70;
        double LAUNCHPAD = 5;
    }

    public interface FirstShotMap {
        Interpolator DIST_TO_RPM =
            new NearestInterpolator(
                new Vector2D(Distances.RING, RPMs.RING),  
                new Vector2D(Distances.LAUNCHPAD, RPMs.LAUNCHPAD));

        Interpolator DIST_TO_YAW =
            new NearestInterpolator(
                new Vector2D(Distances.RING, Yaws.RING),
                new Vector2D(Distances.LAUNCHPAD, Yaws.LAUNCHPAD));
    }

    public interface SecondShotMap {
        Interpolator DIST_TO_RPM =
            new NearestInterpolator(
                new Vector2D(Distances.RING, RPMs.RING),
                new Vector2D(Distances.POINT_A, RPMs.POINT_A),
                new Vector2D(Distances.POINT_B, RPMs.POINT_B),    
                new Vector2D(Distances.LAUNCHPAD, RPMs.LAUNCHPAD));

        Interpolator DIST_TO_YAW =
            new NearestInterpolator(
                new Vector2D(Distances.RING, Yaws.RING),
                new Vector2D(Distances.POINT_A, Yaws.POINT_A),
                new Vector2D(Distances.POINT_B, Yaws.POINT_B),
                new Vector2D(Distances.LAUNCHPAD, Yaws.LAUNCHPAD));
    }

    public static void main(String[] args) throws InterruptedException {
        double max_dist = 8;

        Settings settings = new Settings();

        settings.setXMax(8);

        // rpm range
        settings.setYMin(1000);
        settings.setYMax(4000);

        Plot plot = new Plot(settings);

        // mouse input on y axis is distance, output is time plot of rpm
        // IStream mouse_y = IStream.create(plot.getMouse()::getY).filtered(x -> x * mouse_multiplier);
        // plot.addSeries(Constants.make("ring shot", () -> RPMs.RING))
        //         .addSeries(Constants.make("pad", () -> RPMs.LAUNCHPAD))
        //         .addSeries(Constants.make("first rpm", mouse_y.filtered(FirstShotMap.DIST_TO_RPM)))
        //         .addSeries(Constants.make("second rpm", mouse_y.filtered(SecondShotMap.DIST_TO_RPM)));
                
        // mouse input on x axis is distance, output is distance, rpm plot
        IStream mouse_x = IStream.create(plot.getMouse()::getX).filtered(x -> x * max_dist);
        plot.addSeries(Constants.make("ring shot", () -> new Vector2D(Distances.RING, RPMs.RING)))
                .addSeries(Constants.make("pad", () -> new Vector2D(Distances.LAUNCHPAD, RPMs.LAUNCHPAD)))
                .addSeries(Constants.make("first rpm", VStream.create(mouse_x, mouse_x.filtered(FirstShotMap.DIST_TO_RPM))))
                .addSeries(Constants.make("second rpm", VStream.create(mouse_x, mouse_x.filtered(SecondShotMap.DIST_TO_RPM))));
                
        // mouse input on x axis is distance, outputs yaw, rpm plot
        // plot.addSeries(Constants.make("ring shot", () -> new Vector2D(Yaws.RING, RPMs.RING)))
        //         .addSeries(Constants.make("pad", () -> new Vector2D(Yaws.LAUNCHPAD, RPMs.LAUNCHPAD)))
        //         .addSeries(Constants.make("first", VStream.create(
        //             mouse_x.filtered(FirstShotMap.DIST_TO_YAW),
        //             mouse_x.filtered(FirstShotMap.DIST_TO_RPM))))
        //         .addSeries(Constants.make("second",VStream.create(
        //             mouse_x.filtered(SecondShotMap.DIST_TO_YAW),
        //             mouse_x.filtered(SecondShotMap.DIST_TO_RPM))));

        for (; ; ) {
            plot.update();
            Thread.sleep(20);
        }
    }
}
