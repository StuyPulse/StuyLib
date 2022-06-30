package com.stuypulse.stuylib.control;

public class InlineController extends Controller {

    public static interface Calculator {
        double update(double setpoint, double measurement);
    }

    private final Calculator mCalculator;

    public InlineController(Calculator calculator) {
        mCalculator = calculator;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        return mCalculator.update(setpoint, measurement);
    }
    
}
