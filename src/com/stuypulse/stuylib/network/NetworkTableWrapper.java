/* http://first.wpi.edu/FRC/roborio/release/docs/java/ */

package com.stuypulse.stuylib.network;

import java.util.Set;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The NetworkTableWrapper is a very fast way
 * to easily interface with a network table.
 * 
 * If a function you want is not implemented,
 * use getTable(), getRawEntry(), or getInstance()
 * to call the function yourself
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NetworkTableWrapper {

    // Instance contains IP/Related information
    private NetworkTableInstance mInstance; 
    
    // Current Data Table
    private NetworkTable mTable; 
    
    // Name of Data Table
    private String mTableName = "";

    /**
     * Creates a Network Table Wrapper opened on
     * table "tableName", and with the default
     * NetworkTableInstance (ie. opening a server on the robot)
     * @param tableName network table name
     */
    public NetworkTableWrapper(String tableName) {
        this(NetworkTableInstance.getDefault(), tableName);
    }

    /**
     * Creates a Network Table Wrapper opened on
     * table "tableName", and with the a special
     * NetworkTableInstance (ie. if you are making a client)
     * @param tableName network table name
     * @param instance custom network table instance
     */
    public NetworkTableWrapper(NetworkTableInstance instance, String tableName) {
        setInstance(instance);
        setTable(tableName);
    }

    /**
     * Sets instance of NetworkTableWrapper.
     * WARNING: you must call setTable() after
     * setInstance() or else you WILL get a 
     * NullPointerException
     * @param instance new NetworkTableInstance
     */
    public void setInstance(NetworkTableInstance instance) {
        mInstance = instance;
        mTableName = null;
        mTable = null;
    }

    /**
     * Gets current network table instance
     * @return current network table instance
     */
    public NetworkTableInstance getInstance() {
        return mInstance;
    }

    /**
     * Sets current network table
     * @param tableName new network table
     */
    public void setTable(String tableName) {
        if(getInstance() != null) {
            mTableName = tableName;
            mTable = getInstance().getTable(tableName);
        } else {
            mTableName = null;
            mTable = null;
        }
    }

    /**
     * Gets current network table
     * @return current network table
     */
    public String getTable() {
        return mTableName;
    }

    /**
     * Get a NetworkTableEntry for a key
     * @param key key name 
     * @return NetworkTableEntry for key
     */
    public NetworkTableEntry getRawEntry(String key) {
        return mTable.getEntry(key);
    }

    /**
     * Gets a set of all key names in network table
     * @return set of key names in network table
     */
    public Set<String> getKeys() {
        return mTable.getKeys();
    }

    /**
     * Checks if key is a valid entry
     * @param key key name
     * @return if key is a valid entry
     */
    public boolean isEntryValid(String key) {
        return getRawEntry(key).isValid();
    }

    /**
     * Ckecks to see if network table contains key
     * @param key key name
     * @return if network table contains key
     */
    public boolean containsKey(String key) {
        return mTable.containsKey(key);
    }


    /****************************************/
    /***** GETTING NETWORK TABLE VALUES *****/
    /****************************************/

    /**
     * Boolean returned if no entry is found
     */
    public static final boolean DEFAULT_BOOLEAN = false;

    /**
     * Get boolean from network table
     * @param key key name
     * @return value
     */
    public boolean getBoolean(String key) {
        return getRawEntry(key).getBoolean(DEFAULT_BOOLEAN);
    }

    /**
     * Double returned if no entry is found
     */
    public static final double DEFAULT_DOUBLE = 0.0;

    /**
     * Get Double from network table
     * @param key key name
     * @return value
     */
    public double getDouble(String key) {
        return getRawEntry(key).getDouble(DEFAULT_DOUBLE);
    }

    /**
     * Number returned if no entry is found
     */
    public static final Number DEFAULT_NUMBER = 0.0;

    /**
     * Get Number from network table
     * @param key key name
     * @return value
     */
    public Number getNumber(String key) {
        return getRawEntry(key).getNumber(DEFAULT_NUMBER);
    }

    /**
     * String returned if no entry is found
     */
    public static final String DEFAULT_STRING = "";

    /**
     * Get String from network table
     * @param key key name
     * @return value
     */
    public String getString(String key) {
        return getRawEntry(key).getString(DEFAULT_STRING);
    }


    /****************************************/
    /***** SETTING NETWORK TABLE VALUES *****/
    /****************************************/

    /**
     * Set boolean in network table
     * @param key key name 
     * @param value desired value
     * @return returns false if entry exists with other type
     */
    public boolean setBoolean(String key, boolean value) {
        return getRawEntry(key).setBoolean(value);
    }

    /**
     * Set double in network table
     * @param key key name 
     * @param value desired value
     * @return returns false if entry exists with other type
     */
    public boolean setDouble(String key, double value) {
        return getRawEntry(key).setDouble(value);
    }

    /**
     * Set Number in network table
     * @param key key name 
     * @param value desired value
     * @return returns false if entry exists with other type
     */
    public boolean setNumber(String key, Number value) {
        return getRawEntry(key).setNumber(value);
    }

    /**
     * Set String in network table
     * @param key key name 
     * @param value desired value
     * @return returns false if entry exists with other type
     */
    public boolean setString(String key, String value) {
        return getRawEntry(key).setString(value);
    }
}