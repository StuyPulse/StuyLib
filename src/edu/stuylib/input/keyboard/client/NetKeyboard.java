package edu.stuylib.input.keyboard.client;

import edu.stuylib.input.keyboard.NetKeyboardInfo;
import edu.stuylib.network.NetworkTableClient;

/**
 * This class lets you recieve keyboard
 * presses from a computer on the robot
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NetKeyboard {

    /**
     * Default Table Name
     */
    public static final String DEFAULT_TABLE = NetKeyboardInfo.DEFAULT_TABLE;

    /**
     * Table where key information is stored
     */
    private NetworkTableClient mKeyboardTable;

    /**
     * Creates NetworkKeyboard on table
     * "StuyLibNetworkKeyboard"
     */
    public NetKeyboard() {
        this(DEFAULT_TABLE);
    }

    /**
     * Creates NetworkKeyboard on custom table
     * @param table
     */
    public NetKeyboard(String table) {
        mKeyboardTable = new NetworkTableClient(table);
    }

    /**
     * Gets Key Info
     * @param key name of key
     * @return if key is pressed
     */
    public boolean isKeyPressed(String key) {
        return mKeyboardTable.getBoolean(NetKeyboardInfo.sanatize(key));
    }
}