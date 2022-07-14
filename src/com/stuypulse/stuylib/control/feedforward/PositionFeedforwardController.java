package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.streams.filters.Derivative;

public class PositionFeedforwardController extends Controller {

    private final Derivative mDerivative;
    private final Feedforward mFeedforward;

    public PositionFeedforwardController(Feedforward feedforward) {
        mDerivative = new Derivative();
        mFeedforward = feedforward;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        return mFeedforward.calculate(mDerivative.get(setpoint));
    }


}
