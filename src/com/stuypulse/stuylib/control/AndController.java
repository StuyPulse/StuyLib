package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;

public class AndController extends Controller {

    private static class AngleAndController extends AngleController {
        private final AngleController mControllerA;
        private final AngleController mControllerB;

        public AngleAndController(AngleController controllerA, AngleController controllerB) {
            mControllerA = controllerA;
            mControllerB = controllerB;
        }

        @Override
        public double update(Angle setpoint, Angle measurement) {
            return mControllerA.update(setpoint, measurement) + mControllerB.update(setpoint, measurement);
        }
    }

    private final Controller mControllerA;
    private final Controller mControllerB;

    public AndController(Controller controllerA, Controller controllerB) {
        mControllerA = controllerA;
        mControllerB = controllerB;
    }

    @Override
    public AngleController angle() {
        return new AngleAndController(mControllerA.angle(), mControllerB.angle());
    }

    @Override
    protected double calculate(double setpoint, double measurement) {
        return mControllerA.update(setpoint, measurement) + mControllerB.update(setpoint, measurement);
    }
    
}
