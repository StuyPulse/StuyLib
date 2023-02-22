/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network;

import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.function.Supplier;

/**
 * SmartString works as a wrapper for values on {@link SmartDashboard}. The idea for this class was
 * to make getting values on {@link SmartDashboard} easier by making them variables that you know
 * were initialized.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class SmartString implements Supplier<String> {

    /** The ID / Name for the value on {@link SmartDashboard}. */
    private final int mHandle;

    /** The default value that the {@link SmartDashboard} value was set too. */
    private final String mDefaultValue;

    /**
     * Creates a SmartString with the element name and a default value. The value on {@link
     * SmartDashboard} will be reset to the default value on initialization.
     *
     * @param id the name of the String on {@link SmartDashboard}
     * @param value the default / initialization value for the value
     */
    public SmartString(String id, String value) {
        mHandle = NetworkTablesJNI.getEntry(NetworkTablesJNI.getDefaultInstance(), "SmartDashboard/" + value);
        mDefaultValue = value;
        reset();
    }

    /** @return the value of the String from SmartDashboard */
    public String get() {
        return NetworkTablesJNI.getString(mHandle, mDefaultValue);
    }

    /** @return the default value of the String */
    public String getDefault() {
        return mDefaultValue;
    }

    /** @param value what the value on {@link SmartDashboard} will be set to */
    public void set(String value) {
        NetworkTablesJNI.setString(mHandle, 0, value);
    }

    /** Resets the value on {@link SmartDashboard} to the default value */
    public void reset() {
        set(getDefault());
    }
}
