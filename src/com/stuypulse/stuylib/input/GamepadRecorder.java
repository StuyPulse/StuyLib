/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input;

import com.stuypulse.stuylib.util.StopWatch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamepadRecorder extends Gamepad {

    private static final double PLAYBACK_SPEED = 0.02; // 20 ms

    private final List<GamepadState> states;

    private final Gamepad gamepad;
    private final StopWatch timer;

    private final FileWriter jsonWriter;

    public GamepadRecorder(Gamepad gamepad, File fileToWrite) throws IOException {
        this.gamepad = gamepad;
        this.timer = new StopWatch();
        this.states = new ArrayList<>();
        this.jsonWriter = new FileWriter(fileToWrite);
    }

    public void reset() {
        this.states.clear();
        timer.reset();
    }

    private void saveState() {
        if (timer.getTime() > PLAYBACK_SPEED) {
            this.states.add(new GamepadState(this.gamepad));
            timer.reset();
        }
    }
}
