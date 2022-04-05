/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.booleans.filters.BButton;
import com.stuypulse.stuylib.streams.booleans.filters.BButtonRC;
import com.stuypulse.stuylib.streams.booleans.filters.BDebounce;
import com.stuypulse.stuylib.streams.booleans.filters.BFilter;
import com.stuypulse.stuylib.streams.filters.*;
import com.stuypulse.stuylib.util.Looper;

public final class Playground {

    private interface Constants {

        int TIME = 400;
        int MAX = +1;
        int MIN = -1;

        static GraphData make(String name) {
            return new GraphData(name, TIME, MIN, MAX);
        }
    }

    public static void main(String... args) throws InterruptedException {
        System.out.println("Testing graph library...");

        double RC = 0.2;

        IFilter hpf = IFilter.create();

        for (int i = 0; i < 8; ++i) {
            hpf =
                    hpf.then(
                            IFilter.create()
                                    .sub(
                                            new TimedMovingAverage(0.2)
                                                    .then(new TimedMovingAverage(0.2))));
        }

        GraphData[] inputs =
                new GraphData[] {
                    new FilteredGraphData(
                            Constants.make("Control"), IFilter.create(BFilter.create())),
                    new FilteredGraphData(
                            Constants.make("Pressed2"),
                            IFilter.create(new BButton.Pressed().then(new BDebounce.Falling(0.5)))),
                    new FilteredGraphData(
                            Constants.make("Released2"),
                            IFilter.create(
                                    new BButton.Released().then(new BDebounce.Falling(0.5)))),
                    new FilteredGraphData(
                            Constants.make("Pressed"), IFilter.create(new BButtonRC.Pressed(0.5))),
                    new FilteredGraphData(
                            Constants.make("Released"),
                            IFilter.create(new BButtonRC.Released(0.5))),
                };

        JGraph graph = new JGraph(inputs);

        final IStream mouse = IStream.create(() -> (graph.getMouseTracker().getMouseY() - 0.5) * 2);
        Looper loop =
                new Looper(
                        () -> {
                            final double next = mouse.get();
                            graph.update(next);
                        },
                        0.02);

        loop.start();
        loop.join();
    }
}
