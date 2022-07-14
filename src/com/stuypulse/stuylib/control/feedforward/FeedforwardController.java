package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;

public abstract class FeedforwardController extends Controller {
    private static class AngleFeedforwardController extends AngleController {
        private final FeedforwardController mFeedforwardController;

        public AngleFeedforwardController(FeedforwardController feedforwardController) {
            mFeedforwardController = feedforwardController;
        }

        public double update(Angle setpoint, Angle measurement) {
            return mFeedforwardController.calculate(mUnits.apply(setpoint), mUnits.apply(measurement));
        }
    }

    public AngleFeedforwardController angle() {
        return new AngleFeedforwardController(this);
    }
}
