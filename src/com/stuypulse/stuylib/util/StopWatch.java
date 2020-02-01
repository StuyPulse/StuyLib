package com.stuypulse.stuylib.util;

/**
 * This StopWatch class helps classes who want their functions to be time
 * independent do that by giving them an easy way to get intervals of time.
 * 
 * This is better than just doing it in the class because it stores the time as
 * a long to keep accuracy, but converts it into a double for convenience.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class StopWatch {

    // These are the functions that provide the time data. If you wish to change the
    // way that the StopWatch class gets its time data, change that here.
    private static double kTimerScale = 1_000_000_000;

    private static long getCurrentTime() {
        return System.nanoTime();
    }

    private static double toSeconds(long time) {
        return Math.max(time, 1) / kTimerScale;
    }

    // The start time of the stopwatch
    // This is the return type of System.nanoTime.
    private long mLastTime;

    /**
     * Creates timer and reset it to now.
     */
    public StopWatch() {
        mLastTime = StopWatch.getCurrentTime();
    }

    /**
     * Resets the stop watch to the current time and returns time since last reset.
     * 
     * @return the time since the last reset was called in seconds. The result is
     *         always a non 0 positive number.
     */
    public double reset() {
        long time = StopWatch.getCurrentTime();
        long delta = time - mLastTime;
        mLastTime = time;
        return StopWatch.toSeconds(delta);
    }

    /**
     * Gets the time since the stop watch was reset
     * 
     * @return the time since the last reset was called in seconds. The result is
     *         always a non 0 positive number.
     */
    public double getTime() {
        long delta = StopWatch.getCurrentTime() - mLastTime;
        return StopWatch.toSeconds(delta);
    }
}