package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.vectors.VStream;
import com.stuypulse.stuylib.streams.vectors.filters.VJerkLimit;
import com.stuypulse.stuylib.streams.vectors.filters.VLowPassFilter;
import com.stuypulse.stuylib.streams.vectors.filters.VRateLimit;
import com.stuypulse.stuylib.util.plot.Series.Config;

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

    public static void main(String[] args) throws InterruptedException {
        Plot plot = new Plot();
        
        VStream mouse = plot.getMouse()::getPosition;
        IStream mouse_y = plot.getMouse()::getY;
        
        plot
            .addSeries(Constants.make("mouse", mouse))
            .addSeries(Constants.make("lpf", mouse.filtered(new VLowPassFilter(0.2))))
            .addSeries(Constants.make("rate", mouse.filtered(new VRateLimit(1.0))))
            .addSeries(Constants.make("jerk", mouse.filtered(new VJerkLimit(1.0, 10.0))))
        ;

        for (;;) {
            plot.update();
            Thread.sleep(20);
        }

    }
}
