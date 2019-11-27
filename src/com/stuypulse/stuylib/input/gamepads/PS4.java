package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.WPIGamepad;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * Implementation of the PS4 for the Gamepad Class
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class PS4 extends WPIGamepad {

    // Constructor //
    public PS4(int port) {
        super(port);
    }

    // Left Stick //
    public double getLeftX()    { return getRawAxis(0); }
    public double getLeftY()    { return -getRawAxis(1); }

    // Right Stick //
    public double getRightX()   { return getRawAxis(2); }
    public double getRightY()   { return -getRawAxis(5); }

    // D-Pad //
    public boolean getRawDPadUp()       { return getJoystick().getPOV() == 0; }
    public boolean getRawDPadDown()     { return getJoystick().getPOV() == 180; }
    public boolean getRawDPadLeft()     { return getJoystick().getPOV() == 270; }
    public boolean getRawDPadRight()    { return getJoystick().getPOV() == 90; }

    // Bumpers //
    public boolean getRawLeftBumper()   { return getRawButton(5); }
    public boolean getRawRightBumper()  { return getRawButton(6); }
    
    // Triggers //
    public double getRawLeftTriggerAxis()   { return (getRawAxis(3) + 1.0) / 2.0; }
    public double getRawRightTriggerAxis()  { return (getRawAxis(4) + 1.0) / 2.0; }

    // Face Buttons //
    public boolean getRawLeftButton()   { return getRawButton(1); }
    public boolean getRawRightButton()  { return getRawButton(3); }
    public boolean getRawTopButton()    { return getRawButton(4); }
    public boolean getRawBottomButton() { return getRawButton(2); }

    // Start / Select / Option //
    public boolean getRawSelectButton() { return getRawButton(9); }
    public boolean getRawStartButton()  { return getRawButton(10); }
    public boolean getRawOptionButton() { return getRawButton(10); }
    
    // Analog Stick Buttons // 
    public boolean getRawLeftAnalogButton()     { return getRawButton(11); }
    public boolean getRawRightAnalogButton()    { return getRawButton(12); }

    // Rumble //
    public void setRumble(double intensity) {
        getJoystick().setRumble(GenericHID.RumbleType.kLeftRumble, intensity);
        getJoystick().setRumble(GenericHID.RumbleType.kRightRumble, intensity);
    }
}