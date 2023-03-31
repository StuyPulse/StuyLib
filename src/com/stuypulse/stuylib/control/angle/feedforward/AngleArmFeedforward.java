/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle.feedforward;

import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A feedforward term to account for gravity for motorized arms that can move continuously (if not
 * use `ArmFeedforward`)
 *
 * <p>The motor feedforward used in the context of an arm will not account for gravity that is
 * acting on the arm.
 *
 * <p>Can be paired with MotorFeedforward or other controllers with .add
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class AngleArmFeedforward extends AngleController {

    /** voltage to hold arm horizontal */
    private final Number kG;

    /**
     * Create arm feedforward
     *
     * @param kG term to hold arm vertical against gravity (volts)
     */
    public AngleArmFeedforward(Number kG) {
        this.kG = kG;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Angle Arm Feed Forward");
        builder.addDoubleProperty("Setpoint", () -> getSetpoint().toDegrees(), null);
        builder.addDoubleProperty("Measurement", () -> getMeasurement().toDegrees(), null);
        builder.addDoubleProperty("Output", this::getOutput, null);
        builder.addDoubleProperty("Error", () -> getError().toDegrees(), null);
    }

    /**
     * Calculates voltage to hold arm at the setpoint angle
     *
     * @param setpoint setpoint
     * @param measurement measurement
     * @return kG * cos(setpoint)
     */
    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        return kG.doubleValue() * setpoint.cos();
    }
}
