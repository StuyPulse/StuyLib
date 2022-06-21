/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.*;

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

        GraphData[] inputs =
                new GraphData[] {
                    new FilteredGraphData(Constants.make("Control"), x -> x),
                    // new FilteredGraphData(Constants.make("Rate Limit"), new RateLimit(1.0)),
                    new FilteredGraphData(Constants.make("AAA"), new JerkLimit(accel, jerk)),
                    new FilteredGraphData(
                            Constants.make("BBB"),
                            new JerkLimit(accel, jerk).then(new Derivative())),
                    new FilteredGraphData(
                            Constants.make("CCC"),
                            new JerkLimit(accel, jerk)
                                    .then(new Derivative())
                                    .then(new Derivative())),
                };

        JGraph graph = new JGraph(inputs);

        IStream mouse = IStream.create(() -> (graph.getMouseTracker().getMouseY() - 0.5) * 2);
        // mouse = () -> (System.currentTimeMillis() % 4000 > 2000 ? 1.0 : 0.0);
        for (; ; ) {
            final double next = mouse.get();
            graph.update(next);

            Thread.sleep(20);
        }
    }
}
