package com.stuypulse.stuylib.util.plot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Playground {
    public static void main(String[] args) throws InterruptedException {
        Plot plot = new Plot(new Settings());

        List<Double> x = new LinkedList<>();
        List<Double> y = new LinkedList<>();

        for (;;) {
            x.add(plot.getMouseTracker().getMouseX());
            y.add(plot.getMouseTracker().getMouseY());

            // while (x.size() > 250) x.remove(0);
            // while (y.size() > 250) y.remove(0);

            // plot.update("mouse", x, y);
            // plot.display();

            Thread.sleep(20);
        }

    }
}
