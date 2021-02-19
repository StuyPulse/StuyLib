// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

package com.stuypulse.stuylib.file.csv;

/**
 * Stores the data for each element in a custom class. This is to prevent the need to parse doubles
 * over and over again, when we can just store it as a double.
 *
 * <p>Call .toString() or .toDouble() to get data
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class CSVElement {

    // If value is a number
    final boolean mIsNumber;

    // Value as Double
    final double mNumberValue;

    // Value as String
    final String mStringValue;

    /**
     * Initialize with string
     *
     * @param value desired value
     */
    public CSVElement(String value) {
        // CSVs Sometimes don't trim their values
        mStringValue = value.trim();

        // Have to do this operation without final variables
        boolean isNumber;
        double number;

        try {
            number = Double.parseDouble(mStringValue);
            isNumber = true;
        } catch (NumberFormatException e) {
            number = Double.NaN;
            isNumber = false;
        }

        // Assign values
        mNumberValue = number;
        mIsNumber = isNumber;
    }

    /**
     * Value of element as a string
     *
     * @return value as a string
     */
    public String toString() {
        return mStringValue;
    }

    /**
     * Value of element as a number
     *
     * @return value as a number
     */
    public Number toNumber() throws NumberFormatException {
        return Double.valueOf(toDouble());
    }

    /**
     * Value of element as a double
     *
     * @return value as a double
     */
    public double toDouble() throws NumberFormatException {
        if (isDouble()) {
            return mNumberValue;
        } else {
            String message = "";
            message += "Attempted to read \"";
            message += mStringValue;
            message += "\" as a Number but failed!";
            throw new NumberFormatException(message);
        }
    }

    /**
     * Returns if the element is a string
     *
     * @return if the element is a string
     */
    public boolean isString() {
        return true;
    }

    /**
     * Returns if the element is a number
     *
     * @return if the element is a number
     */
    public boolean isNumber() {
        return mIsNumber;
    }

    /**
     * Returns if the element is a double
     *
     * @return if the element is a double
     */
    public boolean isDouble() {
        return mIsNumber;
    }
}
