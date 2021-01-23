package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * A wrapper for the XboxController class to work with the gamepad interface.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */

public class Xbox extends Gamepad {

    private XboxController mJoystick;

    // Constructor //
    public Xbox(XboxController joystick) {
        mJoystick = joystick;
    }

    public Xbox(int port) {
        this(new XboxController(port));
    }

    // Get the underlying XboxClass
    public XboxController getJoystick() {
        return mJoystick;
    }

    // Left Stick //
    public double getLeftX() {
        return getJoystick().getX(Hand.kLeft);
    }

    public double getLeftY() {
        return getJoystick().getY(Hand.kLeft);
    }

    // Right Stick //
    public double getRightX() {
        return getJoystick().getX(Hand.kRight);
    }

    public double getRightY() {
        return getJoystick().getY(Hand.kRight);
    }

    // D-Pad //
    public boolean getRawDPadUp() {
        return getJoystick().getPOV() == 0;
    }

    public boolean getRawDPadDown() {
        return getJoystick().getPOV() == 180;
    }

    public boolean getRawDPadLeft() {
        return getJoystick().getPOV() == 270;
    }

    public boolean getRawDPadRight() {
        return getJoystick().getPOV() == 90;
    }

    // Bumpers //
    public boolean getRawLeftBumper() {
        return getJoystick().getBumper(Hand.kLeft);
    }

    public boolean getRawRightBumper() {
        return getJoystick().getBumper(Hand.kRight);
    }

    // Triggers //
    public double getLeftTrigger() {
        return getJoystick().getTriggerAxis(Hand.kLeft);
    }

    public double getRightTrigger() {
        return getJoystick().getTriggerAxis(Hand.kRight);
    }

    // Face Buttons //
    public boolean getRawLeftButton() {
        return getJoystick().getXButton();
    }

    public boolean getRawBottomButton() {
        return getJoystick().getAButton();
    }

    public boolean getRawRightButton() {
        return getJoystick().getBButton();
    }

    public boolean getRawTopButton() {
        return getJoystick().getYButton();
    }

    // Start / Select / Option //
    public boolean getRawSelectButton() {
        return getJoystick().getStartButton();
    }

    public boolean getRawStartButton() {
        return getJoystick().getStartButton();
    }

    public boolean getRawOptionButton() {
        return getJoystick().getStartButton();
    }

    // Analog Stick Buttons //
    public boolean getRawLeftAnalogButton() {
        return getJoystick().getStickButton(Hand.kLeft);
    }

    public boolean getRawRightAnalogButton() {
        return getJoystick().getStickButton(Hand.kRight);
    }
}
