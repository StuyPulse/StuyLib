/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input.keyboard;

import com.stuypulse.stuylib.network.SLNetworkTable;

import java.util.Set;

/**
 * This class lets you send and receive keyboard information over network tables
 *
 * <p>Every other class will interact with the network keyboards through this class
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class NetKeyboard {

    private interface Constants {

        /**
         * Gets name of network table for Network Keyboard and its virtual port number
         *
         * @param port virtual port number
         * @return network table name
         */
        public static String getTableName(int port) {
            return ("NetworkKeyboard/port/" + Integer.toString(Math.abs(port)));
        }

        /**
         * Sanitize key names to prevent caps issues
         *
         * @param key key name
         * @return sanitized key name
         */
        public static String sanitize(String key) {
            return key.toUpperCase().trim();
        }
    }

    /** Table where key information is stored */
    private SLNetworkTable mKeyboardTable;

    /**
     * Creates NetworkKeyboard on robot
     *
     * @param port virtual port number (unsure, use 0)
     */
    public NetKeyboard(int port) {
        mKeyboardTable = SLNetworkTable.open(Constants.getTableName(port));
    }

    /**
     * Creates NetworkKeyboard that is connected to the robot from elsewhere
     *
     * @param team robot team number
     * @param port virtual port number (unsure, use 0)
     */
    public NetKeyboard(int team, int port) {
        mKeyboardTable = SLNetworkTable.open(team, Constants.getTableName(port));
    }

    /**
     * Checks if network table is connected
     *
     * @return if network table is connected
     */
    public boolean isConnected() {
        return mKeyboardTable.isConnected();
    }

    /**
     * Set key value
     *
     * @param key name of key
     * @param val new value for key
     */
    public void setKey(String key, boolean val) {
        mKeyboardTable.setBoolean(Constants.sanitize(key), val);
    }

    /**
     * Gets if key is pressed
     *
     * @param key name of key
     * @return if key is pressed
     */
    public boolean getKey(String key) {
        return mKeyboardTable.getBoolean(Constants.sanitize(key));
    }

    /**
     * Returns Set of Strings with the names of every key that is pressed
     *
     * @return set of strings
     */
    public Set<String> getKeysPressed() {
        Set<String> keysPressed = mKeyboardTable.getKeys();
        for (String s : keysPressed) {
            if (!mKeyboardTable.getBoolean(s)) {
                keysPressed.remove(s);
            }
        }
        return keysPressed;
    }
}
