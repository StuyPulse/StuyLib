/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.streams.filters.Derivative;

public class DerivativeController extends Controller {

    private final Controller mController;

    private final Derivative mSetpointDerivative;
    private final Derivative mMeasurementDerivative;

    public DerivativeController(Controller controller) {
        mController = controller;

        mSetpointDerivative = new Derivative();
        mMeasurementDerivative = new Derivative();
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        setpoint = mSetpointDerivative.get(setpoint);
        measurement = mMeasurementDerivative.get(measurement);

        return mController.calculate(setpoint, measurement);
    }
}
