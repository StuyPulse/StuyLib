/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.util.StopWatch;

import edu.wpi.first.wpilibj.GenericHID.HIDType;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class was created as a saftey measure to prevent issues that arise when the wrong gamepad is
 * plugged in. After the robot smashed into a wall because we plugged in a PS4 controller when the
 * code expected a Logitech controller, it was deemed that we needed this class. This class will
 * ONLY RETURN SAFE VALUES, it will never return negatives or unusual values. This cannot be said
 * about other gamepad classes.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public final class AutoGamepad extends Gamepad {

    // The amount of time that the gamepad will go before checking the type
    private static final double MIN_CONTROLLER_CHECK = 2.5;

    // All of the Different Gamepad classes that AutoGamepad Supports
    // Logitech XMode is not here as it is the same as an Xbox Controller
    private final int port;
    private final Joystick mJoystick;
    private final Gamepad mLogitech;
    private final Gamepad mPS4;
    private final Gamepad mXbox;
    private final Gamepad mNull;

    // The gamepad that will be used, and Stopwatch to track updating it
    private StopWatch mTimer;
    private Gamepad mCurrent;

    /** @param port the port that the gamepad should read from */
    public AutoGamepad(int port) {
        this.port = port;
        mJoystick = new Joystick(this.port);
        mLogitech = new Logitech.DMode(mJoystick);
        mPS4 = new PS4(mJoystick);
        mXbox = new Xbox(port);
        mNull = new Gamepad();

        mTimer = new StopWatch();
        mCurrent = mNull;
    }

    /** @return the correct type of gamepad coming from driverstation directly */
    private Gamepad forceDetectGamepad() {
        // Check if joystick is connected
        if (!mJoystick.isConnected()) return mNull;

        // Get Type of Joystick
        HIDType type = mJoystick.getType();

        // Return the right gamepad based on the gamepad type
        if (type == null) return mNull;
        else {
            switch (type) {
                case kXInputGamepad:
                    return mXbox;
                case kHIDJoystick:
                    return mLogitech;
                case kHIDGamepad:
                    return mPS4;
                default:
                    return mNull;
            }
        }
    }

    /** @return the internal gamepad class that this gamepad will be reading from */
    public Gamepad getDetectedGamepad() {
        if (mCurrent == mNull || MIN_CONTROLLER_CHECK < mTimer.getTime()) {
            mTimer.reset();
            mCurrent = forceDetectGamepad();
        }

        return mCurrent;
    }

    /***************************/
    /*** Gamepad Passthrough ***/
    /***************************/

    // Name //
    @Override
    public String getGamepadName() {
        return "AutoGamepad (" + getDetectedGamepad().getGamepadName() + ")";
    }

    // Left Stick //
    public double getLeftX() {
        return SLMath.clamp(getDetectedGamepad().getLeftX(), -1.0, 1.0);
    }

    public double getLeftY() {
        return SLMath.clamp(getDetectedGamepad().getLeftY(), -1.0, 1.0);
    }

    // Right Stick //
    public double getRightX() {
        return SLMath.clamp(getDetectedGamepad().getRightX(), -1.0, 1.0);
    }

    public double getRightY() {
        return SLMath.clamp(getDetectedGamepad().getRightY(), -1.0, 1.0);
    }

    // D-Pad //
    public boolean getRawDPadUp() {
        return getDetectedGamepad().getRawDPadUp();
    }

    public boolean getRawDPadDown() {
        return getDetectedGamepad().getRawDPadDown();
    }

    public boolean getRawDPadLeft() {
        return getDetectedGamepad().getRawDPadLeft();
    }

    public boolean getRawDPadRight() {
        return getDetectedGamepad().getRawDPadRight();
    }

    // Bumpers //
    public boolean getRawLeftBumper() {
        return getDetectedGamepad().getRawLeftBumper();
    }

    public boolean getRawRightBumper() {
        return getDetectedGamepad().getRawRightBumper();
    }

    // Triggers //
    public double getLeftTrigger() {
        return SLMath.clamp(getDetectedGamepad().getLeftTrigger(), 0.0, 1.0);
    }

    public double getRightTrigger() {
        return SLMath.clamp(getDetectedGamepad().getRightTrigger(), 0.0, 1.0);
    }

    // Face Buttons //
    public boolean getRawLeftButton() {
        return getDetectedGamepad().getRawLeftButton();
    }

    public boolean getRawBottomButton() {
        return getDetectedGamepad().getRawBottomButton();
    }

    public boolean getRawRightButton() {
        return getDetectedGamepad().getRawRightButton();
    }

    public boolean getRawTopButton() {
        return getDetectedGamepad().getRawTopButton();
    }

    // Start / Select //
    public boolean getRawSelectButton() {
        return getDetectedGamepad().getRawSelectButton();
    }

    public boolean getRawStartButton() {
        return getDetectedGamepad().getRawStartButton();
    }

    // Analog Stick Buttons //
    public boolean getRawLeftStickButton() {
        return getDetectedGamepad().getRawLeftStickButton();
    }

    public boolean getRawRightStickButton() {
        return getDetectedGamepad().getRawRightStickButton();
    }
}
