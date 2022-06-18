/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util;

import edu.wpi.first.wpilibj.RobotController;

/**
 * This StopWatch class helps classes who want their functions to be time independent do that by
 * giving them an easy way to get intervals of time.
 *
 * <p>This is better than just doing it in the class because it stores the time as a long to keep
 * accuracy, but converts it into a double for convenience.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class StopWatch {

    // Engine interface used to get the current time
    public interface TimeEngine {

        // Get the raw time as a long
        public long getRawTime();

        // Convert the integer time into a double
        public double toSeconds(long raw);
    }

    private TimeEngine mEngine;
    private long mLastTime;

    /**
     * Creates timer and reset it to now.
     *
     * @param engine the method that is used to get the current time
     */
    public StopWatch(TimeEngine engine) {
        mEngine = engine;
        mLastTime = mEngine.getRawTime();
    }

    /** Creates timer and reset it to now. */
    public StopWatch() {
        this(kNanoEngine);
    }

    /**
     * Resets the stop watch to the current time and returns time since last reset.
     *
     * @return the time since the last reset was called in seconds. The result is always a non 0
     *     positive number.
     */
    public double reset() {
        long time = mEngine.getRawTime();
        long delta = Math.max(1, time - mLastTime);
        mLastTime = time;
        return mEngine.toSeconds(delta);
    }

    /**
     * Gets the time since the stop watch was reset
     *
     * @return the time since the last reset was called in seconds. The result is always a non 0
     *     positive number.
     */
    public double getTime() {
        long delta = Math.max(1, mEngine.getRawTime() - mLastTime);
        return mEngine.toSeconds(delta);
    }

    /** This engine is used to get the current time with the system function System.nanoTime() */
    public static final TimeEngine kNanoEngine =
            new TimeEngine() {

                public long getRawTime() {
                    return System.nanoTime();
                }

                public double toSeconds(long raw) {
                    return raw / 1_000_000_000.0;
                }
            };

    /**
     * This engine is used to get the current time with the system function
     * System.currentTimeMillis()
     *
     * <p>This may have a lower resolution, but it is stable.
     */
    public static final TimeEngine kMillisEngine =
            new TimeEngine() {

                public long getRawTime() {
                    return System.currentTimeMillis();
                }

                public double toSeconds(long raw) {
                    return raw / 1_000.0;
                }
            };

    /**
     * This engine is used to get the current time with the WPI function Timer.getFPGATimestamp()
     *
     * <p>this is used if for whatever reason you need to use the WPI Timer
     */
    public static final TimeEngine kFPGATimestamp =
            new TimeEngine() {

                public long getRawTime() {
                    return RobotController.getFPGATime();
                }

                public double toSeconds(long raw) {
                    return raw / 1_000_000.0;
                }
            };
}
