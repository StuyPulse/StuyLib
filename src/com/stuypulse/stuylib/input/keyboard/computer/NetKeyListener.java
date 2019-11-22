package com.stuypulse.stuylib.input.keyboard.computer;

import com.stuypulse.stuylib.input.keyboard.NetKeyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A KeyListener that uploads all of the key strokes to a network table where
 * they can then be recieved and put into the robot
 * 
 * This would not be used on the robot, but should be used on the users computer
 * 
 * Make an AWT window to use this listener
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NetKeyListener extends KeyAdapter {

    /**
     * Network Table for which key presses go
     */
    private NetKeyboard mNetKeyboard;

    /**
     * Initialize Network Keyboard Listener
     * 
     * @param team team number of robot
     * @param port virtual keyboard port
     */
    public NetKeyListener(int team, int port) {
        mNetKeyboard = new NetKeyboard(team, port);
    }

    /**
     * Returns underlying NetKeyboard class
     * 
     * @return underlying NetKeyboard class
     */
    public NetKeyboard getNetKeyboard() {
        return mNetKeyboard;
    }

    /**
     * Checks if network table is connected
     * 
     * @return if network table is connected
     */
    public boolean isConnected() {
        return mNetKeyboard.isConnected();
    }

    /**
     * Adds Key from Key Event to State
     * 
     * @param e Key Event
     */
    public void keyPressed(KeyEvent e) {
        mNetKeyboard.setKey(KeyEvent.getKeyText(e.getKeyCode()), true);
    }

    /**
     * Removes Key from Key Event to State
     * 
     * @param e Key Event
     */
    public void keyReleased(KeyEvent e) {
        mNetKeyboard.setKey(KeyEvent.getKeyText(e.getKeyCode()), false);
    }
}