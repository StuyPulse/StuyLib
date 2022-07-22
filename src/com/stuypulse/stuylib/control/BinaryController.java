package com.stuypulse.stuylib.control;

public class BinaryController extends Controller {

    private final Controller mControllerA;
    private final Controller mControllerB;

    public BinaryController(Controller controllerA, Controller controllerB) {
        mControllerA = controllerA;
        mControllerB = controllerB;
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        return mControllerA.update(setpoint, measurement) + mControllerB.update(setpoint, measurement);
    }
    
}
