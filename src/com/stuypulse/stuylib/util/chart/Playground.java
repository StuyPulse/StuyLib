/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.streams.filters.*;

public final 2class Playground {

    private interface Constants {

        int TIME = 100;
        int MAX = +1;
        int MIN = -1;

        static GraphData make(String name) {
            return new GraphData(name, TIME, MIN, MAX);
        }
    }

    public static void main(String... args) throws InterruptedException {
        System.out.println("Testing graph library...");

        double RC = 0.2;

        GraphData[] inputs =
                new GraphData[] {
                    new FilteredGraphData(Constants.make("Control"), x -> x),
                    new FilteredGraphData(Constants.make("High Pass"), new HighPassFilter(RC)),
                    new FilteredGraphData(Constants.make("Low Pass"), new LowPassFilter(RC)),
                    new FilteredGraphData(Constants.make("Both"), x -> x)
                };

        JGraph graph = new JGraph(inputs);

        for (; ; ) {
            final double next = (graph.getMouseTracker().getMouseY() - 0.5) * 2;
            graph.update(next);

            Thread.sleep(50);
        }
    }
}
