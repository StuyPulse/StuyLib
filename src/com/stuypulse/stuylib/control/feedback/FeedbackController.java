package com.stuypulse.stuylib.control.feedback;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.util.StopWatch;

public abstract class FeedbackController extends Controller {
    
    private final StopWatch mTimer;
    private double mRate;

    public FeedbackController() {
        mTimer = new StopWatch();
        mRate = 0.0;
    }

    public final double getRate() {
        return mRate;
    }

    protected final double calculate(double setpoint, double measurement) {
        mRate = mTimer.reset();
        return calculate(setpoint, measurement);
    }

    protected abstract double calculate(double error);
}
