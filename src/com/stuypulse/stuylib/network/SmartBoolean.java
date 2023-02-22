/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network;

import com.stuypulse.stuylib.streams.booleans.BStream;

import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * {@link SmartBoolean} works as a wrapper for values on {@link SmartDashboard}. The idea for this
 * class was to make getting values on {@link SmartDashboard} easier by making them variables that
 * you know were initialized.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class SmartBoolean implements BStream {

    /** The ID / Name for the value on {@link SmartDashboard}. */
    private final int mHandle;

    /** The default value that the {@link SmartDashboard} value was set too. */
    private final boolean mDefaultValue;

    /**
     * Creates a {@link SmartBoolean} with the element name and a default value. The value on {@link
     * SmartDashboard} will be reset to the default value on initialization.
     *
     * @param id the name of the boolean on {@link SmartDashboard}
     * @param value the default / initialization value for the value
     */
    public SmartBoolean(String id, boolean value) {
        mHandle = NetworkTablesJNI.getEntry(NetworkTablesJNI.getDefaultInstance(), "SmartDashboard/" + id);
        mDefaultValue = value;
        reset();
    }

    /** @return the value of the boolean from {@link SmartDashboard} */
    public boolean get() {
        return NetworkTablesJNI.getBoolean(mHandle, mDefaultValue);
    }

    /** @return the default value of the boolean */
    public boolean getDefault() {
        return mDefaultValue;
    }

    /** @param value what the value on {@link SmartDashboard} will be set to */
    public void set(boolean value) {
        NetworkTablesJNI.setBoolean(mHandle, HALUtil.getFPGATime(), value);
    }

    /** Resets the value on {@link SmartDashboard} to the default value */
    public void reset() {
        set(getDefault());
    }
}
