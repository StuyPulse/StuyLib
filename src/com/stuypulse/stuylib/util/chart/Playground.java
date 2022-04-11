/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.chart;

import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.math.interpolation.CubicInterpolator;
import com.stuypulse.stuylib.math.interpolation.Interpolator;
import com.stuypulse.stuylib.math.interpolation.NearestInterpolator;
import com.stuypulse.stuylib.math.interpolation.PolyInterpolator;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.*;

public final class Playground {

    private interface Constants {

        int TIME = 400;
        int MAX = +1;
        int MIN = -1;

        static GraphData make(String name) {
            return new GraphData(name, TIME, MIN, MAX);
        }
    }

    public static Vector2D[] test_points = {
        new Vector2D(0, 5),
        new Vector2D(1, 1),
        new Vector2D(3, 10),
        new Vector2D(5, 2),
        new Vector2D(8, 5),
        new Vector2D(10, 2)  
};

    public static GraphData makeTest(String name, Interpolator interpolator) {
        GraphData test_graph = new GraphData(name, 100, -5, 15);

        for (double i = 0; i < 10; i += 0.1) {
                test_graph.update(interpolator.interpolate(i));
        }
    
        return test_graph;
}

    public static void main(String... args) throws InterruptedException {
        JGraph graph = new JGraph(
                // makeTest("Cubic", new CubicInterpolator(test_points)), 
                // makeTest("Linear", new NearestInterpolator(test_points)),
                makeTest("Poly", new PolyInterpolator(test_points))
        );

        graph.update();

    }
}