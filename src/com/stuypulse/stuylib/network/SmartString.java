package com.stuypulse.stuylib.network;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * SmartString works as a wrapper for values on SmartDashboard. The idea for
 * this class was to make getting values on SmartDashboard easier by making them
 * variables that you know were initialized.
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */
public class SmartString {

    /**
     * The ID / Name for the value on SmartDashboard.
     */
    private String mTableID;

    /**
     * The default value that the SmartDashboard value was set too.
     */
    private String mDefaultValue;

    /**
     * Creates a SmartString with the element name and a default value. The value on
     * SmartDashboard will be reset to the default value on initialization.
     * 
     * @param id    the name of the String on SmartDashboard
     * @param value the default / initialization value for the value
     */
    public SmartString(String id, String value) {
        mTableID = id;
        mDefaultValue = value;
        SmartDashboard.putString(mTableID, mDefaultValue);
    }

    /**
     * Creates a SmartString for an already existing value.
     * 
     * @param id the name of the String on SmartDashboard
     */
    public SmartString(String id) {
        mTableID = id;
        mDefaultValue = "";
        SmartDashboard.setDefaultString(mTableID, "");
    }

    /**
     * Gets the value of the String from SmartDashboard
     * 
     * @return the value of the String from SmartDashboard
     */
    public String get() {
        return SmartDashboard.getString(mTableID, mDefaultValue);
    }

    /**
     * Gets the default value of the String
     * 
     * @return the default value of the String
     */
    public String getDefault() {
        return mDefaultValue;
    }

    /**
     * Sets the value of the String on SmartDashboard
     * 
     * @param value what the value on SmartDashboard will be set to
     */
    public void set(String value) {
        SmartDashboard.putString(mTableID, value);
    }

    /**
     * Sets the default value of the String
     * 
     * @param value what the default value of the String will be set to
     */
    public void setDefault(String value) {
        mDefaultValue = value;
    }

    /**
     * Resets the value on SmartDashboard to the default value
     */
    public void reset() {
        set(getDefault());
    }
}