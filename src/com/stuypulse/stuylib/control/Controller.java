package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;

/**
 * The controller class is an abstract class that is used to create different
 * controllers. All a class will need to do is implement update(double error),
 * and then the user would use the calculate() functions, which work as a sort
 * of wrapper for the controllers. No matter what controller is used, these
 * functions will always work
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public abstract class Controller {

    /**
     * This function checks to see if a filter is null, if it is, it replaces it
     * with a default filter that doesn't do anything.
     * 
     * @param filter filter you want sanatized
     * @return sanatized filter
     */
    private static final IStreamFilter sanatize(IStreamFilter filter) {
        return (filter == null) ? (x -> x) : filter;
    }

    // Error and Error Filters
    private double mError;
    private IStreamFilter mErrorFilter;

    // Output and Output Filters
    private double mOutput;
    private IStreamFilter mOutputFilter;

    /**
     * Creates a basic controller with everthing initialized
     */
    protected Controller() {
        mError = 0.0;
        setErrorFilter(null);

        mOutput = 0.0;
        setOutputFilter(null);
    }

    /**
     * Lets you specify a filter that will be applied to all measurements that are
     * given to the controller.
     * 
     * A highly recommended option is the LowPassFilter due to its ability to remove
     * noise, and its ability to not lag behind when there has been a gap in calls.
     * This will slightly degrade the performance of the controller, but it may be
     * nessisary sometimes in order to get consistant results.
     * 
     * This will change the way the controller interacts with the robot and may
     * require more tuning to be done.
     * 
     * Passing in null will disable the filter.
     * 
     * @param filter filter to be applied to error measurements
     * @return refrence to the controller (so you can chain the commands together)
     */
    public final Controller setErrorFilter(IStreamFilter filter) {
        mErrorFilter = sanatize(filter);
        return this;
    }

    /**
     * Lets you specify a filter that will be applied to all of the outputs of the
     * controller.
     * 
     * If the robot has a tendancy to jerk, or motions of the robot are too violent,
     * a filter like the LowPassFilter can reduce jerk while still letting the robot
     * get to max speed. This will slightly degrade the performance of the
     * controller, but it may be nessisary sometimes in order to get consistant
     * results.
     * 
     * This will change the way the controller interacts with the robot and may
     * require more tuning to be done.
     * 
     * Passing in null will disable the filter.
     * 
     * @param filter filter to be applied to the outputs of the controller
     * @return refrence to the controller (so you can chain the commands together)
     */
    public final Controller setOutputFilter(IStreamFilter filter) {
        mOutputFilter = sanatize(filter);
        return this;
    }

    /**
     * Gets the error from the last time that .calculate() was called
     * 
     * @return error from the last time that .calculate() was called
     */
    public final double getError() {
        return mError;
    }

    /**
     * Gets the motor output from the last time that .calculate() was called
     * 
     * @return the motor output from the last time that .calculate() was called
     */
    public final double getOutput() {
        return mOutput;
    }

    /**
     * Update the controller with the measurement that was just made and the set
     * point you would like it to approach
     * 
     * @param measurement measurement of device just made
     * @param setpoint    desired result
     * @return controller output
     */
    public final double calculate(double measurement, double setpoint) {
        return calculate(measurement - setpoint);
    }

    /**
     * Update the controller with the measurement that was just made and the set
     * point you would like it to approach
     * 
     * @param error the amount of error from the destination
     * @return controller output
     */
    public final double calculate(double error) {
        mError = mErrorFilter.get(error);
        mOutput = mOutputFilter.get(update(mError));
        return mOutput;
    }

    /**
     * Update the controller with the error just measured
     * 
     * @param error error that was just measured
     * @return controller output.
     */
    protected abstract double update(double error);
}