package edu.stuylib.input.keyboard;

import edu.stuylib.input.keyboard.NetworkKeyListener;
import edu.stuylib.network.NetworkTableClient;

/**
 * This class lets you recieve keyboard
 * presses from a computer on the robot
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NetworkKeyboard {

    /**
     * Default Table Name
     */
    public static final String DEFAULT_TABLE = NetworkKeyListener.DEFAULT_TABLE;

    /**
     * Table where key information is stored
     */
    private NetworkTableClient mKeyboardTable;

    /**
     * Creates NetworkKeyboard on table
     * "StuyLibNetworkKeyboard"
     */
    public NetworkKeyboard() {
        this(DEFAULT_TABLE);
    }

    /**
     * Creates NetworkKeyboard on custom table
     * @param table
     */
    public NetworkKeyboard(String table) {
        mKeyboardTable = new NetworkTableClient(table);
    }

    /**
     * Gets Key Info
     * @param key name of key
     * @return if key is pressed
     */
    public boolean isKeyPressed(String key) {
        return mKeyboardTable.getBoolean(key);
    }
}