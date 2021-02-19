// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

package com.stuypulse.stuylib.network;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * SmartBoolean works as a wrapper for values on SmartDashboard. The idea for this class was to make
 * getting values on SmartDashboard easier by making them variables that you know were initialized.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class SmartBoolean {

    /** The ID / Name for the value on SmartDashboard. */
    private String mTableID;

    /** The default value that the SmartDashboard value was set too. */
    private boolean mDefaultValue;

    /**
     * Creates a SmartBoolean with the element name and a default value. The value on SmartDashboard
     * will be reset to the default value on initialization.
     *
     * @param id the name of the boolean on SmartDashboard
     * @param value the default / initialization value for the value
     */
    public SmartBoolean(String id, boolean value) {
        mTableID = id;
        mDefaultValue = value;
        SmartDashboard.putBoolean(mTableID, mDefaultValue);
    }

    /**
     * Creates a SmartBoolean for an already existing value.
     *
     * @param id the name of the boolean on SmartDashboard
     */
    public SmartBoolean(String id) {
        mTableID = id;
        mDefaultValue = false;
        SmartDashboard.setDefaultBoolean(mTableID, false);
    }

    /**
     * Gets the value of the boolean from SmartDashboard
     *
     * @return the value of the boolean from SmartDashboard
     */
    public boolean get() {
        return SmartDashboard.getBoolean(mTableID, mDefaultValue);
    }

    /**
     * Gets the default value of the boolean
     *
     * @return the default value of the boolean
     */
    public boolean getDefault() {
        return mDefaultValue;
    }

    /**
     * Sets the value of the boolean on SmartDashboard
     *
     * @param value what the value on SmartDashboard will be set to
     */
    public void set(boolean value) {
        SmartDashboard.putBoolean(mTableID, value);
    }

    /**
     * Sets the default value of the boolean
     *
     * @param value what the default value of the boolean will be set to
     */
    public void setDefault(boolean value) {
        mDefaultValue = value;
    }

    /** Resets the value on SmartDashboard to the default value */
    public void reset() {
        set(getDefault());
    }
}
