package edu.stuylib.input.keyboard;

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
 * Because of the lag of network tables, it
 * is advised that you only use this for testing
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class NetworkKeyListener implements KeyListener {

    /**
     * Default Table Name
     */
    public static final String DEFAULT_TABLE = "StuyLibNetworkKeyboard";
    
    /**
     * Network Table for which key presses go
     */
    private NetworkTableClient mKeyboardTable;

    /**
     * Initializes Keyboard State with
     * default network table "StuyLibNetworkKeyboard"
     */
    public NetworkKeyListener() {
        this(DEFAULT_TABLE);
    }

    /**
     * Initializes Keyboard State with
     * default network table "NetworkKeyboard"
     */
    public NetworkKeyListener(String table) {
        mKeyboardTable = new NetworkTableClient(table);
    }


    /*************************/
    /*** KEY LISTENER CODE ***/
    /*************************/
    private String getKeyName(KeyEvent e) {
        return KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();
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