/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * The controller class is an abstract class that is used to create different controllers. All a
 * class will need to do is implement calculate(double error), and then the user would use the
 * update() functions, which work as a sort of wrapper for the controllers. No matter what
 * controller is used, these functions will always work.
 *
 * <p>These functions in the controller are useful for an error based system, and are automatically
 * managed, making implementations of PID easy.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public abstract class Controller {

    /**
     * This function checks to see if a filter is null, if it is, it replaces it with a default
     * filter that doesn't do anything.
     *
     * @param filter filter you want sanitized
     * @return sanitized filter
     */
    private static final IFilter sanitize(IFilter filter) {
        return (filter == null) ? (x -> x) : filter;
    }

    // Error and Error Filters
    private double mError;
    private IFilter mErrorFilter;

    // Velocity, Raw Velocity and Velocity Filter
    private double mVelocity;
    private IFilter mVelocityFilter;
    private double mRawVelocity;

    // Output and Output Filters
    private double mOutput;
    private IFilter mOutputFilter;

    // Rate and Rate Timer
    private double mRate;
    private StopWatch mRateTimer;

    /** Creates a basic controller with everything initialized */
    protected Controller() {
        mError = 0.0;
        setErrorFilter(null);

        mRawVelocity = 0.0;
        mVelocity = 0.0;
        setVelocityFilter(null);

        mOutput = 0.0;
        setOutputFilter(null);

        mRate = -1.0;
        mRateTimer = new StopWatch();
    }

    /**
     * Lets you specify a filter that will be applied to all measurements that are given to the
     * controller.
     *
     * <p>A highly recommended option is the LowPassFilter due to its ability to remove noise, and
     * its ability to not lag behind when there has been a gap in calls. This will slightly degrade
     * the performance of the controller, but it may be necessary sometimes in order to get
     * consistent results.
     *
     * <p>This will change the way the controller interacts with the robot and may require more
     * tuning to be done.
     *
     * <p>Passing in null will disable the filter.
     *
     * @param filter filter to be applied to error measurements
     * @return reference to the controller (so you can chain the commands together)
     */
    public final Controller setErrorFilter(IFilter filter) {
        mErrorFilter = sanitize(filter);
        return this;
    }

    /**
     * Lets you specify a filter that will be applied to the velocity measurements.
     *
     * <p>This can be used to smooth out the otherwise noisy velocity values.
     *
     * <p>This can negatively affect things like the D in PID if set too high and it is recommended
     * that you just filter the error instead.
     *
     * @param filter filter to be applied to velocity measurements
     * @return reference to the controller (so you can chain the commands together)
     */
    public final Controller setVelocityFilter(IFilter filter) {
        mVelocityFilter = sanitize(filter);
        return this;
    }

    /**
     * Lets you specify a filter that will be applied to all of the outputs of the controller.
     *
     * <p>If the robot has a tendency to jerk, or motions of the robot are too violent, a filter
     * like the LowPassFilter can reduce jerk while still letting the robot get to max speed. This
     * will slightly degrade the performance of the controller, but it may be necessary sometimes in
     * order to get consistent results.
     *
     * <p>This will change the way the controller interacts with the robot and may require more
     * tuning to be done.
     *
     * <p>Passing in null will disable the filter.
     *
     * @param filter filter to be applied to the outputs of the controller
     * @return reference to the controller (so you can chain the commands together)
     */
    public final Controller setOutputFilter(IFilter filter) {
        mOutputFilter = sanitize(filter);
        return this;
    }

    /**
     * Gets the error from the last time that .update() was called
     *
     * @return error from the last time that .update() was called
     */
    public final double getError() {
        return mError;
    }

    /**
     * Gets the velocity, which is represented as the change in error since the last time that
     * .update() was called
     *
     * @return velocity from the last time that .update() was called
     */
    public final double getRawVelocity() {
        return mRawVelocity;
    }

    /**
     * Gets the velocity from the last time that .update() was called adjusted to velocity per
     * second
     *
     * @return velocity from the last time that .update() was called
     */
    public final double getVelocity() {
        return mVelocity;
    }

    /**
     * Gets the motor output from the last time that .update() was called
     *
     * @return the motor output from the last time that .update() was called
     */
    public final double getOutput() {
        return mOutput;
    }

    /**
     * Gets the rate of the controller during the last .update() command. This will only return the
     * interval between the last .update() command and the one before it. Thus, the rate may be
     * slightly inconsistent if the update command is not called regularly.
     *
     * <p>This function may be overridden if a special controller needs a custom rate.
     *
     * @return the rate of the controller during the last .update() command
     */
    public double getRate() {
        return mRate;
    }

    /**
     * Get whether or not the Controller has arrived at the target.
     *
     * @param maxError the maximum amount of error allowed
     * @return if the controller has arrived at the target
     */
    public boolean isDone(double maxError) {
        return (Math.abs(getError()) < Math.abs(maxError));
    }

    /**
     * Get whether or not the Controller has arrived at the target.
     *
     * @param maxError the maximum amount of error allowed
     * @param maxVelocity the maximum amount of change in error over a second allowed
     * @return if the controller has arrived at the target
     */
    public boolean isDone(double maxError, double maxVelocity) {
        return ((Math.abs(getError()) < Math.abs(maxError))
                && (Math.abs(getVelocity()) < Math.abs(maxVelocity)));
    }

    /**
     * Update the controller with the measurement that was just made and the set point you would
     * like it to approach
     *
     * <p>This function just subtracts the two at this moment.
     *
     * @param setpoint desired result
     * @param measurement measurement of device just made
     * @return controller output
     */
    public final double update(double setpoint, double measurement) {
        return update(setpoint - measurement);
    }

    /**
     * Update the controller with the error from the destination that you want to reach
     *
     * @param error the amount of error from the destination
     * @return controller output
     */
    public final double update(double error) {
        // Update rate with the amount of time since the last update
        mRate = mRateTimer.reset();

        // Filter the error with the error filter
        error = mErrorFilter.get(error);

        // Get the velocity based on the change in error
        mRawVelocity = error - mError;
        mVelocity = mVelocityFilter.get(mRawVelocity / getRate());

        // Update the error variable
        mError = error;

        // Return and Update the calculated output
        return (mOutput = mOutputFilter.get(calculate(mError)));
    }

    /**
     * Update the controller with the error just measured
     *
     * @param error error that was just measured
     * @return controller output.
     */
    protected abstract double calculate(double error);

    /** Default to string funciton */
    public String toString() {
        return "(error: " + this.getError() + ")";
    }
}
