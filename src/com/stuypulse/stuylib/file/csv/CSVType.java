package com.stuypulse.stuylib.file.csv;

/**
 * Contains all the information needed for each of the csv files to work.
 * 
 * Mainly contains the delimiter for each class
 */

public enum CSVType {

    DEFAULT(","), EXCEL(","), MYSQL("\t"), TCSV(",\t");

    // Stores Seperator for CSV
    private String mDelimiter;

    // Constructor that sets the Delimiter
    private CSVType(String delimiter) {
        mDelimiter = delimiter;
    }

    // Gets the delimiter from the enum
    public String getDelimiter() {
        return mDelimiter;
    }
}