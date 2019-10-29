package com.stuypulse.stuylib.input.keyboard.computer;

import edu.wpi.first.networktables.NetworkTableInstance;

import com.stuypulse.stuylib.input.keyboard.NetKeyboardInfo;
import com.stuypulse.stuylib.network.NetworkTableWrapper;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * A KeyListener that uploads all of the
 * key strokes to a network table where
 * they can then be recieved and put into
 * the robot
 * 
 * This would not be used on the robot,
 * but should be used on the users computer
 * 
 * Make an AWT window to use this listener
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class NetKeyListener implements KeyListener {

    /**
     * Network Table for which key presses go
     */
    private NetworkTableWrapper mKeyboardTable;

    /**
     * Initialize Network Keyboard Listener
     * @param team team number of robot
     * @param table virtual keyboard port
     */
    public NetKeyListener(int team, int port) {
        // Connect to robot instead of making a server
        NetworkTableInstance inst = NetworkTableInstance.create();
        inst.startClientTeam(team);

        // Connect table to robot
        mKeyboardTable = new NetworkTableWrapper(inst, NetKeyboardInfo.getTabelName(port));
    }

    /**
     * Gets key name from key event
     * @param e key event
     * @return key name
     */
    private String getKeyName(KeyEvent e) {
        return NetKeyboardInfo.sanatize(KeyEvent.getKeyText(e.getKeyCode()));
    }

    /**
     * Adds Key from Key Event to State
     * @param e Key Event
     */
    public void keyPressed(KeyEvent e) {
        mKeyboardTable.setBoolean(getKeyName(e), true);
    }

    /**
     * Removes Key from Key Event to State
     * @param e Key Event
     */
    public void keyReleased(KeyEvent e) {
        mKeyboardTable.setBoolean(getKeyName(e), false);
    }

    /**
     * This doesn't do anything as 
     * it does not apply to our use
     * @param e Key Event
     */
    public void keyTyped(KeyEvent e) {}
}