package com.stuypulse.stuylib.network;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * SmartNumber works as a wrapper for values on SmartDashboard. The idea for
 * this class was to make getting values on SmartDashboard easier by making them
 * variables that you know were initialized.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class SmartNumber extends Number {

    private static final long serialVersionUID = 1L;

    /**
     * The ID / Name for the value on SmartDashboard.
     */
    private String mTableID;

    /**
     * The default value that the SmartDashboard value was set too.
     */
    private double mDefaultValue;

    /**
     * Creates a SmartNumber with the element name and a default value. The
     * value on SmartDashboard will be reset to the default value on
     * initialization.
     *
     * @param id    the name of the number on SmartDashboard
     * @param value the default / initialization value for the value
     */
    public SmartNumber(String id, double value) {
        mTableID = id;
        mDefaultValue = value;
        SmartDashboard.putNumber(mTableID, mDefaultValue);
    }

    /**
     * Creates a SmartNumber for an already existing value.
     *
     * @param id the name of the number on SmartDashboard
     */
    public SmartNumber(String id) {
        mTableID = id;
        mDefaultValue = 0.0;
        SmartDashboard.setDefaultNumber(mTableID, 0.0);
    }

    /**
     * Gets the value of the number from SmartDashboard
     *
     * @return the value of the number from SmartDashboard
     */
    public double get() {
        return SmartDashboard.getNumber(mTableID, mDefaultValue);
    }

    /**
     * Gets the default value of the number
     *
     * @return the default value of the number
     */
    public double getDefault() {
        return mDefaultValue;
    }

    /**
     * Sets the value of the number on SmartDashboard
     *
     * @param value what the value on SmartDashboard will be set to
     */
    public void set(double value) {
        SmartDashboard.putNumber(mTableID, value);
    }

    /**
     * Sets the default value of the number
     *
     * @param value what the default value of the number will be set to
     */
    public void setDefault(double value) {
        mDefaultValue = value;
    }

    /**
     * Resets the value on SmartDashboard to the default value
     */
    public void reset() {
        set(getDefault());
    }

    /**
     * Gets the value of the number from SmartDashboard (casted to a double)
     *
     * @return the value of the number from SmartDashboard (casted to a double)
     */
    public double doubleValue() {
        return (double) this.get();
    }

    /**
     * Gets the value of the number from SmartDashboard (casted to a float)
     *
     * @return the value of the number from SmartDashboard (casted to a float)
     */
    public float floatValue() {
        return (float) this.get();
    }

    /**
     * Gets the value of the number from SmartDashboard (casted to a int)
     *
     * @return the value of the number from SmartDashboard (casted to a int)
     */
    public int intValue() {
        return (int) this.get();
    }

    /**
     * Gets the value of the number from SmartDashboard (casted to a long)
     *
     * @return the value of the number from SmartDashboard (casted to a long)
     */
    public long longValue() {
        return (long) this.get();
    }
}
