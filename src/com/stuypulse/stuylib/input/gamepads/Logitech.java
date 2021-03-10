/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.WPIGamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Implementation of Logitech Controller and its 2 Modes for the Gamepad Class
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class Logitech extends WPIGamepad {

    // Constructor //
    protected Logitech(int port) {
        super(port);
    }

    protected Logitech(Joystick joystick) {
        super(joystick);
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

    /**
     * There is a switch on the back of the controller this is for when the switch is in the D
     * position
     */
    public static class DMode extends Logitech {

        // Constructor //
        public DMode(int port) {
            super(port);
        }

        public DMode(Joystick joystick) {
            super(joystick);
        }

        // Name //
        @Override
        public String getGamepadName() {
            return "Logitech DMode";
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
            return -getRawAxis(3);
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
            return getRawButton(7) ? 1.0 : 0.0;
        }

        @Override
        public double getRightTrigger() {
            return getRawButton(8) ? 1.0 : 0.0;
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

    /**
     * There is a switch on the back of the controller this is for when the switch is in the X
     * position.
     *
     * <p>Because of the use of XInput, this is equivilant to using {@link Xbox}. However if you are
     * using a logitech controller on XMode, use this class for readability.
     */
    public static class XMode extends Xbox {

        // Constructor //
        public XMode(int port) {
            super(port);
        }

        public XMode(XboxController joystick) {
            super(joystick);
        }

        // Name //
        @Override
        public String getGamepadName() {
            return "Logitech XMode";
        }
    }
}
