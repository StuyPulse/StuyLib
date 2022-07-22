package com.stuypulse.stuylib.control.angle.feedforward;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.AngleVelocity;

public class AnglePositionFeedforwardController extends AngleController {

    private final Feedforward mFeedforward;
    private final AngleVelocity mDerivative;

    public AnglePositionFeedforwardController(Feedforward feedforward) {
        mFeedforward = feedforward;
        mDerivative = new AngleVelocity();
    }

    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        return mFeedforward.calculate(mDerivative.get(setpoint));
    }
    
}
