/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.control.feedback.FeedbackController;
import com.stuypulse.stuylib.network.SmartNumber;

/**
 * The take back half algorithm is one made specifically to help with controlling shooters. The way
 * it works is it increases speed until it goes over the target, at which point it "takes back
 * half", ie. cuts the speed in half. Then it increases speed again, when it hits the target speed,
 * it takes back half again.
 *
 * <p>This way it is able to hit the target speed with accuracy.
 *
 * <p>The gain value is how fast or slow it should increase speed. Tuning is required but its not as
 * tedious as with PID.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class TBHController extends FeedbackController {

    private Number mGain;
    private double mTBH;
    private double mPreviousError;
    private double mOutput;

    /** @param gain the gain in the take back half algorithm */
    public TBHController(Number gain) {
        setGain(gain).reset();
    }

    /**
     * @param gain the gain in the take back half algorithm
     * @return an instance of the TBHController
     */
    public TBHController setGain(Number gain) {
        mGain = SmartNumber.setNumber(mGain, gain);
        return this;
    }

    /** @return the gain in the take back half algorithm */
    public double getGain() {
        return mGain.doubleValue();
    }

    /** Resets the state of the take back half alorithm */
    public void reset() {
        mTBH = 0;
        mPreviousError = 0;
        mOutput = 0;
    }

    /**
     * Calculate the value that the Take Back Half algorithm wants to move at.
     *
     * @param error the error that the controller will use
     * @return the calculated result from the Take Back Half algorithm
     */
    @Override
    protected double calculate(double error) {
        mOutput += getGain() * error * this.getRate();
        if ((error < 0) != (mPreviousError < 0)) {
            mOutput += mTBH;
            mOutput *= 0.5;

            mTBH = mOutput;
            mPreviousError = error;
        }

        return mOutput;
    }
}