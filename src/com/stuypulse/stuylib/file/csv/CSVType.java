// Copyright (c) 2021 StuyPulse Inc. All rights reserved.
// This work is licensed under the terms of the MIT license
// found in the root directory of this project.

package com.stuypulse.stuylib.file.csv;

/**
 * Contains all the information needed for each of the csv files to work.
 *
 * <p>Mainly contains the delimiter for each class
 */
public enum CSVType {
    COMMA(","),
    TAB("\t"),
    COMMA_TAB(",\t"),
    SEMI_COLON(";"),
    PIPE("|"),
    CARRET("^"),

    DEFAULT(CSVType.COMMA),

    EXCEL(CSVType.COMMA),
    MYSQL(CSVType.TAB),
    TCSV(CSVType.COMMA_TAB);

    // Stores Separator for CSV
    private final String mDelimiter;

    // Constructor that sets the Delimiter
    private CSVType(String delimiter) {
        mDelimiter = delimiter;
    }

    // Constructor that sets the Delimiter
    private CSVType(CSVType other) {
        this(other.getDelimiter());
    }

    // Gets the delimiter from the enum
    public String getDelimiter() {
        return mDelimiter;
    }
}
