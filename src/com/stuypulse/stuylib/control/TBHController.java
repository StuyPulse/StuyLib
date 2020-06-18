package com.stuypulse.stuylib.control;

/**
 * The take back half algorithm is one made specifically to help with controlling shooters. The way
 * it works is it increases speed until it goes over the target, at which point it "takes back
 * half", ie. cuts the speed in half. Then it increases speed again, when it hits the target speed,
 * it takes back half again.
 *
 * This way it is able to hit the target speed with accuracy.
 *
 * The gain value is how fast or slow it should increase speed. Tuning is required but its not as
 * tedious as with PID.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class TBHController extends Controller {

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
        mOutput += mGain * error;
        if((error < 0) != (mPreviousError < 0)) {
            mOutput += mTBH;
            mOutput *= 0.5;

            mTBH = mOutput;
            mPreviousError = error;
        }

        return mOutput;
    }
}
