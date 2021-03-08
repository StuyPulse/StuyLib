/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input.gamepads.keyboard;

import com.stuypulse.stuylib.input.keyboard.NetKeyboard;

/**
 * This class takes data from a Network Keyboard and puts it into a gamepad
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class NetKeyGamepad extends KeyGamepad {

    /** Underlying Network Keyboard */
    private NetKeyboard mKeyboard;

    /**
     * Opens Network Keyboard Gamepad
     *
     * @param port virtual port
     */
    public NetKeyGamepad(int port) {
        mKeyboard = new NetKeyboard(port);
    }

    /**
     * Get boolean for if key is pressed
     *
     * @param key key name
     * @return if key is pressed
     */
    @Override
    public boolean getKey(String key) {
        return mKeyboard.getKey(key);
    }
}
