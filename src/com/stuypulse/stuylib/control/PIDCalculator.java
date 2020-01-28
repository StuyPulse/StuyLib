package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.streams.filters.RollingAverage;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * This is a Bang-Bang controller that while controlling the robot, will be able
 * to calculate the values for the PID controller. It does this by taking the
 * results of oscillations, then creating a PIDController withe the correct
 * values once the oscilations have been measured.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PIDCalculator extends Controller {

    // Maximum amount of time between update commands before the calculator resets
    // its measurements
    private static final double kMaxTimeBeforeReset = 0.25;

    // The exponential weights when measuring the period and amplitude
    private static final double kPeriodAverageWeight = 4;
    private static final double kAmplitudeAverageWeight = 4;

    // The speed that the bang bang controller will run at
    private double mControlSpeed;

    // The results of the period and amplitude
    private double mPeriod;
    private double mAmplitude;

    // The filters used to average the period and amplitudes
    private IStreamFilter mPeriodFilter;
    private IStreamFilter mAmplitudeFilter;

    // Timer that keeps track of the length of a period
    private StopWatch mPeriodTimer;

    // The min and max of the wave
    private double mWaveMax;
    private double mWaveMin;

    // Timer that measures time between updates
    private StopWatch mUpdateTimer;

    // The last error given to the controller
    private double mLastError;

    // Whether or not the system will measure the oscilation
    private boolean mRunning;

    /**
     * @param speed motor output for bang bang controller
     */
    public PIDCalculator(double speed) {
        mControlSpeed = speed;

        mPeriod = 0;
        mPeriodFilter = new RollingAverage(kPeriodAverageWeight);

        mAmplitude = 0;
        mAmplitudeFilter = new RollingAverage(kAmplitudeAverageWeight);

        mPeriodTimer = new StopWatch();
        mWaveMax = 0;
        mWaveMin = 0;
        mLastError = 0;

        mUpdateTimer = new StopWatch();
        mRunning = false;
    }

    /**
     * @param speed sets speed for motor output of controller
     */
    public void setControlSpeed(double speed) {
        mControlSpeed = speed;
    }

    /**
     * Calculate the value that the controller wants to move at while calculating
     * the values for the PIDController
     * 
     * @param error the error that the controller will use
     * @return the calculated result from the controller
     */
    public double update(double error) {
        if (mUpdateTimer.reset() > kMaxTimeBeforeReset) {
            mRunning = false;
        }

        if (mLastError < 0.0 && 0.0 <= error) {
            if (mRunning) {
                mPeriod = mPeriodFilter.get(mPeriodTimer.reset());
                mAmplitude = mAmplitudeFilter.get((Math.abs(mWaveMax) + Math.abs(mWaveMin)) / 2.0);

                mWaveMax = 0;
                mWaveMin = 0;
            } else {
                mPeriodTimer.reset();
                mWaveMax = 0;
                mWaveMin = 0;
            }

            mRunning = true;
        }

        mWaveMax = Math.max(mWaveMax, error);
        mWaveMin = Math.min(mWaveMin, error);

        return Math.signum(mLastError = error) * mControlSpeed;
    }

    /**
     * @return calculated P controller based off of measurements
     */
    public PIDController getPController() {
        if (mAmplitude > 0) {
            double k = (4.0 * mControlSpeed) / (Math.PI * mAmplitude);

            return new PIDController(0.5 * k, -1, -1);
        } else {
            return new PIDController(-1, -1, -1);
        }
    }

    /**
     * @return calculated PI controller based off of measurements
     */
    public PIDController getPIController() {
        if (mAmplitude > 0) {
            double t = mPeriod;
            double k = (4.0 * mControlSpeed) / (Math.PI * mAmplitude);

            return new PIDController(0.45 * k, 0.54 * k / t, -1);
        } else {
            return new PIDController(-1, -1, -1);
        }
    }

    /**
     * @return calculated PID controller based off of measurements
     */
    public PIDController getPIDController() {
        if (mAmplitude > 0) {
            double t = mPeriod;
            double k = (4.0 * mControlSpeed) / (Math.PI * mAmplitude);

            return new PIDController(0.6 * k, 1.2 * k / t, 3.0 * k * t / 40.0);
        } else {
            return new PIDController(-1, -1, -1);
        }
    }
}
