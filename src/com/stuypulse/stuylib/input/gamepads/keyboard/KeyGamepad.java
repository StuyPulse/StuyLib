/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input.gamepads.keyboard;

import com.stuypulse.stuylib.input.Gamepad;

/**
 * This is a base class for a gamepad controlled via keyboard. This class controls the keymappings.
 * <i>All a child controller needs to do is provide a way to check if any keyboard button is
 * pressed.</i>
 *
 * <ul>
 *   <li><b>NOTE:</b> all input types are handled except rumble
 *   <li><b>NOTE:</b> it is suggested to use this with filtered because your values will always be
 *       -1, 0, 1. Filtering is not included for simplicity
 * </ul>
 *
 * <br>
 *
 * @see com.stuypulse.stuylib.streams.filters.IFilter
 * @author Myles Pasetsky (selym3)
 */
public class KeyGamepad extends Gamepad {

    public KeyGamepad() {}

    protected boolean getKey(String name) {
        return false;
    }

    private final int getDirection(String pos, String neg) {
        return (getKey(pos) ? 1 : 0) - (getKey(neg) ? 1 : 0);
    }

    public double getLeftX() {
        return (getDirection("d", "a"));
    }

    public double getLeftY() {
        return (getDirection("w", "s"));
    }

    // Right Stick //
    public double getRightX() {
        return (getDirection("l", "j"));
    }

    public double getRightY() {
        return (getDirection("i", "k"));
    }

    // D-Pad //
    public boolean getRawDPadUp() {
        return getKey("numpad-8") || getKey("up");
    }

    public boolean getRawDPadDown() {
        return getKey("numpad-2") || getKey("down");
    }

    public boolean getRawDPadLeft() {
        return getKey("numpad-4") || getKey("left");
    }

    public boolean getRawDPadRight() {
        return getKey("numpad-6") || getKey("right");
    }

    // Bumpers //
    public boolean getRawLeftBumper() {
        return getKey("e");
    }

    public boolean getRawRightBumper() {
        return getKey("u");
    }

    // Triggers //
    public double getLeftTrigger() {
        return (getKey("q") ? 1 : 0);
    }

    public double getRightTrigger() {
        return (getKey("o") ? 1 : 0);
    }

    // Face Buttons //
    public boolean getRawLeftButton() {
        return getKey("z");
    }

    public boolean getRawRightButton() {
        return getKey("x");
    }

    public boolean getRawTopButton() {
        return getKey("c");
    }

    public boolean getRawBottomButton() {
        return getKey("v");
    }

    // Start / Select //
    public boolean getRawSelectButton() {
        return getKey("r");
    }

    public boolean getRawStartButton() {
        return getKey("t");
    }

    // Analog Stick Buttons //
    public boolean getRawLeftStickButton() {
        return getKey("f");
    }

    public boolean getRawRightStickButton() {
        return getKey("h");
    }
}
