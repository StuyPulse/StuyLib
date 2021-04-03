/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.network;

import com.stuypulse.stuylib.streams.IStream;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * SmartNumber works as a wrapper for values on {@link SmartDashboard}. The idea for this class was
 * to make getting values on {@link SmartDashboard} easier by making them variables that you know
 * were initialized.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class SmartNumber extends Number implements IStream {

    private static final long serialVersionUID = 1L;

    /** The ID / Name for the value on {@link SmartDashboard}. */
    private final NetworkTableEntry mEntry;

    /** The default value that the {@link SmartDashboard} value was set too. */
    private final double mDefaultValue;

    /**
     * Creates a {@link SmartNumber} with a network table entry instead of a value for {@link
     * SmartDashboard}. This allows you to put items on things like {@link
     * edu.wpi.first.wpilibj.shuffleboard.Shuffleboard}, without having to use a raw {@link
     * NetworkTableEntry}.
     *
     * @param entry the {@link NetworkTableEntry} the {@link SmartNumber} should be set to.
     * @param value the default value of the {@link SmartNumber}
     */
    public SmartNumber(NetworkTableEntry entry, double value) {
        mEntry = entry;
        mDefaultValue = value;
        mEntry.forceSetNumber(value);
    }

    /**
     * Creates a SmartNumber with the element name and a default value. The value on {@link
     * SmartDashboard} will be reset to the default value on initialization.
     *
     * @param id the name of the number on SmartDashboard
     * @param value the default / initialization value for the value
     */
    public SmartNumber(String id, double value) {
        this(SmartDashboard.getEntry(id), value);
    }

    /** @return the value of the number from SmartDashboard */
    public double get() {
        return mEntry.getDouble(mDefaultValue);
    }

    /** @return the default value of the number */
    public double getDefault() {
        return mDefaultValue;
    }

    /** @param value what the value on {@link SmartDashboard} will be set to */
    public void set(Number value) {
        mEntry.forceSetNumber(value);
    }

    /** Resets the value on {@link SmartDashboard} to the default value */
    public void reset() {
        set(getDefault());
    }

    /** @return the value of the number from {@link SmartDashboard} (casted to a double) */
    public double doubleValue() {
        return (double) this.get();
    }

    /** @return the value of the number from {@link SmartDashboard} (casted to a float) */
    public float floatValue() {
        return (float) this.get();
    }

    /** @return the value of the number from {@link SmartDashboard} (casted to a int) */
    public int intValue() {
        return (int) this.get();
    }

    /** @return the value of the number from {@link SmartDashboard} (casted to a long) */
    public long longValue() {
        return (long) this.get();
    }

    /**
     * This function checks to see if {@code oldNumber} is a {@link SmartNumber}, if it is, then it
     * sets the {@code oldNumber} to {@code newNumber} and returns the {@code oldNumber}. Otherwise,
     * it just returns {@code newNumber}.
     *
     * @param oldNumber number that is being replaced
     * @param newNumber number representing the value of the new number
     * @return if {@code oldNumber} is not a {@link SmartNumber}, then this function will return
     *     {@code newNumber}. If {@code oldNumber} is a {@link SmartNumber}, then this function will
     *     return {@code oldNumber} with its value set to {@code newNumber}
     */
    public static Number setNumber(Number oldNumber, Number newNumber) {
        if (oldNumber instanceof SmartNumber) {
            SmartNumber oldSmartNumber = (SmartNumber) oldNumber;
            oldSmartNumber.set(newNumber.doubleValue());
            return oldSmartNumber;
        } else {
            return newNumber;
        }
    }
}
