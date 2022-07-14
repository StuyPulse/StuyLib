package com.stuypulse.stuylib.control.feedback;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * This is a utility class for defining usually time-based feedback
 * control algorithms that work on error, rather than setpoint or measurement. 
 * 
 * @author Myles Pasetsky (@selym3)
 */
public abstract class FeedbackController extends Controller {
    
    private static class AngleFeedbackController extends AngleController {
        private final FeedbackController mFeedbackController;

        public AngleFeedbackController(FeedbackController feedbackController) {
            mFeedbackController = feedbackController;
        }

        @Override
        public double update(Angle setpoint, Angle measurement) {
            return mFeedbackController.calculate(mUnits.apply(setpoint.sub(measurement)));
        }
    }

    private final StopWatch mTimer;
    private double mRate;

    public FeedbackController() {
        mTimer = new StopWatch();
        mRate = 0.0;
    }

    public AngleFeedbackController angle() {
        return new AngleFeedbackController(this);
    }

    public final double getRate() {
        return mRate;
    }

    protected final double calculate(double setpoint, double measurement) {
        mRate = mTimer.reset();
        return calculate(setpoint - measurement);
    }

    protected abstract double calculate(double error);
}
