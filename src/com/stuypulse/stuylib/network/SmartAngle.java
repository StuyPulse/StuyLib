package com.stuypulse.stuylib.network;

import java.util.function.Function;

import com.stuypulse.stuylib.math.Angle;

public class SmartAngle implements Angle {

    private SmartNumber mNumber;
    private Function<Double, Double> mToRadians;

    public SmartAngle(SmartNumber number) {
        mNumber = number;
        radians();
    }

    public SmartAngle radians() {
        mToRadians = x -> x;
        return this;
    }

    public SmartAngle degrees() {
        mToRadians = Math::toRadians;
        return this;
    }

    @Override
    public double toRadians() {
        return mToRadians.apply(mNumber.get());
    }
    
}
