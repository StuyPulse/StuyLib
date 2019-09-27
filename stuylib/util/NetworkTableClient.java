/* http://first.wpi.edu/FRC/roborio/release/docs/java/ */

package frc.util;

import java.util.Set;
import java.lang.Number;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PersistentException;

/**
 * The NetworkTableClient is a very fast way
 * to easily interface with a network table
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NetworkTableClient {
    
    /* Members */
    private NetworkTableInstance mInstance; // Instance contains IP/Related information
    private NetworkTable mTable; // Current Data Table
    private String mTableName = ""; // Name of Data Table

    /* Constructors */
    // Default Instance and Custom DataTable
    NetworkTableClient(String dataTable) {
        this(dataTable, NetworkTableInstance.getDefault());
    }

    // Custom Instance and Custom DataTable
    NetworkTableClient(String dataTable, NetworkTableInstance instance) {
        mInstance = instance;
        openTable(dataTable);
    }

    /* Opens Table After Initalizing */
    // Opens Network table after constructing
    public void openTable(String dataTableName) {
        mTableName = dataTableName;
        mTable = mInstance.getTable(dataTableName);
    }

    /* Returns name of the datatable */
    public String tableName() {
        return mTableName;
    }

    /* Saving and writing tables to files */
    // Load entries from a file into the network table
    public String[] loadEntries(String fileName) throws PersistentException {
        return mTable.loadEntries(fileName);
    }

    // Save the network table to a file
    public void saveEntries(String fileName) throws PersistentException {
        mTable.saveEntries(fileName);
    }

    /* Table Listeners */
    // Listen to a single key.
    public int addEntryListener(String key, TableEntryListener listener, int flags) {
        return mTable.addEntryListener(key, listener, flags);
    }

    // Listen to keys only within this table.
    public int addEntryListener(TableEntryListener listener, int flags) {
        return mTable.addEntryListener(listener, flags);
    }

    // When adding listeners, it returns an int. Use this to remove them
    public void removeEntryListener(int listener) {
        mTable.removeEntryListener(listener);
    }

    /* Table Information */
    // Get names of all entrys
    public Set<String> getKeys() {
        return mTable.getKeys();
    }

    /* Gets Network Table Entry from Table */
    /* Not really ment for external use */
    public NetworkTableEntry getRawEntry(String entryName) {
        return mTable.getEntry(entryName);
    }

    /* Boolean Checks */
    public boolean isEntryValid(String entryName) {
        return getRawEntry(entryName).isValid();
    }

    public boolean isEntryPersistent(String entryName) {
        return getRawEntry(entryName).isPersistent();
    }

    public boolean containsKey(String entryName) {
        return mTable.containsKey(entryName);
    }

    /* Read information from Network Table */
    public static final boolean DEFAULT_BOOLEAN = false;

    public boolean getBoolean(String entryName) {
        return getRawEntry(entryName).getBoolean(DEFAULT_BOOLEAN);
    }

    public static final double DEFAULT_DOUBLE = 0.0;

    public double getDouble(String entryName) {
        return getRawEntry(entryName).getDouble(DEFAULT_DOUBLE);
    }

    public static final Number DEFAULT_NUMBER = 0.0;

    public Number getNumber(String entryName) {
        return getRawEntry(entryName).getNumber(DEFAULT_NUMBER);
    }

    public static final String DEFAULT_STRING = "";

    public String getString(String entryName) {
        return getRawEntry(entryName).getString(DEFAULT_STRING);
    }

    public int getFlags(String entryName) {
        return getRawEntry(entryName).getFlags();
    }

    /* Write Information to the Network Table */
    /* Returns False if the entry exists with a different type */
    public boolean setBoolean(String entryName, boolean value) {
        return getRawEntry(entryName).setBoolean(value);
    }

    public boolean setDouble(String entryName, double value) {
        return getRawEntry(entryName).setDouble(value);
    }

    public boolean setNumber(String entryName, Number value) {
        return getRawEntry(entryName).setNumber(value);
    }

    public boolean setString(String entryName, String value) {
        return getRawEntry(entryName).setString(value);
    }

    public void setFlags(String entryName, int value) {
        getRawEntry(entryName).setFlags(value);
    }

    public void clearFlags(String entryName, int value) {
        getRawEntry(entryName).clearFlags(value);
    }

    /* Make value persistent through program restarts. */
    public void setPersistent(String entryName) {
        getRawEntry(entryName).setPersistent();
    }

    public void clearPersistent(String entryName) {
        getRawEntry(entryName).clearPersistent();
    }
}