// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.


package com.stuypulse.stuylib.input;

import com.stuypulse.stuylib.input.buttons.ButtonWrapper;

import edu.wpi.first.wpilibj.Joystick;

/**
 * WPI Gamepad extends Gamepad and adds functions that makes interacting with the underlying
 * Joystick class easy. The WPI joystick class is kept in a separate class to make it easy to update
 * if WPI updates.
 *
 * <p>If you would like to get a button id that is not defined, or an axis id that is not defined,
 * use getRawButton(int id) or getRawAxis(int id). If you want it to return a button for an
 * unimplemented button, type getButton(int id).
 *
 * <p>To initialize this class, pass in a Joystick or an int set to the port number. This will be
 * the Joystick that the Gamepad class will interact with.
 *
 * <p>If you do not initialize with a Joystick, everything will still work except for -
 * getRawButton(int id) - getButton(int id) - getRawAxis(int id) which will return either false or
 * 0.0
 *
 * <p>The difference between the implementations of the Gamepad class is how it interacts with the
 * underlying Joystick class.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class WPIGamepad extends Gamepad {

    /** Underlying Joystick Class */
    private Joystick mJoy;

    /*******************/
    /*** CONSTRUCTOR ***/
    /*******************/

    public WPIGamepad(Joystick joystick) {
        this.mJoy = joystick;
    }

    public WPIGamepad(int port) {
        this(new Joystick(port));
    }

    /**********************/
    /*** JOYSTICK STUFF ***/
    /**********************/

    /**
     * Checks if Gamepad has a Joystick
     *
     * @return if Gamepad has a Joystick
     */
    public final boolean hasJoystick() {
        return getJoystick() != null;
    }

    /**
     * Get underlying joystick
     *
     * @return Underlying joystick
     */
    public final Joystick getJoystick() {
        return this.mJoy;
    }

    /**
     * Get button from underlying joystick
     *
     * @param button Joystick button id
     * @return the value of the button
     */
    public final boolean getRawButton(int button) {
        if (!hasJoystick()) {
            return false;
        }
        return getJoystick().getRawButton(button);
    }

    /**
     * Get button from underlying joystick
     *
     * @param button Joystick button id
     * @return the value of the button
     */
    public final ButtonWrapper getButton(int button) {
        return new ButtonWrapper(() -> getRawButton(button));
    }

    /**
     * Get axis from underlying joystick
     *
     * @param axis Joystick axis id
     * @return the value of the axis
     */
    public final double getRawAxis(int axis) {
        if (!hasJoystick()) {
            return 0.0;
        }
        return getJoystick().getRawAxis(axis);
    }

    /**
     * Set the rumble intensity of the gamepad
     *
     * @param intensity intensity of the rumble
     */
    public final void setRumble(double intensity) {
        getJoystick().setRumble(Joystick.RumbleType.kLeftRumble, intensity);
        getJoystick().setRumble(Joystick.RumbleType.kRightRumble, intensity);
    }
}
