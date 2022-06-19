/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.streams.filters.*;
import com.stuypulse.stuylib.streams.vectors.VStream;
import com.stuypulse.stuylib.streams.vectors.filters.VJerkLimit;

public final class Playground {

    private interface Constants {

        int TIME = 200;
        int MAX = +1;
        int MIN = -1;

        static GraphData make(String name) {
            return new GraphData(name, TIME, MIN, MAX);
        }
    }

    public static void main(String... args) throws InterruptedException {
        System.out.println("Testing graph library...");

        double accel = 1.0;
        double jerk = 1.0;

        JGraph poop = new JGraph(new FilteredGraphData(Constants.make("poop"), x -> x));

        VStream mouse =
                VStream.create(
                        () -> (poop.getMouseTracker().getMouseX() - 0.5) * 2,
                        () -> (poop.getMouseTracker().getMouseY() - 0.5) * 2);
        ;
        VStream filtered_mouse = mouse.filtered(new VJerkLimit(accel, jerk));

        JGraph graph =
                new JGraph(
                        new FilteredGraphData(Constants.make("mouse_x"), x -> mouse.get().x),
                        new FilteredGraphData(Constants.make("mouse_y"), x -> mouse.get().y),
                        new FilteredGraphData(
                                Constants.make("filtered_mouse_x"), x -> filtered_mouse.get().x),
                        new FilteredGraphData(
                                Constants.make("filtered_mouse_y"), x -> filtered_mouse.get().y));

        // mouse = () -> (System.currentTimeMillis() % 4000 > 2000 ? 1.0 : 0.0);
        for (; ; ) {
            graph.update(0.0);
            poop.update();

            Thread.sleep(20);
        }
    }
}
