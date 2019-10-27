package edu.stuylib.input.keyboard.computer;

import edu.wpi.first.networktables.NetworkTableInstance;

import edu.stuylib.input.keyboard.NetKeyboardInfo;
import edu.stuylib.network.NetworkTableClient;

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
    private NetworkTableClient mKeyboardTable;

    /**
     * Initialize NetKeyListener with team number
     * @param team team number of robot
     */
    public NetKeyListener(int team) {
        this(team, NetKeyboardInfo.DEFAULT_TABLE);
    }

    /**
     * Initialize NetKeyListener with team number and table name
     * @param team team number of robot
     * @param table table name of network table
     */
    public NetKeyListener(int team, String table) {
        NetworkTableInstance inst = NetworkTableInstance.create();
        inst.startClientTeam(team);
        mKeyboardTable = new NetworkTableClient(inst, table);
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
     * This doesn't do anything as 
     * it does not apply to our use
     * @param e Key Event
     */
    public void keyTyped(KeyEvent e) {}

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
}