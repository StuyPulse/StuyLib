package com.stuypulse.stuylib.control;

/**
 * This is a take back half controller that is specifically made to tune shooter wheels. It is made
 * to be easy to tune, as it only has one gain parameter.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class TBHController extends Controller {

    // Constants used by the PID controller
    private double mGain;
    private double mTBH;
    private double mPreviousError;
    private double mOutput;

    /**
     * @param gain the gain in the take back half algorithm
     */
    public TBHController(double gain) {
        setGain(gain).reset();
    }

    /**
     * @param gain the gain in the take back half algorithm
     */
    public TBHController setGain(double gain) {
        mGain = gain;
        return this;
    }

    /**
     * @return the gain in the take back half algorithm
     */
    public double getGain() {
        return mGain;
    }

    /**
     * Resets the state of the take back half alorithm
     */
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
        mOutput = mGain * error;
        if((error < 0) != (mPreviousError < 0)) {
            mOutput += mTBH;
            mOutput *= 0.5;

            mTBH = mOutput;
            mPreviousError = error;
        }

        return mOutput;
    }
}
