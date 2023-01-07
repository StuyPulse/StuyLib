/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle.feedback;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.IFilterGroup;
import com.stuypulse.stuylib.streams.filters.TimedMovingAverage;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * This is a Bang-Bang controller that while controlling the robot, will be able to calculate the
 * values for the PID controller. It does this by taking the results of oscillations, then creating
 * a PIDController withe the correct values once the oscillations have been measured.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class AnglePIDCalculator extends AngleController {

    // Maximum amount of time between update commands before the calculator
    // resets its measurements
    private static final double kMaxTimeBeforeReset = 0.5;

    // The minimum period length that will be accepted as a valid period
    private static final double kMinPeriodTime = 0.05;

    // The filter easuring the period and amplitude
    private static IFilter getMeasurementFilter() {
        // This is a mix between accuracy and speed of updating.
        // Takes about 6 periods to get accurate results
        return new IFilterGroup(new TimedMovingAverage(30));
    }

    // Internal timer used by PID Calculator
    private StopWatch mTimer;

    // The speed that the bang bang controller will run at
    private Number mControlSpeed;
    private double mCurrentSpeed;

    // The results of the period and amplitude
    private double mPeriod;
    private double mAmplitude;

    // The filters used to average the period and amplitudes
    private IFilter mPeriodFilter;
    private IFilter mAmplitudeFilter;

    // Timer that keeps track of the length of a period
    private StopWatch mPeriodTimer;

    // The min and max of the wave
    private double mLocalMax;

    // Whether or not the system will measure the oscillation
    private boolean mRunning;

    /** @param speed motor output for bang bang controller */
    public AnglePIDCalculator(Number speed) {
        mTimer = new StopWatch();

        mControlSpeed = speed;
        mCurrentSpeed = mControlSpeed.doubleValue();

        mPeriod = 0;
        mPeriodFilter = getMeasurementFilter();

        mAmplitude = 0;
        mAmplitudeFilter = getMeasurementFilter();

        mPeriodTimer = new StopWatch();
        mLocalMax = 0;

        mRunning = false;
    }

    /**
     * @param speed sets speed for motor output of controller
     * @return the calculated result from the PIDController
     */
    public AnglePIDCalculator setControlSpeed(Number speed) {
        mControlSpeed = SmartNumber.setNumber(mControlSpeed, speed);
        return this;
    }

    protected double calculate(Angle setpoint, Angle measurement) {
        // Calculate error & time step
        double error = setpoint.sub(measurement).toRadians();
        double dt = mTimer.reset();

        // If there is a gap in updates, then disable until next period
        if (dt > kMaxTimeBeforeReset) {
            mRunning = false;
        }

        // Check if we crossed 0, ie, time for next update
        if (Math.signum(mCurrentSpeed) != Math.signum(error)) {
            // Update the controller
            mCurrentSpeed = mControlSpeed.doubleValue() * Math.signum(error);

            // Get period and amplitude
            double period = mPeriodTimer.reset() * 2.0;
            double amplitude = mLocalMax;

            // If we are running and period is valid, record it
            if (mRunning && kMinPeriodTime < period) {
                mPeriod = mPeriodFilter.get(period);
                mAmplitude = mAmplitudeFilter.get(amplitude);
            }

            // Reset everything
            mLocalMax = 0;
            mRunning = true;
        }

        // Calculate amplitude by recording maximum
        mLocalMax = Math.max(Math.abs(mLocalMax), Math.abs(error));

        // Return bang bang control
        return mCurrentSpeed;
    }

    /**
     * Adjusted Amplitude of Oscillations
     *
     * @return Get calculated K value for PID value equation
     */
    public double getK() {
        return (4.0 * mControlSpeed.doubleValue()) / (Math.PI * mAmplitude);
    }

    /**
     * Period of Oscillations
     *
     * @return Get calculated T value for PID value equation
     */
    public double getT() {
        return mPeriod;
    }

    /**
     * @param kP p multiplier when calculating values
     * @param kI p multiplier when calculating values
     * @param kD p multiplier when calculating values
     * @return calculated PID controller based off of measurements
     */
    private AnglePIDController getPIDController(double kP, double kI, double kD) {
        kP = Math.max(kP, 0.0);
        kI = Math.max(kI, 0.0);
        kD = Math.max(kD, 0.0);

        if (mAmplitude > 0) {
            double t = getT();
            double k = getK();

            return new AnglePIDController(kP * (k), kI * (k / t), kD * (k * t));
        } else {
            return new AnglePIDController(-1, -1, -1);
        }
    }

    /** @return calculated PID controller based off of measurements */
    public AnglePIDController getPIDController() {
        return getPIDController(0.6, 1.2, 3.0 / 40.0);
    }

    /** @return calculated PI controller based off of measurements */
    public AnglePIDController getPIController() {
        return getPIDController(0.45, 0.54, -1);
    }

    /** @return calculated PD controller based off of measurements */
    public AnglePIDController getPDController() {
        return getPIDController(0.8, -1, 1.0 / 10.0);
    }

    /** @return calculated P controller based off of measurements */
    public AnglePIDController getPController() {
        return getPIDController(0.5, -1, -1);
    }

    /** @return information about this PIDController */
    public String toString() {
        return "(K: "
                + SLMath.round(getK(), 4)
                + ", T: "
                + SLMath.round(getT(), 4)
                + ") "
                + getPIDController().toString();
    }
}
