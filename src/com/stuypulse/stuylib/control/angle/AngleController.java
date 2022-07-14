package com.stuypulse.stuylib.control.angle;

import java.util.function.Function;

import com.stuypulse.stuylib.math.Angle;

public abstract class AngleController {
    protected Function<Angle, Double> mUnits;

    public AngleController() {
        useRadians();
    }

    public AngleController useUnits(Function<Angle, Double> angleUnits) {
        mUnits = angleUnits;
        return this;
    }

    public AngleController useRadians() {
        return useUnits(a -> a.toRadians());
    }

    public AngleController useDegrees() {
        return useUnits(a -> a.toDegrees());
    }

    public abstract double update(Angle setpoint, Angle measurement);
}
