package edu.stuylib.input.keyboard.server;

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
     * Initializes Keyboard State with
     * default network table "StuyLibNetworkKeyboard"
     */
    public NetKeyListener() {
        this(NetKeyboardInfo.DEFAULT_TABLE);
    }

    /**
     * Initializes Keyboard State with
     * default network table "NetworkKeyboard"
     */
    public NetKeyListener(String table) {
        mKeyboardTable = new NetworkTableClient(table);
    }


    /*************************/
    /*** KEY LISTENER CODE ***/
    /*************************/
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