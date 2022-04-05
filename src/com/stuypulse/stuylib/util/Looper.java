/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util;

/** @author Sam (sam.belliveau@gmail.com) */
public class Looper extends Thread {

    private final Runnable mFunction;
    private final double mDT;

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
