/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;

/**
 * A wrapper for the XboxController class to work with the {@link Gamepad} interface.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class Xbox extends Gamepad {

    private XboxController mJoystick;

    private boolean flipped;

    // Constructor //
    public Xbox(XboxController joystick) {
        mJoystick = joystick;

        flipped = false;
    }

    public Xbox(int port) {
        this(new XboxController(port));
    }

    // Get the underlying XboxClass
    public XboxController getJoystick() {
        return mJoystick;
    }

    // Name //
    @Override
    public String getGamepadName() {
        return "Xbox Controller";
    }

    // Left Stick //
    @Override
    public double getLeftX() {
        return getJoystick().getLeftX();
    }

    @Override
    public double getLeftY() {
        return -getJoystick().getLeftY();
    }

    // Right Stick //
    @Override
    public double getRightX() {
        return (flipped ? +1 : -1) * getJoystick().getRightX();
    }

    @Override
    public double getRightY() {
        return (flipped ? +1 : -1) * getJoystick().getRightY();
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
        return getJoystick().getLeftBumper();
    }

    @Override
    public boolean getRawRightBumper() {
        return getJoystick().getRightBumper();
    }

    // Triggers //
    @Override
    public double getLeftTrigger() {
        return getJoystick().getLeftTriggerAxis();
    }

    @Override
    public double getRightTrigger() {
        return getJoystick().getRightTriggerAxis();
    }

    // Face Buttons //
    @Override
    public boolean getRawLeftButton() {
        return getJoystick().getXButton();
    }

    @Override
    public boolean getRawBottomButton() {
        return getJoystick().getAButton();
    }

    @Override
    public boolean getRawRightButton() {
        return getJoystick().getBButton();
    }

    @Override
    public boolean getRawTopButton() {
        return getJoystick().getYButton();
    }

    // Start / Select //
    @Override
    public boolean getRawSelectButton() {
        return getJoystick().getBackButton();
    }

    @Override
    public boolean getRawStartButton() {
        return getJoystick().getStartButton();
    }

    // Analog Stick Buttons //
    @Override
    public boolean getRawLeftStickButton() {
        return getJoystick().getLeftStickButton();
    }

    @Override
    public boolean getRawRightStickButton() {
        return getJoystick().getRightStickButton();
    }

    // Rumble
    @Override
    public void setRumble(double intensity) {
        mJoystick.setRumble(RumbleType.kLeftRumble, intensity);
        mJoystick.setRumble(RumbleType.kRightRumble, intensity);
    }

    public Xbox flipped() {
        flipped = true;
        return this;
    }
}
