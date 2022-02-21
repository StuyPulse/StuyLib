/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input;

import com.stuypulse.stuylib.util.StopWatch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GamepadPlayback extends Gamepad {

    private static final List<GamepadState> readGamepadStates(Path path) {
        try {
            Gson jsonParser = new Gson();
            BufferedReader jsonFile = Files.newBufferedReader(path);
            List<GamepadState> states =
                    jsonParser.fromJson(jsonFile, new TypeToken<List<GamepadState>>() {}.getType());
            
            return states;
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(new GamepadState());
        }
    }

    private static final double PLAYBACK_SPEED = 0.02; // 20 ms

    private final StopWatch timer;

    private final List<GamepadState> states;
    private int tick;

    public GamepadPlayback(Path pathToJson) {
        states = readGamepadStates(pathToJson);
        timer = new StopWatch();
        tick = 0;
    }

    private GamepadState getGamepadState() {
        if (timer.getTime() > PLAYBACK_SPEED) {
            timer.reset();
            tick++;
        }

        return tick < states.size() ? states.get(tick) : new GamepadState();
    }

    public void reset() {
        timer.reset();
        tick = 0;
    }

    // Left Stick //
    @Override
    public double getLeftX() {
        return getGamepadState().stickLeftX;
    }

    @Override
    public double getLeftY() {
        return getGamepadState().stickLeftY;
    }

    // Right Stick //
    @Override
    public double getRightX() {
        return getGamepadState().stickRightX;
    }

    @Override
    public double getRightY() {
        return getGamepadState().stickRightY;
    }

    // D-Pad //
    @Override
    public boolean getRawDPadUp() {
        return getGamepadState().dpadUp;
    }

    @Override
    public boolean getRawDPadDown() {
        return getGamepadState().dpadDown;
    }

    @Override
    public boolean getRawDPadLeft() {
        return getGamepadState().dpadLeft;
    }

    @Override
    public boolean getRawDPadRight() {
        return getGamepadState().dpadRight;
    }

    // Bumpers //
    @Override
    public boolean getRawLeftBumper() {
        return getGamepadState().bumperLeft;
    }

    @Override
    public boolean getRawRightBumper() {
        return getGamepadState().bumperRight;
    }

    // Triggers //
    @Override
    public double getLeftTrigger() {
        return getGamepadState().triggerLeft;
    }

    @Override
    public double getRightTrigger() {
        return getGamepadState().triggerRight;
    }

    // Face Buttons //
    @Override
    public boolean getRawTopButton() {
        return getGamepadState().buttonTop;
    }

    @Override
    public boolean getRawBottomButton() {
        return getGamepadState().buttonBottom;
    }

    @Override
    public boolean getRawLeftButton() {
        return getGamepadState().buttonLeft;
    }

    @Override
    public boolean getRawRightButton() {
        return getGamepadState().buttonRight;
    }

    // Start / Select / Option //
    @Override
    public boolean getRawSelectButton() {
        return getGamepadState().buttonSelect;
    }

    @Override
    public boolean getRawStartButton() {
        return getGamepadState().buttonStart;
    }

    // Analog Stick Buttons //
    @Override
    public boolean getRawLeftStickButton() {
        return getGamepadState().buttonStickLeft;
    }

    @Override
    public boolean getRawRightStickButton() {
        return getGamepadState().buttonStickRight;
    }
}
