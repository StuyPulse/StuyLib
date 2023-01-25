package com.stuypulse.stuylib.control.feedforward;

import com.stuypulse.stuylib.control.Controller;

/**
 * A feedforward term to account for gravity for motorized
 * lifts.
 * 
 * The motor feedforward used in the context of a lift
 * will not account for gravity that is acting on the lift. 
 * 
 * Can be paired with MotorFeedforward or other controllers
 * with .add
 * 
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class ElevatorFeedforward extends Controller {

    /** voltage to hold arm in place against gravity */
    private final Number kG;

    /**
     * Create ElevatorFeedforward
     * 
     * @param kG voltage to hold lift
     */
    public ElevatorFeedforward(Number kG) {
        this.kG = kG;
    }

    /**
     * Calculate voltage to hold elevator at setpoint height
     * 
     * @param setpoint  setpoint
     * @param measurement measurement
     * @return kG
     */
    @Override
    protected double calculate(double setpoint, double measurement) {
        return kG.doubleValue();
    }
    
}
