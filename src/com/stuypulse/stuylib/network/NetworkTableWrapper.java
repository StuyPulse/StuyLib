/* http://first.wpi.edu/FRC/roborio/release/docs/java/ */

package com.stuypulse.stuylib.network;

import java.util.Set;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The NetworkTableWrapper is a very fast way to easily interface with a network
 * table.
 * 
 * If a function you want is not implemented, use getTable(), getRawEntry(), or
 * getInstance() to call the function yourself
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NetworkTableWrapper {

    /*********************/
    /***** FACTORIES *****/
    /*********************/

    /**
     * Opens network table on local device. IE a robot opens a network table for
     * other devices to connect to
     * 
     * @param table network table name
     * @return Configured Network Table Wrapper
     */
    public static NetworkTableWrapper open(String table) {
        return open(NetworkTableInstance.getDefault(), table);
    }

    /**
     * Opens network table that is connected to a robot. IE a program connecting to
     * a robot.
     * 
     * @param team  team number
     * @param table network table name
     * @return Configured Network Table Wrapper
     */
    public static NetworkTableWrapper open(int team, String table) {
        NetworkTableInstance instance = NetworkTableInstance.create();
        instance.startClientTeam(team);
        return open(instance, table);
    }

    /**
     * Opens network table with special instance.
     * 
     * @param instance NetworkTableInstance
     * @param table    network table name
     * @return Configured Network Table Wrapper
     */
    public static NetworkTableWrapper open(NetworkTableInstance instance, String table) {
        return new NetworkTableWrapper(instance, table);
    }

    /*********************/
    /***** VARIABLES *****/
    /*********************/
    
    private NetworkTableInstance mInstance; // Instance contains IP/Related information
    private NetworkTable mTable; // Current Data Table
    private String mTableName = ""; // Name of Data Table

    /************************/
    /***** CONSTRUCTORS *****/
    /************************/

    /**
     * Creates a Network Table Wrapper opened on table "tableName", and with the a
     * special NetworkTableInstance (ie. if you are making a client)
     * 
     * @param tableName network table name
     * @param instance  custom network table instance
     */
    private NetworkTableWrapper(NetworkTableInstance instance, String table) {
        mInstance = instance;
        mTable = mInstance.getTable(table);
        mTableName = table;
    }

    /****************************/
    /***** VARIABLE GETTERS *****/
    /****************************/

    /**
     * Gets current network table instance
     * 
     * @return current network table instance
     */
    public NetworkTableInstance getInstance() {
        return mInstance;
    }

    /**
     * Gets current network table
     * 
     * @return current network table
     */
    public NetworkTable getTable() {
        return mTable;
    }

    /**
     * Gets current network table name
     * 
     * @return current network table name
     */
    public String getTableName() {
        return mTableName;
    }

    /************************/
    /***** MISC GETTERS *****/
    /************************/

    /**
     * Checks if network table is connected
     * 
     * @return if network table is connected
     */
    public boolean isConnected() {
        return getInstance().isConnected();
    }

    /**
     * Gets a set of all key names in network table
     * 
     * @return set of key names in network table
     */
    public Set<String> getKeys() {
        return mTable.getKeys();
    }

    /**
     * Get a NetworkTableEntry for a key
     * 
     * @param key key name
     * @return NetworkTableEntry for key
     */
    public NetworkTableEntry getRawEntry(String key) {
        return mTable.getEntry(key);
    }

    /**
     * Checks if key is a valid entry
     * 
     * @param key key name
     * @return if key is a valid entry
     */
    public boolean isEntryValid(String key) {
        return getRawEntry(key).isValid();
    }

    /**
     * Ckecks to see if network table contains key
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
     * @param key   key name
     * @param value desired value
     * @return returns false if entry exists with other type
     */
    public boolean setBoolean(String key, boolean value) {
        return getRawEntry(key).setBoolean(value);
    }

    /**
     * Set double in network table
     * 
     * @param key   key name
     * @param value desired value
     * @return returns false if entry exists with other type
     */
    public boolean setDouble(String key, double value) {
        return getRawEntry(key).setDouble(value);
    }

    /**
     * Set Number in network table
     * 
     * @param key   key name
     * @param value desired value
     * @return returns false if entry exists with other type
     */
    public boolean setNumber(String key, Number value) {
        return getRawEntry(key).setNumber(value);
    }

    /**
     * Set String in network table
     * 
     * @param key   key name
     * @param value desired value
     * @return returns false if entry exists with other type
     */
    public boolean setString(String key, String value) {
        return getRawEntry(key).setString(value);
    }
}