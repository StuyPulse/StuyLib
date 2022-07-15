package com.stuypulse.stuylib.control.feedforward;

public class VelocityFeedforwardController extends FeedforwardController {

    private final Feedforward mFeedforward;
    
    public VelocityFeedforwardController(Feedforward feedforward) {
        mFeedforward = feedforward;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        return mFeedforward.calculate(setpoint);
    }
    
}
