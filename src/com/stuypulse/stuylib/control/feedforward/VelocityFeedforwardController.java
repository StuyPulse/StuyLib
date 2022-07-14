package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.streams.filters.Derivative;

public class VelocityFeedforwardController extends FeedforwardController {

    private final Derivative mAcceleration;
    private final Feedforward mFeedforward;
    
    public VelocityFeedforwardController(Feedforward feedforward) {
        mAcceleration = new Derivative();
        mFeedforward = feedforward;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        double velocity = setpoint;
        double acceleration = mAcceleration.get(velocity);
        return mFeedforward.calculate(velocity, acceleration);
    }
    
}
