package edu.stuylib.input.keyboard.computer;

import edu.wpi.first.networktables.NetworkTableInstance;

import edu.stuylib.input.keyboard.NetKeyboardInfo;
import edu.stuylib.network.NetworkTableClient;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.HashSet;
import java.util.Iterator;

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
public class NetKeyListener implements KeyListener, Iterable<String> {
    
    ///////////////
    // VARIABLES //
    /////////////// 

    /**
     * Network Table for which key presses go
     */
    private NetworkTableClient mKeyboardTable;

    /**
     * Set of current keys being pressed
     */
    private HashSet<String> mKeysPressed;

    //////////////////
    // CONSTRUCTORS // 
    //////////////////

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
        mKeyboardTable = new NetworkTableClient(inst, NetKeyboardInfo.getTabelName(port));

        // Rare for more than 16 keys to be pressed
        mKeysPressed = new HashSet<String>(16, 0.75f);
    }

    ///////////////////////
    // HASHSET INTERFACE //
    ///////////////////////

    /**
     * Returns iterator to hash set
     * @return Hash Set Iterator
     */
    public Iterator<String> iterator() {
        return mKeysPressed.iterator();
    }

    /**
     * Checks if key is pressed
     * @param key key name
     * @return if key is pressed
     */
    public boolean isKeyPressed(String key) {
        return mKeysPressed.contains(NetKeyboardInfo.sanatize(key));
    }

    ////////////////////////
    // KEY LISTENER STUFF //
    ////////////////////////

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
        String keyName = getKeyName(e);
        mKeysPressed.add(keyName);
        mKeyboardTable.setBoolean(keyName, true);
    }

    /**
     * Removes Key from Key Event to State
     * @param e Key Event
     */
    public void keyReleased(KeyEvent e) {
        String keyName = getKeyName(e);
        mKeysPressed.remove(keyName);
        mKeyboardTable.setBoolean(keyName, false);
    }
}