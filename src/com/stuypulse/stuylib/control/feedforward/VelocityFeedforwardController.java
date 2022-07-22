package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;

public class VelocityFeedforwardController extends Controller {

    private final Feedforward mFeedforward;
    
    public VelocityFeedforwardController(Feedforward feedforward) {
        mFeedforward = feedforward;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        return mFeedforward.calculate(setpoint);
    }
    
}