/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util;

import com.stuypulse.stuylib.streams.filters.Derivative;

import edu.wpi.first.math.geometry.Rotation2d;

public class AngleVelocity {

    private Derivative velocity;

    public AngleVelocity() {
        velocity = new Derivative();
    }

    public double get(Rotation2d angle) {
        return velocity.get(angle.getDegrees());
    }
}
