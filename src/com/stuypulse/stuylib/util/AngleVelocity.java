package com.stuypulse.stuylib.util;

import com.stuypulse.stuylib.math.Angle;

public class AngleVelocity {
    
    private final StopWatch mTimer;
    private Angle mPreviousAngle;

    public AngleVelocity() {
        mTimer = new StopWatch();
        mPreviousAngle = Angle.kZero;
    }

    public double get(Angle angle) {
        double velocity = angle.velocityRadians(mPreviousAngle, mTimer.getTime());
        mPreviousAngle = angle;
        return velocity;
    }

}
