package com.stuypulse.stuylib.input.keyboard.robot;

import com.stuypulse.stuylib.input.keyboard.NetKeyboardInfo;
import com.stuypulse.stuylib.network.NetworkTableWrapper;

import java.util.Set;

/**
 * This class lets you recieve keyboard
 * presses from a computer on the robot
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NetKeyboard {

    /**
     * Table where key information is stored
     */
    private NetworkTableWrapper mKeyboardTable;

    /**
     * Creates NetworkKeyboard on robot
     * @param port virtual port number (unsure, use 0)
     */
    public NetKeyboard(int port) {
        mKeyboardTable = NetworkTableWrapper.open(NetKeyboardInfo.getTabelName(port));
    }

    /**
     * Creates NetworkKeyboard that is
     * connected to the robot from elsewhere
     * @param team robot team number
     * @param port virtual port number (unsure, use 0)
     */
    public NetKeyboard(int team, int port) {
        mKeyboardTable = NetworkTableWrapper.open(team, NetKeyboardInfo.getTabelName(port));
    }

    /**
     * Gets Key Info
     * @param key name of key
     * @return if key is pressed
     */
    public boolean isKeyPressed(String key) {
        return mKeyboardTable.getBoolean(NetKeyboardInfo.sanatize(key));
    }

    /**
     * Returns Set of Strings with the names 
     * of every key that is pressed
     * @return set of strings
     */
    public Set<String> getKeysPressed() {
        Set<String> keysPressed = mKeyboardTable.getKeys();
        for(String s : keysPressed) {
            if(!mKeyboardTable.getBoolean(s)) {
                keysPressed.remove(s);
            }
        }
        return keysPressed;
    }
}