/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * WPI Gamepad extends Gamepad and adds functions that makes interacting with the underlying
 * Joystick class easy. The WPI joystick class is kept in a separate class to make it easy to update
 * if WPI updates.
 *
 * <p>If you would like to get a button id that is not defined, or an axis id that is not defined,
 * use {@link #getRawButton(int)} or {@link #getRawAxis(int)}. If you want it to return a button for
 * an unimplemented button, type {@link #getButton(int)}.
 *
 * <p>To initialize this class, pass in a Joystick or an int set to the port number. This will be
 * the Joystick that the Gamepad class will interact with.
 *
 * <p>If you do not initialize with a Joystick, everything will still work except for - {@link
 * #getRawButton(int)} - {@link #getRawAxis(int)} - {@link #getButton(int)} which will return either
 * false or 0.0
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

    /** @param joystick WPI Joystick that will be stored in this class */
    public WPIGamepad(Joystick joystick) {
        this.mJoy = joystick;
    }

    /** @param port The port that the gamepad is plugged into */
    public WPIGamepad(int port) {
        this(new Joystick(port));
    }

    /**********************/
    /*** JOYSTICK STUFF ***/
    /**********************/

    @Override
    public String getGamepadName() {
        return "WPIGamepad";
    }

    /** @return if Gamepad has a Joystick */
    public final boolean hasJoystick() {
        return getJoystick() != null;
    }

    /** @return Underlying joystick */
    public final Joystick getJoystick() {
        return this.mJoy;
    }

    /**
     * @param button Joystick button id
     * @return The value of the button
     */
    public final boolean getRawButton(int button) {
        if (!hasJoystick()) {
            return false;
        }
        return getJoystick().getRawButton(button);
    }

    /**
     * @param button Joystick button id
     * @return Trigger that activates with {@link #getRawButton(int)}
     */
    public final Trigger getButton(int button) {
        return new Trigger(() -> getRawButton(button));
    }

    /**
     * @param axis Joystick axis id
     * @return The value of the axis
     */
    public final double getRawAxis(int axis) {
        if (!hasJoystick()) {
            return 0.0;
        }
        return getJoystick().getRawAxis(axis);
    }

    /** @param intensity amount to make the gamepad rumble */
    public final void setRumble(double intensity) {
        getJoystick().setRumble(Joystick.RumbleType.kLeftRumble, intensity);
        getJoystick().setRumble(Joystick.RumbleType.kRightRumble, intensity);
    }
}
