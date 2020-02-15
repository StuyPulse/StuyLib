package com.stuypulse.stuylib.control;

public enum TunerType {
    COHEN_COON, ZIEGLER_NICHOLS,
    /**
     * Lambda method of tuning absorbs disturbances better, but has a slower
     * loop response
     */
    LAMBDA;
}
