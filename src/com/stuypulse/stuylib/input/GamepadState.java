/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input;

/**
 * This class stores the state of a {@link Gamepad} as all of the different values for its buttons
 * and axis.
 *
 * <p>It can be constructed by passing in a {@link Gamepad}, which it will grab all of the current
 * values and store them.
 *
 * <p>This class implements all of the functions for {@link Gamepad}, making it a valid gamepad that
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

    public final boolean buttonStickLeft;
    public final boolean buttonStickRight;

    /*******************/
    /*** CONSTRUCTOR ***/
    /*******************/

    /** @param gamepad Gamepad class to record in the state */
    public GamepadState(Gamepad gamepad) {
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

        this.buttonStickLeft = gamepad.getRawLeftStickButton();
        this.buttonStickRight = gamepad.getRawRightStickButton();
    }

    /*******************************/
    /*** GAMEPAD STATE FUNCTIONS ***/
    /*******************************/

    // TODO: JSON FORMATTING

    /****************************************/
    /*** GAMEPAD IMPLEMENTATION FUNCTIONS ***/
    /****************************************/

    // Left Stick //
    @Override
    public double getLeftX() {
        return this.stickLeftX;
    }

    @Override
    public double getLeftY() {
        return this.stickLeftY;
    }

    // Right Stick //
    @Override
    public double getRightX() {
        return this.stickRightX;
    }

    @Override
    public double getRightY() {
        return this.stickRightY;
    }

    // D-Pad //
    @Override
    public boolean getRawDPadUp() {
        return this.dpadUp;
    }

    @Override
    public boolean getRawDPadDown() {
        return this.dpadDown;
    }

    @Override
    public boolean getRawDPadLeft() {
        return this.dpadLeft;
    }

    @Override
    public boolean getRawDPadRight() {
        return this.dpadRight;
    }

    // Bumpers //
    @Override
    public boolean getRawLeftBumper() {
        return this.bumperLeft;
    }

    @Override
    public boolean getRawRightBumper() {
        return this.bumperRight;
    }

    // Triggers //
    @Override
    public double getLeftTrigger() {
        return this.triggerLeft;
    }

    @Override
    public double getRightTrigger() {
        return this.triggerRight;
    }

    // Face Buttons //
    @Override
    public boolean getRawTopButton() {
        return this.buttonTop;
    }

    @Override
    public boolean getRawBottomButton() {
        return this.buttonBottom;
    }

    @Override
    public boolean getRawLeftButton() {
        return this.buttonLeft;
    }

    @Override
    public boolean getRawRightButton() {
        return this.buttonRight;
    }

    // Start / Select / Option //
    @Override
    public boolean getRawSelectButton() {
        return this.buttonSelect;
    }

    @Override
    public boolean getRawStartButton() {
        return this.buttonStart;
    }

    // Analog Stick Buttons //
    @Override
    public boolean getRawLeftStickButton() {
        return this.buttonStickLeft;
    }

    @Override
    public boolean getRawRightStickButton() {
        return this.buttonStickRight;
    }
}
