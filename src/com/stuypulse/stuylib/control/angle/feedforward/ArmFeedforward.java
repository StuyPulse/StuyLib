/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle.feedforward;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.streams.filters.Derivative;
import com.stuypulse.stuylib.util.AngleVelocity;

public class ArmFeedforward {
    private final Number kG, kS, kV, kA;

    private final AngleVelocity mVelocity;
    private final Derivative mAcceleration;

    public ArmFeedforward(Number kG, Number kS, Number kV, Number kA) {
        this.kG = kG;
        this.kS = kS;
        this.kV = kV;
        this.kA = kA;

        mVelocity = new AngleVelocity();
        mAcceleration = new Derivative();
    }

    public AngleArmPositionFeedforwardController angle() {
        return new AngleArmPositionFeedforwardController(this);
    }

    public double calculate(Angle position) {
        return calculate(position, mVelocity.get(position));
    }

    private double calculate(Angle position, double velocity) {
        return calculate(position, velocity, mAcceleration.get(velocity));
    }

    private double calculate(Angle position, double velocity, double acceleration) {
        return kG.doubleValue() * position.cos()
                + kS.doubleValue() * Math.signum(velocity)
                + kV.doubleValue() * velocity
                + kA.doubleValue() * acceleration;
    }
}
