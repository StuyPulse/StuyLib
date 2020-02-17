package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.streams.filters.IStreamFilterGroup;
import com.stuypulse.stuylib.streams.filters.MovingAverage;
import com.stuypulse.stuylib.streams.filters.RollingAverage;
import com.stuypulse.stuylib.util.StopWatch;
import com.stuypulse.stuylib.math.SLMath;

/**
 * This is a Bang-Bang controller that while controlling the robot, will be able
 * to calculate the values for the PID controller. It does this by taking the
 * results of oscillations, then creating a PIDController withe the correct
 * values once the oscillations have been measured.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PIDCalculator extends Controller {

    // Maximum amount of time between update commands before the calculator
    // resets
    // its measurements
    private static final double kMaxTimeBeforeReset = 0.3;

    // The minimum period length that will be accepted as a valid period
    private static final double kMinPeriodTime = 0.2;
    
    // The filter easuring the period and amplitude
    private static IStreamFilter getMeasurementFilter() {
        // This is a mix between accuracy and speed of updating.
        // Takes about 6 periods to get accurate results
        return new IStreamFilterGroup(new MovingAverage(6),
                new RollingAverage(2));
    }

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
    private double mLocalMax;

    // Whether or not the system will measure the oscillation
    private boolean mRunning;
    
    private PIDTunerType mTuner;
    
    private double mDeadTime;

    /**
     * @param speed motor output for bang bang controller
     */
    public PIDCalculator(double speed) {
        mControlSpeed = speed;

        mPeriod = 0;
        mPeriodFilter = getMeasurementFilter();

        mAmplitude = 0;
        mAmplitudeFilter = getMeasurementFilter();

        mPeriodTimer = new StopWatch();
        mLocalMax = 0;
        
        mDeadTime = -1;

        mRunning = false;
    }

    /**
     * @param speed sets speed for motor output of controller
     * @return the calculated result from the PIDController
     */
    public PIDCalculator setControlSpeed(double speed) {
        mControlSpeed = speed;
        return this;
    }

    /**
     * Calculate the value that the controller wants to move at while
     * calculating the values for the PIDController
     *
     * @param error the error that the controller will use
     * @return the calculated result from the controller
     */
    @Override
    protected double calculate(double error) {
        // If there is a gap in updates, then disable until next period
        if(getRate() > kMaxTimeBeforeReset) {
            mRunning = false;
        }

        // Check if we crossed 0, ie, time for next update
        double sign = Math.signum(error);
        if((error * sign) < (getRawVelocity() * sign)) {
            // Get period and amplitude
            double period = mPeriodTimer.reset() * 2.0;
            double amplitude = mLocalMax;

            // If we are running and period is valid, record it
            if(mRunning && kMinPeriodTime < period) {
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
        return Math.signum(error) * mControlSpeed;
    }

    /**
     * Adjusted Amplitude of Oscillations
     *
     * @return Get calculated K value for PID value equation
     */
    public double getK() {
        return (4.0 * mControlSpeed) / (Math.PI * mAmplitude);
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
     * Set the type of PID tuning to calculate
     * 
     * @return Uses Builder notation, so returns instance of itself
    */
    public PIDCalculator setTunerType(PIDTunerType tuner) {
        this.mTuner = tuner;
        return this;
    }
    
    /**
     * Gets the 
     * @return 
     */
    public PIDTunerType getTunerType() {
        return mTuner;
    }

    //TODO: Fix Javadoc b/c Winston wrote it, and he's dumb
    /**
     * This method is used to return corrected PID values
     * Use setTunerType() to change the tuner type. Default is Ziegler Nichols
     * 
     * @param kP p multiplier when calculating values
     * @param kI i multiplier when calculating values
     * @param kD d multiplier when calculating values
     * @return calculated PID controller based off of measurements
     */
    public PIDController getPIDController(double kP, double kI, double kD) {
        kP = Math.max(kP, 0.0);
        kI = Math.max(kI, 0.0);
        kD = Math.max(kD, 0.0);

        if( > 0 && mDeadTime < 0)
            mDeadTime = mPeriodTimer.getTime();
        if(mAmplitude > 0 && getTunerType() == PIDTunerType.ZIEGLER_NICHOLS) {
            double t = getT();
            double k = getK();
            
            return new PIDController(kP * (k), kI * (k / t), kD * (k * t));
        } else {
            return new PIDController(-1, -1, -1);
        }
    }

    /**
     * @return calculated PID controller based off of measurements
     */
    public PIDController getPIDController() {
        return getPIDController(0.6, 1.2, 3.0 / 40.0);
    }

    /**
     * @return calculated PI controller based off of measurements
     */
    public PIDController getPIController() {
        return getPIDController(0.45, 0.54, -1);
    }

    /**
     * @return calculated PD controller based off of measurements
     */
    public PIDController getPDController() {
        return getPIDController(0.8, -1, 1.0 / 10.0);
    }

    /**
     * @return calculated P controller based off of measurements
     */
   public PIDController getPController() {
        return getPIDController(0.5, -1, -1);
    }

    /**
     * @return information about this PIDController
     */
    public String toString() {
        return "(K: " + SLMath.round(getK(), 4) + ", T: "
                + SLMath.round(getT(), 4) + ") "
                + getPIDController().toString();
    }
}
