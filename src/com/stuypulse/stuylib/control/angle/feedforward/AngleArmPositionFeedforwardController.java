package com.stuypulse.stuylib.control.angle.feedforward;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;

public class AngleArmPositionFeedforwardController extends AngleController  {

    private final ArmFeedforward mArmFeedforward;

    public AngleArmPositionFeedforwardController(ArmFeedforward armFeedforward) {
        mArmFeedforward = armFeedforward;
    }
    
    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        return mArmFeedforward.calculate(setpoint);
    }
    
}
