/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.WPIGamepad;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Implementation of the PS4 for the {@link com.stuypulse.stuylib.input.Gamepad} Class
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class PS4 extends WPIGamepad {

    // Constructor //
    public PS4(int port) {
        super(port);
    }

    public PS4(Joystick joystick) {
        super(joystick);
    }

    // Name //
    @Override
    public String getGamepadName() {
        return "Playstation Controller";
    }

    // Left Stick //
    @Override
    public double getLeftX() {
        return getRawAxis(0);
    }

    @Override
    public double getLeftY() {
        return -getRawAxis(1);
    }

    // Right Stick //
    @Override
    public double getRightX() {
        return getRawAxis(2);
    }

    @Override
    public double getRightY() {
        return -getRawAxis(5);
    }

    // D-Pad //
    @Override
    public boolean getRawDPadUp() {
        return getJoystick().getPOV() == 0;
    }

    @Override
    public boolean getRawDPadDown() {
        return getJoystick().getPOV() == 180;
    }

    @Override
    public boolean getRawDPadLeft() {
        return getJoystick().getPOV() == 270;
    }

    @Override
    public boolean getRawDPadRight() {
        return getJoystick().getPOV() == 90;
    }

    // Bumpers //
    @Override
    public boolean getRawLeftBumper() {
        return getRawButton(5);
    }

    @Override
    public boolean getRawRightBumper() {
        return getRawButton(6);
    }

    // Triggers //
    @Override
    public double getLeftTrigger() {
        return (getRawAxis(3) + 1.0) / 2.0;
    }

    @Override
    public double getRightTrigger() {
        return (getRawAxis(4) + 1.0) / 2.0;
    }

    // Face Buttons //
    @Override
    public boolean getRawLeftButton() {
        return getRawButton(1);
    }

    @Override
    public boolean getRawBottomButton() {
        return getRawButton(2);
    }

    @Override
    public boolean getRawRightButton() {
        return getRawButton(3);
    }

    @Override
    public boolean getRawTopButton() {
        return getRawButton(4);
    }

    // Start / Select //
    @Override
    public boolean getRawSelectButton() {
        return getRawButton(9);
    }

    @Override
    public boolean getRawStartButton() {
        return getRawButton(10);
    }

    // Analog Stick Buttons //
    @Override
    public boolean getRawLeftStickButton() {
        return getRawButton(11);
    }

    @Override
    public boolean getRawRightStickButton() {
        return getRawButton(12);
    }
}
