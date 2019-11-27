package com.stuypulse.stuylib.file.csv;

/**
 * Stores the data for each element in a custom class. This is to prevent the
 * need to parse doubles over and over again, when we can just store it as a
 * double.
 * 
 * Call .toString() or .toDouble() to get data
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class CSVElement {

    // If value is a number
    boolean mIsNumber;

    // Value as Double
    double mNumberValue;

    // Value as String
    String mStringValue;

    /**
     * Initialize with string
     * 
     * @param value desired value
     */
    public CSVElement(String value) {
        setValue(value);
    }

    /**
     * Set internal value
     * 
     * @param value desired value
     * @return if value was a number
     */
    public boolean setValue(String value) {
        mStringValue = value.trim();
        try {
            mNumberValue = Double.parseDouble(mStringValue);
            mIsNumber = true;
        } catch (NumberFormatException e) {
            mNumberValue = Double.NaN;
            mIsNumber = false;
        }

        return mIsNumber;
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