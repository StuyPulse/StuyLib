package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.streams.filters.Derivative;

public class PositionFeedforwardController extends FeedforwardController {

    private final Derivative mVelocity;
    private final Derivative mAcceleration;
    private final Feedforward mFeedforward;

    public PositionFeedforwardController(Feedforward feedforward) {
        mVelocity = new Derivative();
        mAcceleration = new Derivative();
        mFeedforward = feedforward;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        double velocity = mVelocity.get(setpoint);
        double acceleration = mAcceleration.get(velocity);
        return mFeedforward.calculate(velocity, acceleration);
    }


}
