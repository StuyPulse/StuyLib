/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util;

/**
 * A class that runs a function every dt seconds
 *
 * <p>If the function falls behind, this will speed up until its back on track. Due to the way it's
 * programmed, it will never fall behind schedule as long as the time to execute the function is
 * smaller than dt.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class Looper extends Thread {

    private final Runnable mFunction;
    private final double mDT;

    /**
     * @param function function to loop
     * @param dt how long to wait between every function call
     */
    public Looper(Runnable function, double dt) {
        mFunction = function;
        mDT = dt;
    }

    public void run() {
        final StopWatch timer = new StopWatch();
        double time = 0.0;

        timer.reset();
        while (!isInterrupted()) {
            mFunction.run();
            time += mDT - timer.reset();

            try {
                if (time > 0) sleep(time);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private static void sleep(double time) throws InterruptedException {
        long millis = (long) (time * 1_000);
        int nanos = (int) (time * 1_000_000_000L - millis * 1_000_000L);
        Thread.sleep(millis, nanos);
    }
}
