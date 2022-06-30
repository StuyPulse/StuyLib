package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.control.feedforward.Feedforward;

public class FeedforwardController extends Controller {

    private final Feedforward mFeedforward;
    
    public FeedforwardController(Feedforward feedforward) {
        mFeedforward = feedforward;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        return mFeedforward.calculate(setpoint);
    }
    
}
