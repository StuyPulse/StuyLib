package com.stuypulse.stuylib.input.keyboard;

import com.stuypulse.stuylib.network.NetworkTableWrapper;

import java.util.Set;

/**
 * This class lets you send and recieve keyboard information over network tables
 * 
 * Every other class will interact with the network keyboards through this class
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
         * Sanatize key names to prevent caps issues
         * 
         * @param key unsanatized key name
         * @return sanatized key name
         */
        public static String sanatize(String key) {
            return key.toUpperCase().trim();
        }
    }

    /**
     * Table where key information is stored
     */
    private NetworkTableWrapper mKeyboardTable;

    /**
     * Creates NetworkKeyboard on robot
     * 
     * @param port virtual port number (unsure, use 0)
     */
    public NetKeyboard(int port) {
        mKeyboardTable = NetworkTableWrapper.open(Constants.getTableName(port));
    }

    /**
     * Creates NetworkKeyboard that is connected to the robot from elsewhere
     * 
     * @param team robot team number
     * @param port virtual port number (unsure, use 0)
     */
    public NetKeyboard(int team, int port) {
        mKeyboardTable = NetworkTableWrapper.open(team, Constants.getTableName(port));
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
        mKeyboardTable.setBoolean(Constants.sanatize(key), val);
    }

    /**
     * Gets if key is pressed
     * 
     * @param key name of key
     * @return if key is pressed
     */
    public boolean getKey(String key) {
        return mKeyboardTable.getBoolean(Constants.sanatize(key));
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