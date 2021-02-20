// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

package com.stuypulse.stuylib.input;

import com.stuypulse.stuylib.util.StopWatch;

/**
 * This class stores the state of a gamepad as all of the different values for its buttons and axis.
 *
 * <p>It can be constructed by passing in a gamepad, which it will grab all of the current values
 * and store them.
 *
 * <p>This class implements all of the functions for gamepad, making it a valid gamepad that
 * something can call.
 *
 * <p>TODO: this class is designed to be turned into a JSON string and back, which has yet to be
 * implemented.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class GamepadState extends Gamepad {

    /*************************************/
    /*** VARIABLES MAKING UP THE STATE ***/
    /*************************************/

    private final StopWatch timer;

    public final double stickLeftX;
    public final double stickLeftY;

    public final double stickRightX;
    public final double stickRightY;

    public final boolean dpadUp;
    public final boolean dpadDown;
    public final boolean dpadLeft;
    public final boolean dpadRight;

    public final boolean bumperLeft;
    public final boolean bumperRight;

    public final double triggerLeft;
    public final double triggerRight;

    public final boolean buttonTop;
    public final boolean buttonBottom;
    public final boolean buttonLeft;
    public final boolean buttonRight;

    public final boolean buttonSelect;
    public final boolean buttonStart;
    public final boolean buttonOption;

    public final boolean buttonStickLeft;
    public final boolean buttonStickRight;

    /*******************/
    /*** CONSTRUCTOR ***/
    /*******************/

    public GamepadState(Gamepad gamepad) {
        this.timer = new StopWatch();

        this.stickLeftX = gamepad.getLeftX();
        this.stickLeftY = gamepad.getLeftY();

        this.stickRightX = gamepad.getRightX();
        this.stickRightY = gamepad.getRightY();

        this.dpadUp = gamepad.getRawDPadUp();
        this.dpadDown = gamepad.getRawDPadDown();
        this.dpadLeft = gamepad.getRawDPadLeft();
        this.dpadRight = gamepad.getRawDPadRight();

        this.bumperLeft = gamepad.getRawLeftBumper();
        this.bumperRight = gamepad.getRawRightBumper();

        this.triggerLeft = gamepad.getLeftTrigger();
        this.triggerRight = gamepad.getRightTrigger();

        this.buttonTop = gamepad.getRawTopButton();
        this.buttonBottom = gamepad.getRawBottomButton();
        this.buttonLeft = gamepad.getRawLeftButton();
        this.buttonRight = gamepad.getRawRightButton();

        this.buttonSelect = gamepad.getRawSelectButton();
        this.buttonStart = gamepad.getRawStartButton();
        this.buttonOption = gamepad.getRawOptionButton();

        this.buttonStickLeft = gamepad.getRawLeftStickButton();
        this.buttonStickRight = gamepad.getRawRightStickButton();
    }

    /*******************************/
    /*** GAMEPAD STATE FUNCTIONS ***/
    /*******************************/

    /**
     * Get the age of this class since construction.
     *
     * <p>This is done by an internal timer that is created at construction and can not be reset.
     *
     * @return the time since construction in seconds.
     */
    public double getAge() {
        return this.timer.getTime();
    }

    /****************************************/
    /*** GAMEPAD IMPLEMENTATION FUNCTIONS ***/
    /****************************************/

    // Left Stick //
    public double getLeftX() {
        return this.stickLeftX;
    }

    public double getLeftY() {
        return this.stickLeftY;
    }

    // Right Stick //
    public double getRightX() {
        return this.stickRightX;
    }

    public double getRightY() {
        return this.stickRightY;
    }

    // D-Pad //
    public boolean getRawDPadUp() {
        return this.dpadUp;
    }

    public boolean getRawDPadDown() {
        return this.dpadDown;
    }

    public boolean getRawDPadLeft() {
        return this.dpadLeft;
    }

    public boolean getRawDPadRight() {
        return this.dpadRight;
    }

    // Bumpers //
    public boolean getRawLeftBumper() {
        return this.bumperLeft;
    }

    public boolean getRawRightBumper() {
        return this.bumperRight;
    }

    // Triggers //
    public double getLeftTrigger() {
        return this.triggerLeft;
    }

    public double getRightTrigger() {
        return this.triggerRight;
    }

    // Face Buttons //
    public boolean getRawTopButton() {
        return this.buttonTop;
    }

    public boolean getRawBottomButton() {
        return this.buttonBottom;
    }

    public boolean getRawLeftButton() {
        return this.buttonLeft;
    }

    public boolean getRawRightButton() {
        return this.buttonRight;
    }

    // Start / Select / Option //
    public boolean getRawSelectButton() {
        return this.buttonSelect;
    }

    public boolean getRawStartButton() {
        return this.buttonStart;
    }

    public boolean getRawOptionButton() {
        return this.buttonOption;
    }

    // Analog Stick Buttons //
    public boolean getRawLeftStickButton() {
        return this.buttonStickLeft;
    }

    public boolean getRawRightStickButton() {
        return this.buttonStickRight;
    }
}
