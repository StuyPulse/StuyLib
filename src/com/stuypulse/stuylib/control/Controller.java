package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.streams.filters.IFilter;

public abstract class Controller {

    private double mSetpoint;
    private double mMeasurement;

    private IFilter mSetpointFilter;
    private IFilter mMeasurementFilter;
    private IFilter mOutputFilter;

    public Controller() {
        mSetpoint = 0.0;
        mMeasurement = 0.0;

        mSetpointFilter = x -> x;
        mMeasurementFilter = x -> x;
        mOutputFilter = x -> x;
    }

    public Controller setSetpointFilter(IFilter setpointFilter) {
        mSetpointFilter = setpointFilter;
        return this;
    }

    public Controller setMeasurementFilter(IFilter measurementFilter) {
        mMeasurementFilter = measurementFilter;
        return this;
    }

    public Controller setOutputFilter(IFilter outputFilter) {
        mOutputFilter = outputFilter;
        return this;
    }

    public double getSetpoint() {
        return mSetpoint;
    }

    public double getMeasurement() {
        return mMeasurement;
    }

    public double getError() {
        return getSetpoint() - getMeasurement();
    }

    public boolean isDone(double acceptableError) {
        return Math.abs(getError()) < acceptableError;
    }

    public Controller and(Controller other) {
        return new InlineController((s, m) -> this.update(s, m) + other.update(s, m));
    }

    public double update(double setpoint, double measurement) {
        mSetpoint = mSetpointFilter.get(setpoint);
        mMeasurement = mMeasurementFilter.get(measurement);

        double output = calculate(mSetpoint, mMeasurement);
        return mOutputFilter.get(output);
    }

    protected abstract double calculate(double setpoint, double measurement);

}
