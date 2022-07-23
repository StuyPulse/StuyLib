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
